package cegep.management.system.api.service;

import org.springframework.stereotype.Service;

import cegep.management.system.api.dto.CourseDTO;
import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.interfaces.CourseInterface;
import cegep.management.system.api.model.Course;
import cegep.management.system.api.model.Department;
import cegep.management.system.api.model.Session;
import cegep.management.system.api.model.Student;
import cegep.management.system.api.model.StudentCourse;
import cegep.management.system.api.model.StudentCourseId;
import cegep.management.system.api.model.Teacher;
import cegep.management.system.api.repo.CourseRepository;
import cegep.management.system.api.repo.StudentCourseRepository;
import cegep.management.system.api.repo.StudentRepository;
import cegep.management.system.api.repo.TeacherRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final TeacherRepository teacherRepository;
    private final DepartmentService departmentService;
    private final SessionService sessionService;

    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository,
            StudentCourseRepository studentCourseRepository, DepartmentService departmentService,
            TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.studentCourseRepository = studentCourseRepository;
        this.teacherRepository = teacherRepository;
        this.departmentService = departmentService;
        this.sessionService = null;
    }

    public List<Course> getAllCourses() {
        return this.courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return this.courseRepository.findById(id);
    }

    public Course createCourse(CourseDTO courseDTO) {
        Teacher teacher = teacherRepository.findById(courseDTO.getTeacherId()).orElseThrow(
                () -> new RuntimeException("Can't find a teacher with the id: " + courseDTO.getTeacherId()));
        Department department = departmentService.getDepartmentById(courseDTO.getDepartmentId()).orElseThrow(
                () -> new RuntimeException("Can't find a department with the id: " + courseDTO.getDepartmentId()));
        Course course = new Course(courseDTO.getName(), courseDTO.getSigle(), department, teacher);
        return courseRepository.save(course);
    }

    @Transactional
    public Course updateCourse(Long id, CourseInterface courseDetails) {
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

        if (!studentOpt.isPresent()) {
            throw new ResourceNotFoundException("Student not found with id " + studentId);
        }

        if (!courseOpt.isPresent()) {
            throw new ResourceNotFoundException("Course not found with id " + courseId);
        }

        Session session = sessionService.getOrCreateSessionForCurrentDate();

        Student student = studentOpt.get();
        Course course = courseOpt.get();

        StudentCourse studentCourse = new StudentCourse(studentId, courseId, session, session.getEndDate(), 0);
        studentCourse.setStudent(student);
        studentCourse.setCourse(course);

        studentCourseRepository.save(studentCourse);

        return student;
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
