package sg.edu.ntu.m3p3.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import sg.edu.ntu.m3p3.entity.Session;
import sg.edu.ntu.m3p3.service.SessionService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/sessions")
public class SessionController {
    private static final Logger logger = LoggerFactory.getLogger(SessionController.class);
    private final SessionService sessionService;

    // @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

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

    // post, with validation
    @PostMapping
    public ResponseEntity<Session> createSession(@Valid @RequestBody Session session, BindingResult bindingResult) {
        logger.info("POST /session created");
        if (bindingResult.hasErrors()) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Session createdSession = sessionService.createSession(session);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);
    }

    // get with id

    @GetMapping("/{id}")
    public ResponseEntity<Session> getSessionById(@PathVariable Long id) {
        Session session = sessionService.getSessionById(id);
        logger.info("GET /session/{} called", id);
        return ResponseEntity.ok(session);
        // return ResponseEntity.ok("GET /sessions/" + id + " ok");
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<Session> updateSession(@PathVariable Long id, @RequestBody Session sessionDetails) {
        Session updatedSession = sessionService.updateSession(id, sessionDetails);
        logger.info("PUT /session/{} updated", id);
        return ResponseEntity.ok(updatedSession);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
        logger.info("DELETE /session/{} deleted", id);
        // return ResponseEntity.noContent().build();
        return ResponseEntity.ok("Session with Id " + id + " has been deleted.");
    }

}
