package cegep.management.system.api.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Session session;

    @ManyToOne
    private Course course;

    private Date endDate;
    private String grade;
}