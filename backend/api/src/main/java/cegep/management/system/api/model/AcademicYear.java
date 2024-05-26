package cegep.management.system.api.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AcademicYear {
    @Id
    private String yearId;
    private Date startDate;
    private Date endDate;
}
