package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findByColor(String color);

    //Collection<Faculty> findFacultiesByNameIgnoreCase(String name);

    Collection<Faculty> findFacultiesByColorIgnoreCaseOrNameIgnoreCase(String color,
                                                                       String name);

    Faculty findFacultyByName(String name);
}
