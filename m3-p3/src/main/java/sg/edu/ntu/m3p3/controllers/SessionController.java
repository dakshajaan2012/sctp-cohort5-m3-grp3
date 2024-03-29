package sg.edu.ntu.m3p3.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import sg.edu.ntu.m3p3.entity.Session;
import sg.edu.ntu.m3p3.repository.BookingRepository;
import sg.edu.ntu.m3p3.repository.SessionRepository;
import sg.edu.ntu.m3p3.repository.UserRepository;
import sg.edu.ntu.m3p3.service.SessionService;

@RestController
@Tag(name = "4. Session", description = "Session APIs")
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
    // Ok
    // Get all /sessions
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

    // Ok
    // End point /sessions/users/userid
    @Operation(summary = "Create Session", description = "Before calling this API, you have to:\n" +
            "1. POST /slots - Create Parking Slot\n" +
            "2. GET /slot - Retrieve ParkingSlotId\n" +
            "3. POST /bookings - Create Booking\n" +
            "4. POST /sessions/users/{userId} - Create Session")

    @PostMapping("/users/{userId}")
    public ResponseEntity<Map<String, String>> createSessionForUser(@PathVariable UUID userId,
            @RequestBody Session sessionData) {
        Session session = sessionService.createSessionForUser(userId, sessionData);
        String message = "Session created successfully for user with ID: " + userId;
        return ResponseEntity.ok(Collections.singletonMap("message", message));
        // return ResponseEntity.ok(session);
    }

    // Get session with sessionId- ok
    // End point- sessions/sessionId created
    @GetMapping("/{id}")
    public ResponseEntity<Session> getSessionById(@PathVariable UUID userId) {
        Session session = sessionService.getSessionById(userId);
        logger.info("GET /session/{} called", userId);
        return ResponseEntity.ok(session);
        // return ResponseEntity.ok("GET /sessions/" + id + " ok");
    }

    // Update not applicable
    @PutMapping("/{userId}")
    public ResponseEntity<Session> updateSession(@PathVariable UUID userId, @RequestBody Session sessionDetails) {
        Session updatedSession = sessionService.updateSession(userId, sessionDetails);
        logger.info("PUT /session/{} updated", userId);
        return ResponseEntity.ok(updatedSession);
    }

    // Session can not be deleted
    @DeleteMapping("sessions/{id}")
    public ResponseEntity<String> deleteSession(@PathVariable UUID id) {
        sessionService.deleteSession(id);
        logger.info("DELETE /session/{} deleted", id);
        // return ResponseEntity.noContent().build();
        return ResponseEntity.ok("Session with Id " + id + " has been deleted.");
    }
}