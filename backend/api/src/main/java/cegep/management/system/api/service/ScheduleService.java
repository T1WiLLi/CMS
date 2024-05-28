package cegep.management.system.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.Schedule;
import cegep.management.system.api.repo.ScheduleRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Optional<Schedule> getScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId);
    }

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Transactional
    public Schedule updateSchedule(Long scheduleId, Schedule updatedSchedule) {
        return scheduleRepository.findById(scheduleId).map(schedule -> {
            schedule.setStudent(updatedSchedule.getStudent());
            schedule.setCourse(updatedSchedule.getCourse());
            schedule.setHourStart(updatedSchedule.getHourStart());
            schedule.setHourEnd(updatedSchedule.getHourEnd());
            return scheduleRepository.save(schedule);
        }).orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
    }

    public void deleteSchedule(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }
}
