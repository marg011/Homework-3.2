package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
public class FiveLastStudentsController {

    private final StudentService studentService;

    public FiveLastStudentsController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/five-last-students")
    public List<String> getFiveLastStudents(){
        return studentService.getFiveLastStudents();
    }
}
