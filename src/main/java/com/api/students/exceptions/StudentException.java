package com.api.students.exceptions;

import lombok.Getter;

@Getter
public class StudentException extends RuntimeException{

    private final boolean conflict;
    public StudentException(String message, boolean existsStudent){
        super(message);
        this.conflict = existsStudent;
    }
}
