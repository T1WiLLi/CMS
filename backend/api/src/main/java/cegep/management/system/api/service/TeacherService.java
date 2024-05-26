package cegep.management.system.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.Teacher;
import cegep.management.system.api.repo.TeacherRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> getTeacherById(Long teacherId) {
        return teacherRepository.findById(teacherId);
    }

    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Transactional
    public Teacher updateTeacher(Long teacherId, Teacher updatedTeacher) {
        return teacherRepository.findById(teacherId).map(teacher -> {
            teacher.setEmployee(updatedTeacher.getEmployee());
            teacher.setDepartment(updatedTeacher.getDepartment());
            teacher.setSeniority(updatedTeacher.getSeniority());
            return teacherRepository.save(teacher);
        }).orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
    }

    public void deleteTeacher(Long teacherId) {
        teacherRepository.deleteById(teacherId);
    }
}
