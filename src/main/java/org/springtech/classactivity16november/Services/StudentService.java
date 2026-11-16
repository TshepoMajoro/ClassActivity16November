package org.springtech.classactivity16november.Services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springtech.classactivity16november.DTO.StudentRequests;
import org.springtech.classactivity16november.DTO.StudentResponse;
import org.springtech.classactivity16november.Entities.Students;
import org.springtech.classactivity16november.Exceptions.DuplicateResourceException;
import org.springtech.classactivity16november.Exceptions.ResourceNotFoundException;
import org.springtech.classactivity16november.Repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private StudentRepository repo;

    public StudentService(StudentRepository repository){
        repo = repository;
    }

    private StudentResponse serializeStudentToStudentResponse(Students student){
        return new StudentResponse(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail()
        );
    }

    private Students serializeStudentRequestToStudent(StudentRequests request){
        Students student = new Students();
        student.setEmail(request.email());
        student.setFirstName(request.firstName());
        student.setLastName(request.lastName());
        return student;
    }

    @Transactional
    public StudentResponse createStudent(StudentRequests request) throws DuplicateResourceException {
        if (repo.existsByEmail(request.email())){
            try{
                throw new DuplicateResourceException("A student with this email already exists");
            }catch (DuplicateResourceException e){
                System.out.println(e.getMessage());
            }
            return null;
        }
        Students student = repo.save(serializeStudentRequestToStudent(request));
        return serializeStudentToStudentResponse(student);
    }

    @Transactional
    public List<StudentResponse> getAllStudents(){
       List<Students> students = repo.findAll();
       return students.stream().map((Students stu) -> serializeStudentToStudentResponse(stu)).toList();
    }

    @Transactional
    public StudentResponse getStudentById(Long id) throws ResourceNotFoundException {
        Optional<Students> studentOptional = repo.findById(id);
        if (studentOptional.isEmpty()){
            try{
                throw new ResourceNotFoundException("Student of id "+id+" does not exist.");
            }catch (ResourceNotFoundException e){
                System.out.println("Student of id "+id+" does not exist.");
            }
            return null;
        }
        return serializeStudentToStudentResponse(studentOptional.get());
    }

    @Transactional
    public StudentResponse updateStudent(Long id, StudentRequests request) throws ResourceNotFoundException, DuplicateResourceException {
        Optional<Students> studentOptional = repo.findById(id);

        if (studentOptional.isEmpty()){
            try{
                throw new ResourceNotFoundException("Student of id "+id+" does not exist.");
            }catch (ResourceNotFoundException e){
                System.out.println("Student of id "+id+" does not exist.");
            }
            return null;
        }

        Students student = studentOptional.get();
        Long studentId = student.getId();
//        String studentEmail = request.email();
//
//        if (repo.existsByEmailAndIdNot(studentEmail, studentId)){
//            throw new DuplicateResourceException("Another student is already using email address "+studentEmail);
//        }

        student = serializeStudentRequestToStudent(request);
        student.setId(studentId);

        student = repo.save(student);
        return serializeStudentToStudentResponse(student);
    }

    @Transactional
    public boolean deleteStudent(Long id) throws ResourceNotFoundException {
        Optional<Students> studentOptional = repo.findById(id);
        if (studentOptional.isEmpty()){
            try{
                throw new ResourceNotFoundException("Student of id "+id+" does not exist.");
            }catch (ResourceNotFoundException e){
                System.out.println("Student of id "+id+" does not exist.");
            }
            return false;
        }
        repo.delete(studentOptional.get());
        return true;
    }
}
