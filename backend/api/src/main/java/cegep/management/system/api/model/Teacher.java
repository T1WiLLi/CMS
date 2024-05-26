package cegep.management.system.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    private int teacherId;

    @OneToOne
    @JoinColumn(name = "employee_id", unique = true) // Ensuring unique constraint
    private Employee employee;

    @ManyToOne
    private Department department;

    private String seniority;
}
