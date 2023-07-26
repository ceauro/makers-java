package com.api.students.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseStudent implements ResponseGeneral {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Date creationDate;
    private Date modificationDate;
}
