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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class FacultyControllerMockTests {
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
    public void testGetFaculty() throws Exception {
        final String name = "Gryffindor";
        final String color = "green";
        final long id = 1L;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.findById(any(long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void testPostFaculty() throws Exception {
        final String name = "Gryffindor";
        final String color = "green";
        final long id = 1L;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void testPutFaculty() throws Exception {
        final String name = "Gryffindor";
        final String color = "green";
        final long id = 1L;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void testGetFacultiesByColor() throws Exception {
        final String name1 = "Gryffindor";
        final String color = "green";
        final long id1 = 1;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id1);
        facultyObject.put("name", name1);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id1);
        faculty.setName(name1);
        faculty.setColor(color);

        final String name2 = "Slytherin";
        final long id2 = 2;

        JSONObject facultyObject2 = new JSONObject();
        facultyObject2.put("id", id2);
        facultyObject2.put("name", name2);
        facultyObject2.put("color", color);

        Faculty faculty2 = new Faculty();
        faculty2.setId(id2);
        faculty2.setName(name2);
        faculty2.setColor(color);

        List<JSONObject> jsonObjects = new ArrayList<>();
        jsonObjects.add(facultyObject);
        jsonObjects.add(facultyObject2);

        List<Faculty> faculties = new ArrayList<>();
        faculties.add(faculty);
        faculties.add(faculty2);

        when(facultyRepository.findByColor("green")).thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/get-by-color/green")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(faculties.size()))
                .andExpect(jsonPath("$[0]").value(faculties.get(0)))
                .andExpect(jsonPath("$[1]").value(faculties.get(1)));
    }

    @Test
    public void testGetStudentsByColorOrName() throws Exception {
        final String name1 = "Gryffindor";
        final String color1 = "green";
        final long id1 = 1;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id1);
        facultyObject.put("name", name1);
        facultyObject.put("color", color1);

        Faculty faculty = new Faculty();
        faculty.setId(id1);
        faculty.setName(name1);
        faculty.setColor(color1);

        final String name2 = "Slytherin";
        final long id2 = 2;
        final String color2 = "green";
        JSONObject facultyObject2 = new JSONObject();
        facultyObject2.put("id", id2);
        facultyObject2.put("name", name2);
        facultyObject2.put("color", color2);

        Faculty faculty2 = new Faculty();
        faculty2.setId(id2);
        faculty2.setName(name2);
        faculty2.setColor(color2);

        List<JSONObject> jsonObjects = new ArrayList<>();
        jsonObjects.add(facultyObject);
        jsonObjects.add(facultyObject2);

        List<Faculty> faculties = new ArrayList<>();
        faculties.add(faculty);
        faculties.add(faculty2);

        when(facultyRepository.findFacultiesByColorIgnoreCaseOrNameIgnoreCase("green", "Gryffindor")).thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/filter-by-color-or-name?color=green&name=Gryffindor")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(faculties.size()))
                .andExpect(jsonPath("$[0]").value(faculties.get(0)))
                .andExpect(jsonPath("$[1]").value(faculties.get(1)));
    }

    @Test
    public void testGetStudentsByFaculty() throws Exception {
        final String name1 = "Gryffindor";
        final String color1 = "green";
        final long id1 = 1L;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id1);
        facultyObject.put("name", name1);
        facultyObject.put("color", color1);

        Faculty faculty = new Faculty();
        faculty.setId(id1);
        faculty.setName(name1);
        faculty.setColor(color1);
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L,"Harry", 17));
        students.add(new Student(2L,"Mary",17));
        faculty.setStudents(students);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/students-by-id/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(faculty.getStudents().size()));
    }

}
