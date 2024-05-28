package cegep.management.system.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cegep.management.system.api.model.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}
