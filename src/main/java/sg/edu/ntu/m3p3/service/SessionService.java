package sg.edu.ntu.m3p3.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import sg.edu.ntu.m3p3.entity.Booking;
import sg.edu.ntu.m3p3.entity.Session;
import sg.edu.ntu.m3p3.repository.BookingRepository;
import sg.edu.ntu.m3p3.repository.ParkingSlotRepository;
import sg.edu.ntu.m3p3.repository.SessionRepository;
import sg.edu.ntu.m3p3.repository.UserRepository;

@Service
public class SessionService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    // private final SessionRepository sessionRepository;
    // private final UserRepository userRepository;

    public SessionService(SessionRepository sessionRepository, UserRepository userRepository,
            BookingRepository bookingRepository, ParkingSlotRepository parkingSlotRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.parkingSlotRepository = parkingSlotRepository;
    }

    // Added booking parameter

    @Transactional
    public Session createSessionForUser(UUID userId, Session sessionDat) {

        sg.edu.ntu.m3p3.entity.User.User user = userRepository.findById(userId)

                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        List<Booking> bookings = bookingRepository.findByUser_UserId(userId);

        if (bookings.isEmpty()) {
            throw new EntityNotFoundException("Booking not found for user with id: " + userId);
        }

        // process the first booking in the list
        Booking booking = bookings.get(0);

        Session session = new Session();
        session.setUser(user);
        session.setBooking(booking);
        // session.setUserName(user.getUserName()); // Set the userName from the User
        // entity to the Session entity
        // session.setFirstName(user.getFirstName());
        // session.setLastName(user.getLastName());
        // session.setTimeStart(LocalDateTime.now().minusMinutes(30));
        // session.setTimeStop(LocalDateTime.now());
        // session.setSlotNumber(parkingSlot.getSlotNumber());

        session.setCreatedAt(user.getCreatedAt());
        session.setUpdatedAt(user.getUpdatedAt());
        session.setBooking(booking);
        session.setBookingTime(booking.getBookingTime());
        return sessionRepository.save(session);
    }

    // All for session only
    public List<Session> getAllSession() {
        return sessionRepository.findAll();
    }

    // Get session by id
    public Session getSessionById(UUID userId) {
        return sessionRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));
    }

    // Update session
    public Session updateSession(UUID userId, Session sessionDetails) {
        Session session = sessionRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));

        // session.setUserName(sessionDetails.getUserName());
        session.setActivity(sessionDetails.getActivity());
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