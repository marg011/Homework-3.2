package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void contextLoad() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testGetStudent() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/1", String.class))
                .isNotNull();
    }

    @Test
    public void testPostStudent() throws Exception {
        Student student = new Student();
        student.setAge(15);
        student.setName("Harry");
        student.setId(3L);
        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student, String.class))
                .isNotNull();
    }

    @Test
    public void testPutStudent() throws Exception {
        testPostStudent();
        Student student = new Student();
        student.setAge(15);
        student.setName("Harry");
        ResponseEntity<Student> exchange = restTemplate.exchange(
                "http://localhost:" + port + "/student",
                HttpMethod.PUT,
                new HttpEntity<>(student),
                Student.class);
        Assertions
                .assertThat(exchange.getBody())
                .isNotNull();
        Assertions
                .assertThat(exchange.getBody().getName())
                .isEqualTo(student.getName());
    }

    @Test
    public void testDeleteStudent() throws Exception {
        Long id = studentRepository.findStudentByName("Harry").getId();
        Student student = new Student();
        student.setAge(15);
        student.setName("Harry");
        student.setId(id);
        ResponseEntity<Student> exchange = restTemplate.exchange(
                "http://localhost:" + port + "/student/" + id,
                HttpMethod.DELETE,
                new HttpEntity<>(student),
                Student.class);
        Assertions
                .assertThat(exchange.getBody())
                .isNull();
    }

    @Test
    public void testGetStudentByAge() throws Exception {
        testPostStudent();
        int age = studentRepository.findStudentByName("Harry").getAge();
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/get-by-age" + age, String.class))
                .isNotNull();
    }

    @Test
    public void testGetStudentsByAgeBetween(){
        int age = studentRepository.findStudentByName("Harry").getAge();
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/find-students-by-age-between?min=10&max=16", String.class))
                .isNotNull();
    }

    @Test
    public void testGetFacultyByStudent(){
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/faculty-by-id/2", Faculty.class))
                .isNotNull();
    }




}
