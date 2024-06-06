package cegep.management.system.api.model;

import java.time.LocalDate;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourse {

    @EmbeddedId
    private StudentCourseId id;

    @ManyToOne
    @MapsId("studentId")
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    private LocalDate endDate;

    private Integer grade;

    public StudentCourse(Long studentId, Long courseId, Session session, LocalDate endDate, Integer grade) {
        this.id = new StudentCourseId(studentId, courseId);
        this.session = session;
        this.endDate = endDate;
        this.grade = grade;
    }
}
