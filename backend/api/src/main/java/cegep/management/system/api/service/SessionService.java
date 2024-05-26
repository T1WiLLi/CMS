package cegep.management.system.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.Session;
import cegep.management.system.api.repo.SessionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public Optional<Session> getSessionById(Long sessionId) {
        return sessionRepository.findById(sessionId);
    }

    public Session createSession(Session session) {
        return sessionRepository.save(session);
    }

    @Transactional
    public Session updateSession(Long sessionId, Session updatedSession) {
        return sessionRepository.findById(sessionId).map(session -> {
            session.setAcademicYear(updatedSession.getAcademicYear());
            session.setName(updatedSession.getName());
            session.setStartDate(updatedSession.getStartDate());
            session.setEndDate(updatedSession.getEndDate());
            return sessionRepository.save(session);
        }).orElseThrow(() -> new ResourceNotFoundException("Session not found"));
    }

    public void deleteSession(Long sessionId) {
        sessionRepository.deleteById(sessionId);
    }
}
