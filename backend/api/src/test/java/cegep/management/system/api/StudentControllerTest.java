package cegep.management.system.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import cegep.management.system.api.controllers.StudentController;
import cegep.management.system.api.dto.StudentDTO;
import cegep.management.system.api.model.Person;
import cegep.management.system.api.model.Program;
import cegep.management.system.api.model.Session;
import cegep.management.system.api.model.Student;
import cegep.management.system.api.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

/**
 * Test class for StudentController
 */
@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    public void testCreateStudent() throws Exception {
        // Arrange
        StudentDTO studentDTO = createStudentDTO();
        Student student = createStudent(studentDTO);
        Mockito.when(studentService.createStudent(Mockito.any(StudentDTO.class))).thenReturn(student);

        // Convert StudentDTO to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String studentDTOJson = objectMapper.writeValueAsString(studentDTO);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(studentDTOJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.person.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.person.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.person.email").value("john.doe@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.program").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.session").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.field").value("Computer Science"));
    }

    private StudentDTO createStudentDTO() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstName("John");
        studentDTO.setLastName("Doe");
        studentDTO.setEmail("john.doe@example.com");
        studentDTO.setPhone("1234567890");
        studentDTO.setPassword("password");
        studentDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));
        studentDTO.setProgramId(1L);
        return studentDTO;
    }

    private Student createStudent(StudentDTO studentDTO) {
        Person person = new Person();
        person.setFirstName(studentDTO.getFirstName());
        person.setLastName(studentDTO.getLastName());
        person.setEmail(studentDTO.getEmail());
        person.setPhone(studentDTO.getPhone());
        person.setPassword(studentDTO.getPassword());
        person.setDateOfBirth(studentDTO.getDateOfBirth());

        Student student = new Student();
        student.setPerson(person);
        student.setProgram(new Program());
        student.setSession(new Session());
        student.setField("Computer Science");
        return student;
    }
}