package sg.edu.ntu.m3p3.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sg.edu.ntu.m3p3.entity.Session;
import sg.edu.ntu.m3p3.repository.SessionRepository;
import java.util.List;
import java.util.UUID;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<Session> getAllSession() {
        return sessionRepository.findAll();
    }

    public Session createSession(Session session) {

        // session.setDeleted(false);
        return sessionRepository.save(session);
    }

    public Session getSessionById(UUID id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));
    }

    public Session updateSession(UUID id, Session sessionDetails) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));

        session.setUserName(sessionDetails.getUserName());
        session.setSessionName(sessionDetails.getSessionName());
        session.setTimeStart(sessionDetails.getTimeStart());
        session.setTimeStop(sessionDetails.getTimeStop());
        // session.setDeleted(sessionDetails.isDeleted());

        return sessionRepository.save(session);
    }

    public void deleteSession(UUID id) {

        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Session not found"));

        sessionRepository.deleteById(id);

    }
}
