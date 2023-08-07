package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student){
        return studentRepository.save(student);
    }

    public Student findStudent(long id){
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student){
        return studentRepository.save(student);
    }

    public void deleteStudent(long id){
        studentRepository.deleteById(id);
    }

    public Collection<Student> ageFilter(int age){
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findStudentByAgeBetween(int min, int max){
        return studentRepository.findStudentByAgeBetween(min, max);
    }

    public Faculty getStudentFaculty(Long id){
        return studentRepository.findById(id).get().getFaculty();
    }

    public Student findStudentByName(String name){
        return studentRepository.findStudentByName(name);
    }

    public int getNumberOfStudents() {
        return studentRepository.getNumberOfStudents();
    }

    public int getAverageAgeOfStudents() {
        return studentRepository.getAverageAgeOfStudents();
    }

    public List<String> getFiveLastStudents() {
        return studentRepository.getFiveLastStudents();
    }
}
