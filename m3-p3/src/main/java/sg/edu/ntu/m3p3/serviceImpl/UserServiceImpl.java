package sg.edu.ntu.m3p3.serviceImpl;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import sg.edu.ntu.m3p3.UserLogNotFoundException;
import sg.edu.ntu.m3p3.entity.UserLog;
import sg.edu.ntu.m3p3.entity.User.User;
import sg.edu.ntu.m3p3.entity.User.UserBulkUpdateRequest;
import sg.edu.ntu.m3p3.exceptions.UserNotFoundException;
import sg.edu.ntu.m3p3.repository.UserLogRepository;
import sg.edu.ntu.m3p3.repository.UserRepository;
import sg.edu.ntu.m3p3.service.UserService;
import sg.edu.ntu.m3p3.utils.ComparisonOperator;
import sg.edu.ntu.m3p3.utils.FilterCriteria;
import sg.edu.ntu.m3p3.utils.LogicalOperator;
import sg.edu.ntu.m3p3.utils.SearchCriteria;

@Service
// @SuppressWarnings("null")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserLogRepository userLogRepository;

    public UserServiceImpl(UserRepository userRepository, UserLogRepository userLogRepository) {
        this.userRepository = userRepository;
        this.userLogRepository = userLogRepository;
    }

    @Override
    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    @Override
    public ArrayList<User> getAllUsers() {
        ArrayList<User> allUsers = userRepository.findAll();
        return (ArrayList<User>) allUsers;
    }

    @Override
    public List<User> getAllUsersNativeQuery() {
        return userRepository.findAllUsersNativeQuery();
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findByUserNameContaining(@Param("keyword") String keyword) {
        return userRepository.findByUserNameContaining(keyword);
    }

    @Override
    public User createUser(User user) {
        Optional<User> existingUserByUsername = findByUserName(user.getUserName());
        if (existingUserByUsername.isPresent()) {
            return null; // Username already exists, do not create
        }
        Optional<User> existingUserByEmail = findByEmail(user.getEmail());
        if (existingUserByEmail.isPresent()) {
            return null; // Email already exists, do not create
        }

        // Set other attributes and save the user
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setLoginAttemptCounter(0);
        user.setIsActive(true);
        user.setIsDeleted(false);

        return userRepository.save(user);
    }

    @Override
    public List<User> findBySearchCriteria(SearchCriteria searchCriteria) {
        Specification<User> specification = buildSpecification(searchCriteria);
        return userRepository.findAll(specification);
    }

    private <T extends Comparable<? super T>> Specification<User> buildSpecification(SearchCriteria searchCriteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Iterate through the filter criteria list
            for (FilterCriteria filterCriteria : searchCriteria.getFilterCriteriaList()) {
                List<String> fieldNames = filterCriteria.getFieldNames();
                ComparisonOperator comparisonOperator = filterCriteria.getComparisonOperator();
                Object value = filterCriteria.getValue();

                // Create predicates based on the comparison operator
                switch (comparisonOperator) {
                    case EQUALS:
                        for (String fieldName : fieldNames) {
                            predicates.add(criteriaBuilder.equal(root.get(fieldName), value));
                        }
                        break;
                    case NOT_EQUALS:
                        for (String fieldName : fieldNames) {
                            predicates.add(criteriaBuilder.notEqual(root.get(fieldName), value));
                        }
                        break;
                    case GREATER_THAN:
                        for (String fieldName : fieldNames) {
                            predicates.add(criteriaBuilder.greaterThan(root.get(fieldName), (T) value));
                        }
                        break;
                    case GREATER_THAN_OR_EQUAL:
                        for (String fieldName : fieldNames) {
                            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName), (T) value));
                        }
                        break;
                    case LESS_THAN:
                        for (String fieldName : fieldNames) {
                            predicates.add(criteriaBuilder.lessThan(root.get(fieldName), (T) value));
                        }
                        break;
                    case LESS_THAN_OR_EQUAL:
                        for (String fieldName : fieldNames) {
                            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(fieldName), (T) value));
                        }
                        break;
                    case CONTAINS:
                        for (String fieldName : fieldNames) {
                            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(fieldName)),
                                    "%" + value.toString().toLowerCase() + "%"));
                        }
                        break;
                    case STARTS_WITH:
                        for (String fieldName : fieldNames) {
                            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(fieldName)),
                                    value.toString().toLowerCase() + "%"));
                        }
                        break;
                    case ENDS_WITH:
                        for (String fieldName : fieldNames) {
                            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(fieldName)),
                                    "%" + value.toString().toLowerCase()));
                        }
                        break;
                    default:
                        // Handle unsupported comparison operators
                        throw new IllegalArgumentException("Unsupported comparison operator: " + comparisonOperator);
                }
            }

            // Combine predicates using logical operator
            Predicate finalPredicate;
            if (searchCriteria.getLogicalOperator() == LogicalOperator.AND) {
                finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            } else {
                finalPredicate = criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            }

            return finalPredicate;
        };
    }

    @Override
    public User updateUser(UUID userId, User user) {
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public List<User> bulkUpdateUsers(UserBulkUpdateRequest bulkUpdateRequest) {
        List<UUID> userIdList = bulkUpdateRequest.getUserIdList();
        List<User> updatedUsers = new ArrayList<>();

        for (UUID userId : userIdList) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

            if (bulkUpdateRequest.getIsActive() != null) {
                user.setIsActive(bulkUpdateRequest.getIsActive());
            }
            if (bulkUpdateRequest.getIsDeleted() != null) {
                user.setIsDeleted(bulkUpdateRequest.getIsDeleted());
            }
            if (bulkUpdateRequest.getIsAdmin() != null) {
                user.setIsAdmin(bulkUpdateRequest.getIsAdmin());
            }

            user.setUpdatedAt(LocalDateTime.now());

            updatedUsers.add(userRepository.save(user));
        }

        return updatedUsers;
    }

    @Override
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserLog addUserLogToUser(UUID userId, UserLog userLog) {
        User selectedUser = userRepository.findById(userId).orElseThrow(() -> new UserLogNotFoundException(userId));
        userLog.setUser(selectedUser);
        return userLogRepository.save(userLog);
    }

}