package app.data.appdata.repository;

import app.data.appdata.entity.Guardian;
import app.data.appdata.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void saveStudent() {
        Student student = Student.builder()
            .firstName("Sudar")
            .lastName("Kasi")
            .emailId("sudar@email.com")
            .build();
        studentRepository.save(student);
    }

    @Test
    public void saveStudentWithGuardian() {
        Guardian guardian = Guardian.builder()
            .email("gua@gmail.com")
            .mobile("999900099")
            .name("Dave Batt")
            .build();
        Student student = Student.builder()
            .firstName("jagan")
            .lastName("beram")
            .emailId("jaganb@email.com")
            .guardian(guardian)
            .build();
        studentRepository.save(student);
    }

    @Test
    public void testStudentAll() {
        var students = studentRepository.findAll();
        log.info("students {}", students);
    }

    @Test
    public void testFindByFirstName() {
        var students = studentRepository.findByFirstName("Sudar");
        log.info("students {}", students);
    }

    @Test
    public void testFindByFirstNameContaining() {
        var students = studentRepository.findByFirstNameContaining("Su");
        log.info("students {}", students);
    }

    @Test
    public void testFindByLastNameNonNull() {
        var students = studentRepository.findByLastNameNotNull();
        log.info("students {}", students);
    }

    @Test
    public void testFindBYGuardianName() {
        var students = studentRepository.findByGuardianName("Dave Batt");
        log.info("students {}", students);
    }

    @Test
    public void testFindByFirstNameAndLastName() {
        var student = studentRepository.findByFirstNameAndLastName("Sugan","");
        log.info("student {}", student);
    }

    @Test
    public void testGetStudentByEmailAddress() {
        var student = studentRepository.getStudentByEmailAddress("sudar@email.com");
        log.info("student {}", student);
    }

    @Test
    public void testGetStudentFirstNameByEmailAddress() {
        var firstName = studentRepository.getStudentFirstNameByEmailAddress("sudar@email.com");
        log.info("firstName: {}", firstName);
    }

    @Test
    public void testGetStudentByEmailAddressNative() {
        var student = studentRepository.getStudentByEmailAddressNative("sudar@email.com");
        log.info("student {}", student);
    }
}