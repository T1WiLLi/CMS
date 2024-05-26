package cegep.management.system.api.service;

import org.springframework.stereotype.Service;

import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.Course;
import cegep.management.system.api.repo.CourseRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return this.courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return this.courseRepository.findById(id);
    }

    public Course createCourse(Course course) {
        return this.courseRepository.save(course);
    }

    @Transactional
    public Course updateCourse(Long id, Course courseDetails) {
        return this.courseRepository.findById(id)
                .map(course -> {
                    course.setName(courseDetails.getName());
                    course.setSigle(courseDetails.getSigle());
                    course.setDepartment(courseDetails.getDepartment());
                    course.setTeacher(courseDetails.getTeacher());
                    return this.courseRepository.save(course);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));
    }

    public void deleteCourse(Long id) {
        if (this.courseRepository.existsById(id)) {
            this.courseRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Course not found with id " + id);
        }
    }
}
