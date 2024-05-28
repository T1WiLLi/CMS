package cegep.management.system.api.repo;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cegep.management.system.api.model.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("SELECT s FROM Session s WHERE :currentDate BETWEEN s.startDate AND s.endDate")
    Optional<Session> findSessionByDate(LocalDate currentDate);
}
