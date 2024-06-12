package cegep.management.system.api.dto;

import org.springframework.beans.factory.annotation.Autowired;

import cegep.management.system.api.interfaces.CourseInterface;
import cegep.management.system.api.model.Department;
import cegep.management.system.api.model.Teacher;
import cegep.management.system.api.repo.DepartmentRepository;
import cegep.management.system.api.repo.TeacherRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO implements CourseInterface {

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    TeacherRepository teacherRepository;

    private String name;
    private String sigle;
    private Long departmentId;
    private Long teacherId;

    public Department getDepartment() {
        return departmentRepository.findById(this.departmentId)
                .orElseThrow(() -> new RuntimeException("Can't find a department with id: " + this.departmentId));
    }

    public Teacher getTeacher() {
        return teacherRepository.findById(this.teacherId)
                .orElseThrow(() -> new RuntimeException("Can't find a teacher with id: " + this.teacherId));
    }
}
