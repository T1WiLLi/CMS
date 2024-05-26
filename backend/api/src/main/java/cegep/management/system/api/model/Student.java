package cegep.management.system.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    private Long studentId;

    @OneToOne
    @JoinColumn(name = "person_id", unique = true) // Ensuring unique constraint
    private Person person;

    @ManyToOne
    private Program program;

    @ManyToOne
    private Session session;

    private String field;
}
