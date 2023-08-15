package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student){
        logger.debug("createStudent method is called");
        return studentRepository.save(student);
    }

    public Student findStudent(long id){
        logger.debug("findStudent method is called");
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student){
        logger.debug("editStudent method is called");
        return studentRepository.save(student);
    }

    public void deleteStudent(long id){
        logger.debug("deleteStudent method is called");
        studentRepository.deleteById(id);
    }

    public Collection<Student> ageFilter(int age){
        logger.debug("ageFilter method is called");
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findStudentByAgeBetween(int min, int max){
        logger.debug("findStudentByAgeBetween method is called");
        return studentRepository.findStudentByAgeBetween(min, max);
    }

    public Faculty getStudentFaculty(Long id){
        logger.debug("getStudentFaculty method is called");
        return studentRepository.findById(id).get().getFaculty();
    }

    public List<Student> findStudentsByName(String name){
        logger.debug("findStudentsByName method is called");
        return studentRepository.findStudentsByName(name);
    }

    public int getNumberOfStudents() {
        logger.debug("getNumberOfStudents method is called");
        return studentRepository.getNumberOfStudents();
    }

    public int getAverageAgeOfStudents() {
        logger.debug("getAverageAgeOfStudents method is called");
        return studentRepository.getAverageAgeOfStudents();
    }

    public List<String> getFiveLastStudents() {
        logger.debug("getFiveLastStudents method is called");
        return studentRepository.getFiveLastStudents();
    }

    public List<String> getStudentsNamesStartingWithA(){
        return studentRepository.findAll()
                .stream()
                .parallel()
                .map(student -> student.getName())
                .filter(name -> name.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }

    public double getAverageAge2(){
        return studentRepository.findAll()
                .stream()
                .parallel()
                .mapToDouble(student -> student.getAge())
                .average()
                .orElse(Double.NaN);
    }

    public List<Student> getAllStudents(){
        List<Student> students = studentRepository.findAll();
        System.out.println(students.get(0));
        System.out.println(students.get(1));

        new Thread(() -> {
            System.out.println(students.get(2));
            System.out.println(students.get(3));
        }).start();

        new Thread(() -> {
            System.out.println(students.get(4));
            System.out.println(students.get(5));
        }).start();

        return students;
    }

    public List<Student> getAllStudentsSync(){
        List<Student> students = studentRepository.findAll();
        printStudents(students, 0);
        printStudents(students, 1);

        new Thread(() -> {
            printStudents(students, 2);
            printStudents(students, 3);
        }).start();

        new Thread(() -> {
            printStudents(students, 4);
            printStudents(students, 5);
        }).start();

        return students;
    }
    private synchronized void printStudents(List<Student> students, int index) {
        System.out.println(students.get(index));
    }
}
