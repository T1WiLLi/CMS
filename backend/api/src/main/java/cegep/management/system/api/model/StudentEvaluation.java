package cegep.management.system.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class StudentEvaluation {
    @Id
    @ManyToOne
    private int admissionNumber;

    @Id
    @ManyToOne
    private int evaluationId;
}
