package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;


@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id){
        Student student = studentService.findStudent(id);
        if (student == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student){

        return studentService.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student){
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/get-by-age/{age}")
    public ResponseEntity ageFilter(@PathVariable Integer age){

        return ResponseEntity.ok(studentService.ageFilter(age));
    }

    @GetMapping("/find-students-by-age-between")
    public ResponseEntity findStudentByAgeBetween(@RequestParam int min, @RequestParam int max){
        return ResponseEntity.ok(studentService.findStudentByAgeBetween(min, max));
    }

    @GetMapping("/faculty-by-id/{id}")
    public ResponseEntity findFacultyByStudent(@PathVariable Long id){
        return ResponseEntity.ok(studentService.getStudentFaculty(id));
    }

    @GetMapping("/students-by-name/{name}")
    public ResponseEntity findStudentsByName(@PathVariable String name){
        return ResponseEntity.ok(studentService.findStudentsByName(name));
    }

    @GetMapping("/average-age-of-students")
    public int getAverageAgeOfStudents(){
        return studentService.getAverageAgeOfStudents();
    }

    @GetMapping("/five-last-students")
    public List<String> getFiveLastStudents(){
        return studentService.getFiveLastStudents();
    }

    @GetMapping("/number-of-students")
    public int getNumberOfStudents(){
        return studentService.getNumberOfStudents();
    }

    @GetMapping("/students-A")
    public List<String> getStudentsNamesStartingWithA(){
        return studentService.getStudentsNamesStartingWithA();
    }

    @GetMapping("/average-age2")
    public double getAverageAge2(){
        return studentService.getAverageAge2();
    }

    }

