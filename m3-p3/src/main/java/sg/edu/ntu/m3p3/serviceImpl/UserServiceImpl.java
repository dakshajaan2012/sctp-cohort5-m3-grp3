package sg.edu.ntu.m3p3.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import sg.edu.ntu.m3p3.entity.Session;
import sg.edu.ntu.m3p3.entity.UserLog;
import sg.edu.ntu.m3p3.entity.User.User;
import sg.edu.ntu.m3p3.entity.User.UserBulkUpdateRequest;
import sg.edu.ntu.m3p3.exception.UserLogNotFoundException;
import sg.edu.ntu.m3p3.repository.AddressRepository;
import sg.edu.ntu.m3p3.repository.FeatureUsageRepository;
import sg.edu.ntu.m3p3.repository.SessionRepository;
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
    private final AddressRepository addressRepository;
    private final SessionRepository sessionRepository;
    private final FeatureUsageRepository featureUsageRepository;

    public UserServiceImpl(UserRepository userRepository, UserLogRepository userLogRepository,
            FeatureUsageRepository featureUsageRepository,
            SessionRepository sessionRepository,
            AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.userLogRepository = userLogRepository;
        this.addressRepository = addressRepository;
        this.sessionRepository = sessionRepository;
        this.featureUsageRepository = featureUsageRepository;
    }

    @Override
    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
    }

    @Override
    public ArrayList<User> getAllUsers() {
        ArrayList<User> allUsers = userRepository.findAll();
        return (ArrayList<User>) allUsers;
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public void checkUsernameExists(String userName) {
        Optional<User> existingUser = findByUserName(userName);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Username '" + userName + "' already exists");
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void checkEmailExists(String email) {
        Optional<User> existingEmail = findByEmail(email);
        if (existingEmail.isPresent()) {
            throw new IllegalArgumentException("Email '" + email + "' already exists");
        }
    }

    @Override
    public List<User> findByUserNameContaining(@Param("keyword") String keyword) {
        return userRepository.findByUserNameContaining(keyword);
    }

    @Override
    public User createUser(User user) {
        CompletableFuture<Optional<User>> existingUserByUsernameFuture = CompletableFuture
                .supplyAsync(() -> findByUserName(user.getUserName()));
        CompletableFuture<Optional<User>> existingUserByEmailFuture = CompletableFuture
                .supplyAsync(() -> findByEmail(user.getEmail()));

        try {
            Optional<User> existingUserByUsername = existingUserByUsernameFuture.get();
            Optional<User> existingUserByEmail = existingUserByEmailFuture.get();

            StringBuilder errorMessage = new StringBuilder();

            if (existingUserByUsername.isPresent()) {
                errorMessage.append("Username '").append(user.getUserName()).append("' already exists. ");
            }
            if (existingUserByEmail.isPresent()) {
                errorMessage.append("Email '").append(user.getEmail()).append("' already exists.");
            }

            if (errorMessage.length() > 0) {
                throw new IllegalArgumentException(errorMessage.toString());
            }

            // Set other attributes and save the user
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            user.setLoginAttemptCounter(0);
            user.setIsActive(true);
            user.setIsDeleted(false);

            return userRepository.save(user);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            // Handle other exceptions if needed
            throw new IllegalArgumentException("Failed to create user: " + e.getMessage());
        }
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
                    case IS:
                        for (String fieldName : fieldNames) {
                            predicates.add(criteriaBuilder.isTrue(root.get(fieldName)));
                        }
                        break;
                    case IS_NOT:
                        for (String fieldName : fieldNames) {
                            predicates.add(criteriaBuilder.isFalse(root.get(fieldName)));
                        }
                        break;
                    case ON_OR_BEFORE:
                        for (String fieldName : fieldNames) {
                            if (value instanceof LocalDateTime) {
                                predicates.add(
                                        criteriaBuilder.lessThanOrEqualTo(root.get(fieldName), (LocalDateTime) value));
                            } else if (value instanceof String) {
                                LocalDateTime dateTimeValue = LocalDateTime.parse((String) value);
                                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(fieldName), dateTimeValue));
                            } else {
                                throw new IllegalArgumentException(
                                        "Value must be a LocalDateTime or String for ON_OR_BEFORE comparison.");
                            }
                        }
                        break;
                    case ON_OR_AFTER:
                        for (String fieldName : fieldNames) {
                            if (value instanceof LocalDateTime) {
                                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName),
                                        (LocalDateTime) value));
                            } else if (value instanceof String) {
                                LocalDateTime dateTimeValue = LocalDateTime.parse((String) value);
                                predicates
                                        .add(criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName), dateTimeValue));
                            } else {
                                throw new IllegalArgumentException(
                                        "Value must be a LocalDateTime or String for ON_OR_AFTER comparison.");
                            }
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
    @Transactional
    public void deleteUser(UUID userId) {
        // Retrieve sessions associated with the user
        List<Session> sessions = sessionRepository.findByUser_UserId(userId);

        // Iterate through sessions and delete related data
        for (Session session : sessions) {
            // Delete related data for each session
            featureUsageRepository.deleteBySession_SessionId(session.getSessionId());
        }

        // Delete sessions
        sessionRepository.deleteByUser_UserId(userId);

        // Delete other related data
        userLogRepository.deleteByUser_UserId(userId);
        addressRepository.deleteByUser_UserId(userId);

        // Delete the user
        userRepository.deleteById(userId);
    }

    @Override
    public UserLog addUserLogToUser(UUID userId, UserLog userLog) {
        User selectedUser = userRepository.findById(userId).orElseThrow(() -> new UserLogNotFoundException(userId));
        userLog.setUser(selectedUser);
        return userLogRepository.save(userLog);
    }

    @Override
    public boolean existsById(UUID userId) {
        return userRepository.existsById(userId);
    }

}