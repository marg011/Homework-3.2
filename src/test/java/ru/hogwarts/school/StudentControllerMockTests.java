package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class StudentControllerMockTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private StudentService studentService;

    @SpyBean
    private AvatarService avatarService;

    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void testGetStudent() throws Exception {
        final String name = "alice";
        final int age = 25;
        final long id = 1;

        JSONObject studentObject = new JSONObject();
        studentObject.put("id", id);
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.findById(any(long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void testPostStudent() throws Exception {
        final String name = "alice";
        final int age = 25;
        final long id = 1;

        JSONObject studentObject = new JSONObject();
        studentObject.put("id", id);
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void testPutStudent() throws Exception {
        final String name = "alice";
        final int age = 25;
        final long id = 1;

        JSONObject studentObject = new JSONObject();
        studentObject.put("id", id);
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void testGetStudentsByAge() throws Exception {
        final String name1 = "alice";
        final int age = 25;
        final long id1 = 1;

        JSONObject studentObject = new JSONObject();
        studentObject.put("id", id1);
        studentObject.put("name", name1);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id1);
        student.setName(name1);
        student.setAge(age);

        final String name2 = "harry";
        final long id2 = 2;

        JSONObject studentObject2 = new JSONObject();
        studentObject2.put("id", id2);
        studentObject2.put("name", name2);
        studentObject2.put("age", age);

        Student student2 = new Student();
        student2.setId(id2);
        student2.setName(name2);
        student2.setAge(age);

        List<JSONObject> jsonObjects = new ArrayList<>();
        jsonObjects.add(studentObject);
        jsonObjects.add(studentObject2);

        List<Student> students = new ArrayList<>();
        students.add(student);
        students.add(student2);

        when(studentRepository.findByAge(25)).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/get-by-age/25")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(students.size()))
                .andExpect(jsonPath("$[0]").value(students.get(0)))
                .andExpect(jsonPath("$[1]").value(students.get(1)));
    }

    @Test
    public void testGetStudentsByAgeBetween() throws Exception {
        final String name1 = "alice";
        final int age1 = 25;
        final long id1 = 1;

        JSONObject studentObject1 = new JSONObject();
        studentObject1.put("id", id1);
        studentObject1.put("name", name1);
        studentObject1.put("age", age1);

        Student student = new Student();
        student.setId(id1);
        student.setName(name1);
        student.setAge(age1);

        final String name2 = "harry";
        final long id2 = 2;
        final int age2 = 22;

        JSONObject studentObject2 = new JSONObject();
        studentObject2.put("id", id2);
        studentObject2.put("name", name2);
        studentObject2.put("age", age2);

        Student student2 = new Student();
        student2.setId(id2);
        student2.setName(name2);
        student2.setAge(age2);

        List<JSONObject> jsonObjects = new ArrayList<>();
        jsonObjects.add(studentObject1);
        jsonObjects.add(studentObject2);

        List<Student> students = new ArrayList<>();
        students.add(student);
        students.add(student2);

        when(studentRepository.findStudentByAgeBetween(20, 30)).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/find-students-by-age-between?min=20&max=30")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(students.size()))
                .andExpect(jsonPath("$[0]").value(students.get(0)))
                .andExpect(jsonPath("$[1]").value(students.get(1)));
    }

    @Test
    public void testGetFacultyByStudent() throws Exception {
        final String name = "alice";
        final int age = 25;
        final long id = 1L;

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setFaculty(new Faculty(1L,"Gryffindor", "green"));

        when(studentRepository.findById(any(long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/faculty-by-id/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Gryffindor"))
                .andExpect(jsonPath("$.age").value("green"));
    }
}
