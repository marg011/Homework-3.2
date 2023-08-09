package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findByAge(int age);

    Collection<Student> findStudentByAgeBetween(int min, int max);

    List<Student> findStudentsByName(String name);
    @Query(value="SELECT COUNT(*) as numberOfStudents FROM student", nativeQuery = true)
    int getNumberOfStudents();

    @Query(value="SELECT AVG(age) as averageAge FROM student", nativeQuery = true)
    int getAverageAgeOfStudents();

    @Query(value="SELECT name FROM student ORDER BY Id DESC LIMIT 5", nativeQuery = true)
    List<String> getFiveLastStudents();
}
