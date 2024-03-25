
package sg.edu.ntu.m3p3.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
//import sg.edu.ntu.m3p3.entity.Session;
//import sg.edu.ntu.m3p3.entity.Session;
import sg.edu.ntu.m3p3.entity.User;
import sg.edu.ntu.m3p3.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // get all
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            logger.error("GET /users called - Not found");
            return ResponseEntity.notFound().build();
        }
        logger.info("GET /users called - Found {} users", users.size());

        return ResponseEntity.ok(users);
    }

    // post, with validation
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // Construct a response object containing validation error details
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            // return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User createdUser = userService.createUser(user);
        logger.info("POST /users created");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        logger.info("GET /user/{} called", id);
        return ResponseEntity.ok(user);
        // return ResponseEntity.ok("GET /sessions/" + id + " ok");
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        logger.info("PUT /user/{} updated", id);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        logger.info("DELETE /user/{} deleted", id);
        // return ResponseEntity.noContent().build();
        return ResponseEntity.ok("User with Id " + id + " has been deleted.");
    }

    // Define user-related REST endpoints
}
