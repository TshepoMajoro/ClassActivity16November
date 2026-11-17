package org.springtech.classactivity16november.Controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springtech.classactivity16november.DTO.StudentRequests;
import org.springtech.classactivity16november.DTO.StudentResponse;
import org.springtech.classactivity16november.Exceptions.DuplicateResourceException;
import org.springtech.classactivity16november.Exceptions.ResourceNotFoundException;
import org.springtech.classactivity16november.Services.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService service){
        studentService = service;
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents(){
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable long id) throws ResourceNotFoundException {
        StudentResponse response = studentService.getStudentById(id);
//        if (response == null){
//            return ResponseEntity.notFound().build();
//        }
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<Void> createStudent(@Valid @RequestBody StudentRequests student) throws DuplicateResourceException {
        StudentResponse response = studentService.createStudent(student);
//        if (response == null){
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStudent(@PathVariable long id, @Valid @RequestBody StudentRequests student) throws ResourceNotFoundException, DuplicateResourceException {
        StudentResponse response = studentService.updateStudent(id, student);
//        if (response == null){
//            return ResponseEntity.notFound().build();
//        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) throws ResourceNotFoundException {
        boolean response = studentService.deleteStudent(id);
//        if (!response){
//            return ResponseEntity.notFound().build();
//        }
        return ResponseEntity.noContent().build();
    }
}
