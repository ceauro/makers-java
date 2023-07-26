package com.api.students.services;

import com.api.students.exceptions.StudentException;
import com.api.students.models.entities.Student;
import com.api.students.models.requests.RequestStudent;
import com.api.students.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository repository;
    private final ModelMapper mapper;

    public Student getStudentById(UUID id){
        Optional<Student> student = repository.findById(id);
        return student.orElseThrow(() -> new StudentException("Estudiante no existe", false));
    }

    public List<Student> getStudents(){
        return repository.findAll();
    }

    public UUID createStudent(RequestStudent request){
        Student student = mapper.map(request, Student.class);
        student.setCreationDate(new Date());
        Student entity = repository.save(student);
        return entity.getId();
    }

    public Student updateStudent(RequestStudent request, UUID id) {

        if(!repository.existsById(id)){
            throw new StudentException("Estudiante no se puede actualizar porque no existe", true);
        }

        Student student  = mapper.map(request, Student.class);
        student.setId(id);
        student.setModificationDate(new Date());
        student.setCreationDate(repository.findCreationDateById(id));
        return repository.save(student);
    }

    public void deleteStudent(UUID id)  {
        if(!repository.existsById(id)){
            throw new StudentException("Estudiante no se puede eliminar porque no existe", true);
        }

        repository.deleteById(id);
    }
}
