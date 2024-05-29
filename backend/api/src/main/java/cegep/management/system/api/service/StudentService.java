package cegep.management.system.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cegep.management.system.api.dto.StudentDTO;
import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.Evaluation;
import cegep.management.system.api.model.Person;
import cegep.management.system.api.model.Program;
import cegep.management.system.api.model.Session;
import cegep.management.system.api.model.Student;
import cegep.management.system.api.model.StudentEvaluation;
import cegep.management.system.api.model.StudentEvaluationId;
import cegep.management.system.api.repo.EvaluationRepository;
import cegep.management.system.api.repo.ProgramRepository;
import cegep.management.system.api.repo.SessionRepository;
import cegep.management.system.api.repo.StudentEvaluationRepository;
import cegep.management.system.api.repo.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final EvaluationRepository evaluationRepository;
    private final StudentEvaluationRepository studentEvaluationRepository;
    private final PersonService personService;
    private final ProgramRepository programRepository;
    private final SessionRepository sessionRepository;
    private final SessionService sessionService;

    public StudentService(StudentRepository studentRepository,
            EvaluationRepository evaluationRepository,
            StudentEvaluationRepository studentEvaluationRepository,
            PersonService personService, ProgramRepository programRepository,
            SessionRepository sessionRepository, SessionService sessionService) {
        this.studentRepository = studentRepository;
        this.evaluationRepository = evaluationRepository;
        this.studentEvaluationRepository = studentEvaluationRepository;
        this.personService = personService;
        this.programRepository = programRepository;
        this.sessionRepository = sessionRepository;
        this.sessionService = sessionService;
    }

    // Basic CRUD op for the studentList
    public List<Student> findAllStudent() {
        return this.studentRepository.findAll();
    }

    public Optional<Student> findStudentById(Long id) {
        return this.studentRepository.findById(id);
    }

    public Student createStudent(StudentDTO studentDTO) {
        Person person = new Person(studentDTO.getFirstName(), studentDTO.getLastName(), studentDTO.getEmail(),
                studentDTO.getPhone(), studentDTO.getPassword(), studentDTO.getDateOfBirth());

        if (this.personService.createUser(person) != null) {
            Program program = this.programRepository.findById(studentDTO.getProgramId())
                    .orElseThrow(
                            () -> new EntityNotFoundException(
                                    "Program not found with ID: " + studentDTO.getProgramId()));

            Session session;
            if (studentDTO.getSessionId() == null) {
                session = this.sessionService.getOrCreateSessionForCurrentDate();
            } else {
                session = this.sessionRepository.findById(studentDTO.getSessionId())
                        .orElseThrow(
                                () -> new EntityNotFoundException(
                                        "Session not found with ID: " + studentDTO.getSessionId()));
            }

            Student student = new Student();
            student.setPerson(person);
            student.setProgram(program);
            student.setSession(session);
            student.setField(studentDTO.getField());

            return this.studentRepository.save(student);
        }
        return null;

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

    @Transactional
    public Student updateStudentProgram(Long studentId, Long programId) {
        return studentRepository.findById(studentId)
                .map(student -> {
                    Program program = programRepository.findById(programId)
                            .orElseThrow(() -> new ResourceNotFoundException("Program not found"));
                    student.setProgram(program);
                    return studentRepository.save(student);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    public void deleteStudent(Long studentId) {
        if (studentRepository.existsById(studentId)) {
            studentRepository.deleteById(studentId);
        } else {
            throw new ResourceNotFoundException("Student not found");
        }
    }

    // Business Logic for Evaluations

    public List<Evaluation> getEvaluationsForStudent(Long studentId) {
        List<StudentEvaluation> studentEvaluations = studentEvaluationRepository.findAllByIdStudentId(studentId);
        return studentEvaluations.stream()
                .map(se -> evaluationRepository.findById(se.getId().getEvaluationId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

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
