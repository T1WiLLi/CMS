package cegep.management.system.api.service;

import org.springframework.stereotype.Service;

import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.Course;
import cegep.management.system.api.model.Student;
import cegep.management.system.api.model.StudentCourse;
import cegep.management.system.api.model.StudentCourseId;
import cegep.management.system.api.repo.CourseRepository;
import cegep.management.system.api.repo.StudentCourseRepository;
import cegep.management.system.api.repo.StudentRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final StudentCourseRepository studentCourseRepository;

    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository,
            StudentCourseRepository studentCourseRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.studentCourseRepository = studentCourseRepository;
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

    public List<Course> getCoursesForStudent(Long studentId) {
        List<StudentCourse> studentCourses = studentCourseRepository.findAllByIdStudentId(studentId);
        return studentCourses.stream()
                .map(sc -> courseRepository.findById(sc.getId().getCourseId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Transactional
    public Student addCourseToStudent(Long studentId, Long courseId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<Course> courseOpt = courseRepository.findById(courseId);

        if (studentOpt.isPresent() && courseOpt.isPresent()) {
            Student student = studentOpt.get();
            Course course = courseOpt.get();
            StudentCourse studentCourse = new StudentCourse(studentId, courseId);
            studentCourse.setStudent(student);
            studentCourse.setCourse(course);
            studentCourseRepository.save(studentCourse);
            return student;
        } else {
            throw new ResourceNotFoundException("Student or Course not found");
        }
    }

    @Transactional
    public void removeCourseFromStudent(Long studentId, Long courseId) {
        StudentCourseId id = new StudentCourseId(studentId, courseId);
        if (studentCourseRepository.existsById(id)) {
            studentCourseRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("StudentCourse not found");
        }
    }
}
