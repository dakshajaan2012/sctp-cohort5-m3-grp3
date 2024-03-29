package sg.edu.ntu.m3p3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.sym.Name;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import sg.edu.ntu.m3p3.entity.Booking;
import sg.edu.ntu.m3p3.entity.Session;
import sg.edu.ntu.m3p3.entity.User.User;
import sg.edu.ntu.m3p3.repository.BookingRepository;
import sg.edu.ntu.m3p3.repository.SessionRepository;
import sg.edu.ntu.m3p3.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SessionService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private UserRepository userRepository;

    // private final SessionRepository sessionRepository;
    // private final UserRepository userRepository;

    public SessionService(SessionRepository sessionRepository, UserRepository userRepository,
            BookingRepository bookingRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    // TESTING
    // Added booking parameter

    @Transactional
    public Session createSessionForUser(UUID userId, Session sessionDat) {

        User user = userRepository.findById(userId)

                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        Booking booking = bookingRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found for user with id: " + userId));

        Session session = new Session();
        session.setUser(user);
        session.setBooking(booking);
        session.setUserName(user.getUserName()); // Set the userName from the User entity to the Session entity
        session.setFirstName(user.getFirstName());
        session.setLastName(user.getLastName());
        // session.setTimeStart(LocalDateTime.now().minusMinutes(30));
        // session.setTimeStop(LocalDateTime.now());

        session.setCreatedAt(user.getCreatedAt());
        session.setUpdatedAt(user.getUpdatedAt());
        session.setBooking(booking);
        session.setBookingTime(booking.getBookingTime());
        return sessionRepository.save(session);
    }

    /*
     * @Transactional
     * public Session createSessionForUser(@Valid User session) {
     * User user = UserRepository.findById(user.getId())
     * .orElseThrow(() -> new EntityNotFoundException("User not found with id: " +
     * session2));
     * 
     * Session session = new Session();
     * session.setUser(user);
     * session.setUserName(user.getUserName()); // Set userName from User entity to
     * Session entity
     * // session.setContactNumber(user.getContactNumber()); // Set email from User
     * // entity to Session entity
     * // Set other fields as needed
     * 
     * // Save the session to the database
     * return sessionRepository.save(session);
     * }
     */

    /*
     * @Transactional
     * public Session createSessionForUser(User user, Session session) {
     * // Set the user for the session
     * session.setUser(user);
     * 
     * // Save the session to the database
     * return sessionRepository.save(session);
     * }
     */

    /*
     * @Transactional
     * public Session createSessionForUser(Long userId) {
     * User user = userRepository.findById(userId)
     * .orElseThrow(() -> new EntityNotFoundException("User not found with id: " +
     * userId));
     * 
     * Session session = new Session();
     * session.setUser(user);
     * // session.setUserName(user.getName()); // Set the userName from the User
     * entity
     * // to the Session entity
     * session.setEmail(user.getEmail());
     * // session.setUserName(user.getUserName());
     * // session.setSessionName(user.getSessions());
     * 
     * return sessionRepository.save(session);
     * }
     */

    // All for session only

    public List<Session> getAllSession() {
        return sessionRepository.findAll();
    }
    // create session

    /*
     * public Session createSession(Session session) {
     * // session.setDeleted(false);
     * return sessionRepository.save(session);
     * }
     */

    // get session by id
    public Session getSessionById(UUID id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));
    }

    // Update session
    public Session updateSession(UUID id, Session sessionDetails) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));

        // session.setUserName(sessionDetails.getName());
        session.setUserName(sessionDetails.getUserName());
        session.setActivity(sessionDetails.getActivity());
        // session.setTimeStart(sessionDetails.getTimeStart());
        // session.setTimeStop(sessionDetails.getTimeStop());
        // session.setDeleted(sessionDetails.isDeleted());

        return sessionRepository.save(session);
    }

    // Delete session
    public void deleteSession(UUID id) {

        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Session not found"));

        sessionRepository.deleteById(id);

    }

}