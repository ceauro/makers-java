package com.api.students.models.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseErrors implements ResponseGeneral {
    @Schema(description = "Lista de errores ocurridos")
    private List<ResponseError> errors;
}
