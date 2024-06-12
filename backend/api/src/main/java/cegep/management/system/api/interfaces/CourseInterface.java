package cegep.management.system.api.interfaces;

import cegep.management.system.api.model.Department;
import cegep.management.system.api.model.Teacher;

public interface CourseInterface {
    // Define common prop on eithjer course or courseDTO.
    String getName();

    String getSigle();

    Department getDepartment();

    Teacher getTeacher();
}
