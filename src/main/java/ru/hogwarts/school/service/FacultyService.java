package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;


@Service
public class FacultyService {

   private FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty){
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id){
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty){
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id){
        facultyRepository.deleteById(id);
    }

    public List<Faculty> colorFilter(String color){
        return facultyRepository.findByColor(color);
    }
}
