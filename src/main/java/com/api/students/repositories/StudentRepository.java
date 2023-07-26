package com.api.students.repositories;

import com.api.students.models.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

    @Query("SELECT e.creationDate FROM Student e WHERE e.id = :id")
    Date findCreationDateById(@Param("id") UUID id);
}
