package cegep.management.system.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.Course;
import cegep.management.system.api.model.Evaluation;
import cegep.management.system.api.model.Student;
import cegep.management.system.api.model.StudentCourse;
import cegep.management.system.api.model.StudentCourseId;
import cegep.management.system.api.model.StudentEvaluation;
import cegep.management.system.api.model.StudentEvaluationId;
import cegep.management.system.api.repo.CourseRepository;
import cegep.management.system.api.repo.EvaluationRepository;
import cegep.management.system.api.repo.StudentCourseRepository;
import cegep.management.system.api.repo.StudentEvaluationRepository;
import cegep.management.system.api.repo.StudentRepository;
import jakarta.transaction.Transactional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final EvaluationRepository evaluationRepository;
    private final StudentEvaluationRepository studentEvaluationRepository;

    public StudentService(StudentRepository studentRepository,
            CourseRepository courseRepository,
            StudentCourseRepository studentCourseRepository,
            EvaluationRepository evaluationRepository,
            StudentEvaluationRepository studentEvaluationRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.studentCourseRepository = studentCourseRepository;
        this.evaluationRepository = evaluationRepository;
        this.studentEvaluationRepository = studentEvaluationRepository;
    }

    // Basic CRUD op for the studentList
    public List<Student> findAllStudent() {
        return this.studentRepository.findAll();
    }

    public Optional<Student> findStudentById(Long id) {
        return this.studentRepository.findById(id);
    }

    public Student createStudent(Student student) {
        return this.studentRepository.save(student);
    }

    @Transactional
    public Student updateStudent(Long studentId, Student updatedStudent) {
        return studentRepository.findById(studentId).map(student -> {
            student.setProgram(updatedStudent.getProgram());
            student.setSession(updatedStudent.getSession());
            student.setField(updatedStudent.getField());
            return studentRepository.save(student);
        }).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    public void deleteStudent(Long studentId) {
        if (studentRepository.existsById(studentId)) {
            studentRepository.deleteById(studentId);
        } else {
            throw new ResourceNotFoundException("Student not found");
        }
    }

    // Buisness logic for courses

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

    // Business Logic for Evaluations

    @Transactional
    public Student addEvaluationToStudent(Long studentId, Long evaluationId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<Evaluation> evaluationOpt = evaluationRepository.findById(evaluationId);

        if (studentOpt.isPresent() && evaluationOpt.isPresent()) {
            Student student = studentOpt.get();
            Evaluation evaluation = evaluationOpt.get();
            StudentEvaluation studentEvaluation = new StudentEvaluation(studentId, evaluationId);
            studentEvaluation.setStudent(student);
            studentEvaluation.setEvaluation(evaluation);
            studentEvaluationRepository.save(studentEvaluation);
            return student;
        } else {
            throw new ResourceNotFoundException("Student or Evaluation not found");
        }
    }

    @Transactional
    public void removeEvaluationFromStudent(Long studentId, Long evaluationId) {
        StudentEvaluationId id = new StudentEvaluationId(studentId, evaluationId);
        if (studentEvaluationRepository.existsById(id)) {
            studentEvaluationRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("StudentEvaluation not found");
        }
    }
}
