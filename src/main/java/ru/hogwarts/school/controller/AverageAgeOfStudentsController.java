package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.StudentService;

@RestController
public class AverageAgeOfStudentsController {

    private final StudentService studentService;

    public AverageAgeOfStudentsController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/average-age-of-students")
    public int getAverageAgeOfStudents(){
        return studentService.getAverageAgeOfStudents();
    }
}
