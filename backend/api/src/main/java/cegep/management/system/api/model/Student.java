package cegep.management.system.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Student {
    @Id
    private Long userId;

    @ManyToOne
    private User user;

    private int admissionNumber;

    @ManyToOne
    private Program program;

    @ManyToOne
    private Session session;

    private String field;
}
