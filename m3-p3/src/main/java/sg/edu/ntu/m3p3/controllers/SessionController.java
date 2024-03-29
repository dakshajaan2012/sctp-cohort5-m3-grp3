package sg.edu.ntu.m3p3.controllers;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.ntu.m3p3.entity.Session;
import sg.edu.ntu.m3p3.repository.BookingRepository;
import sg.edu.ntu.m3p3.repository.SessionRepository;
import sg.edu.ntu.m3p3.repository.UserRepository;
import sg.edu.ntu.m3p3.service.SessionService;

@RestController
@RequestMapping("/sessions")
public class SessionController {
    private static final Logger logger = LoggerFactory.getLogger(SessionController.class);
    private final SessionService sessionService;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public SessionController(SessionService sessionService, SessionRepository sessionRepository,
            UserRepository userRepository, BookingRepository bookingRepository) {
        this.sessionService = sessionService;
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    @Autowired
    // private UserRepository userRepository;
    // private SessionRepository sessionRepository;

    // Endpoint to retrieve sessions for a specific user
    /*
     * @GetMapping("/users/{userId}/sessions")
     * public ResponseEntity<List<Session>> getUserSessions(@PathVariable UUID
     * userId) {
     * // Retrieve sessions for the specified user ID
     * List<Session> sessions = sessionService.getSessionsByUserId(userId);
     * 
     * // Check if sessions are found and return the response accordingly
     * if (sessions.isEmpty()) {
     * return ResponseEntity.notFound().build();
     * } else {
     * return ResponseEntity.ok(sessions);
     * }
     * }
     */

    // get all
    @GetMapping
    public ResponseEntity<List<Session>> getAllSession() {
        List<Session> sessions = sessionService.getAllSession();
        if (sessions.isEmpty()) {
            logger.error("GET /session called - Not found");
            return ResponseEntity.notFound().build();
        }
        logger.info("GET /session called - Found {} sessions", sessions.size());

        return ResponseEntity.ok(sessions);
    }

    // FOR TESTING

    @PostMapping("/users/{userId}")
    public ResponseEntity<Session> createSessionForUser(@PathVariable UUID userId, @RequestBody Session sessionData) {
        Session session = sessionService.createSessionForUser(userId, sessionData);
        return ResponseEntity.ok(session);
    }

    /*
     * @PostMapping("/users/{userId}")
     * public ResponseEntity<Session> createSessionForUser(@PathVariable UUID
     * userId) {
     * Session createdSession = sessionService.createSessionForUser(userId);
     * return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);
     * }
     */

    /*
     * @PostMapping("/users/{userId}/sessions")
     * public ResponseEntity<Session> createSessionForUser(@PathVariable UUID
     * userId) {
     * Session createdSession = sessionService.createSessionForUser(userId);
     * return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);
     * }
     */
    /*
     * @PostMapping
     * public ResponseEntity<Session> createSessionForUser(@PathVariable UUID
     * userId, @RequestBody Session session) {
     * User user = userRepository.findById(userId)
     * .orElseThrow(() -> new EntityNotFoundException("User not found with id: " +
     * userId));
     * 
     * // Call the service method to create a session for the user
     * Session createdSession = sessionService.createSessionForUser(user, session);
     * 
     * // Return the created session in the response
     * return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);
     * }
     */

    // All for session only
    // post, with validation

    /*
     * @PostMapping
     * public ResponseEntity<Session> createSession(@Valid @RequestBody Session
     * session, BindingResult bindingResult) {
     * logger.info("POST /session created");
     * if (bindingResult.hasErrors()) {
     * 
     * return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
     * }
     * 
     * Session createdSession = sessionService.createSession(session);
     * return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);
     * }
     */

    // get with id

    @GetMapping("/{id}")
    public ResponseEntity<Session> getSessionById(@PathVariable UUID id) {
        Session session = sessionService.getSessionById(id);
        logger.info("GET /session/{} called", id);
        return ResponseEntity.ok(session);
        // return ResponseEntity.ok("GET /sessions/" + id + " ok");
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<Session> updateSession(@PathVariable UUID id, @RequestBody Session sessionDetails) {
        Session updatedSession = sessionService.updateSession(id, sessionDetails);
        logger.info("PUT /session/{} updated", id);
        return ResponseEntity.ok(updatedSession);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSession(@PathVariable UUID id) {
        sessionService.deleteSession(id);
        logger.info("DELETE /session/{} deleted", id);
        // return ResponseEntity.noContent().build();
        return ResponseEntity.ok("Session with Id " + id + " has been deleted.");
    }
}