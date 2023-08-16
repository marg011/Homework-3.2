package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id){
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty){
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty){
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteFaculty(@PathVariable Long id){
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-by-color/{color}")
    public ResponseEntity colorFilter(@PathVariable String color){

        return ResponseEntity.ok(facultyService.colorFilter(color));
    }

    @GetMapping("/filter-by-color-or-name")
    public ResponseEntity findFacultiesByColorIgnoreCaseOrNameIgnoreCase(@RequestParam(required = false) String color,
                                                                         @RequestParam(required = false) String name){
        return ResponseEntity.ok(facultyService.findFacultiesByColorIgnoreCaseOrNameIgnoreCase(color, name));
    }

    @GetMapping("/students-by-id/{id}")
    public ResponseEntity findStudentsByFaculty(@PathVariable Long id){
        return ResponseEntity.ok(facultyService.getFacultyStudents(id));
    }

    @GetMapping("/longest-faculty-name")
    public String getLongestFacultyName(){
        return facultyService.getLongestFacultyName();
    }

    @GetMapping("/integer")
    public int getInteger(){
        return facultyService.getInteger();
    }


}
