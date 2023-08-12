package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;


@Service
public class FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyService.class);
   private FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty){
        logger.debug("createFaculty method is called");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id){
        logger.debug("findFaculty method is called");
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty){
        logger.debug("editFaculty method is called");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id){
        logger.debug("deleteFaculty method is called");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> colorFilter(String color){
        logger.debug("colorFilter method is called");
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> findFacultiesByColorIgnoreCaseOrNameIgnoreCase(String color, String name){
        logger.debug("findFacultiesByColorIgnoreCaseOrNameIgnoreCase method is called");
        return facultyRepository.findFacultiesByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }

    public Collection<Student> getFacultyStudents(Long id){
        logger.debug("getFacultyStudents method is called");
        return facultyRepository.findById(id).map(Faculty::getStudents).orElse(null);
    }

    public Faculty findFacultyByName(String name){
        logger.debug("findFacultyByName method is called");
        return facultyRepository.findFacultyByName(name);
    }
}
