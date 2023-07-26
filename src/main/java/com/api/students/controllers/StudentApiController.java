package com.api.students.controllers;


import com.api.students.exceptions.StudentException;
import com.api.students.models.entities.Student;
import com.api.students.models.requests.RequestStudent;
import com.api.students.models.responses.ResponseError;
import com.api.students.models.responses.ResponseGeneral;
import com.api.students.models.responses.ResponseStudent;
import com.api.students.models.responses.ResponseStudents;
import com.api.students.services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@Tag(name = "Students", description = "Aquí encontraras los endpoints de la API de Students")
@RestController
@RequestMapping(path = "/api/student")
@AllArgsConstructor
@Validated
public class StudentApiController {

    private final StudentService service;
    private final ModelMapper mapper;

    @Operation(
            summary = "Obtiene un estudiante",
            description = "Obtiene un estudiante, de acuerdo al id enviado",
            tags = "Students"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ResponseStudent.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ResponseError.class), mediaType = "application/json") }) })
    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseGeneral> getStudent(@PathVariable UUID id){
        Student student = service.getStudentById(id);
        return ResponseEntity.ok().body(mapper.map(student, ResponseStudent.class));
    }

    @Operation(
            summary = "Obtiene todo los estudiantes",
            description = "Obtiene todos los estudiantes almacenados en la base de datos",
            tags = "Students"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ResponseStudents.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ResponseError.class), mediaType = "application/json") }) })
    @GetMapping(path = "/all")
    public ResponseEntity<ResponseGeneral> getStudents(){
        List<Student> students = service.getStudents();
        if(students.isEmpty()){
            throw new StudentException("No hay estudiantes creados.", false);
        }

        return ResponseEntity
                .ok()
                .body(new ResponseStudents(students
                        .stream()
                        .map(student -> mapper.map(student, ResponseStudent.class))
                        .toList())
                );
    }

    @Operation(
            summary = "Crea un nuevo estudiante",
            description = "Crea un nuevo estudiante con la data enviada como paramentro",
            tags = "Students"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ResponseError.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", content = { @Content(schema = @Schema()) }) })
    @PostMapping
    public ResponseEntity<ResponseGeneral> postStudent(@RequestBody @Valid RequestStudent student) throws URISyntaxException {
       String id = service.createStudent(student).toString();
       return ResponseEntity.created(new URI("/api/student/" + id)).build();
    }

    @Operation(
            summary = "Actualiza un estudiante",
            description = "Actualiza los datos del estudiante, de acuerdo al id enviado",
            tags = "Students"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ResponseStudent.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ResponseError.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", content = { @Content(schema = @Schema(implementation = ResponseError.class), mediaType = "application/json") }) })
    @PutMapping(path = "/{id}")
    public ResponseEntity<ResponseGeneral> putStudent(@RequestBody @Valid RequestStudent user,
                                                      @PathVariable UUID id){
        service.updateStudent(user, id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Actualiza un estudiante",
            description = "Actualiza los datos del estudiante, de acuerdo al id enviado",
            tags = "Students"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ResponseStudent.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", content = { @Content(schema = @Schema(implementation = ResponseError.class), mediaType = "application/json") }) })
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ResponseGeneral> deleteStudent(@PathVariable UUID id){
        service.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
