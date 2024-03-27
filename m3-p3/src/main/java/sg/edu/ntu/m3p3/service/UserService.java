package sg.edu.ntu.m3p3.service;

import java.util.*;

import org.springframework.data.repository.query.Param;

import sg.edu.ntu.m3p3.entity.UserLog;
import sg.edu.ntu.m3p3.entity.User.User;
import sg.edu.ntu.m3p3.entity.User.UserBulkUpdateRequest;
import sg.edu.ntu.m3p3.utils.SearchCriteria;

public interface UserService {

    User createUser(User user);

    User getUserById(UUID userId);

    ArrayList<User> getAllUsers();

    List<User> getAllUsersNativeQuery();

    Optional<User> findByUserName(String userName);

    Optional<User> findByEmail(String email);

    User updateUser(UUID userId, User user);

    void deleteUser(UUID userId);

    UserLog addUserLogToUser(UUID userId, UserLog userLog);

    List<User> findByUserNameContaining(@Param("keyword") String keyword);

    List<User> findBySearchCriteria(SearchCriteria searchCriteria);

    List<User> bulkUpdateUsers(UserBulkUpdateRequest bulkUpdateRequest);

	boolean existsById(UUID userId);

}