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
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FacultyRepository facultyRepository;

    @Test
    void contextLoad() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    public void testGetFaculty() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/1", String.class))
                .isNotNull();
    }

    @Test
    public void testPostFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setColor("blue");
        faculty.setName("Slytherin");
        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, String.class))
                .isNotNull();
    }

    @Test
    public void testPutFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setColor("green");
        faculty.setName("Gryffindor");
        ResponseEntity<Faculty> exchange = restTemplate.exchange(
                "http://localhost:" + port + "/faculty",
                HttpMethod.PUT,
                new HttpEntity<>(faculty),
                Faculty.class);
        Assertions
                .assertThat(exchange.getBody())
                .isNotNull();
        Assertions
                .assertThat(exchange.getBody().getName())
                .isEqualTo(faculty.getName());
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        Long id = facultyRepository.findFacultyByName("Gryffindor").getId();
        Faculty faculty = new Faculty();
        faculty.setColor("green");
        faculty.setName("Gryffindor");
        faculty.setId(id);
        ResponseEntity<Student> exchange = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + id,
                HttpMethod.DELETE,
                new HttpEntity<>(faculty),
                Student.class);
        Assertions
                .assertThat(exchange.getBody())
                .isNull();
    }

    @Test
    public void testGetFacultyByColor() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/get-by-color/blue", String.class))
                .isNotNull();
    }

    @Test
    public void testGetFacultiesByColorOrName() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/filter-by-color-or-name?color=blue", String.class))
                .isNotNull();

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/filter-by-color-or-name?name=Slytherin", String.class))
                .isNotNull();
    }

    @Test
    public void testGetStudentsByFaculty(){
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/students-by-id/66", String.class))
                .isNotNull();
    }


}
