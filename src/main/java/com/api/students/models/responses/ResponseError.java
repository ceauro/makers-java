package com.api.students.models.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseError implements ResponseGeneral {
    @Schema(example = "mensaje de error", description = "describe el error ocurrido")
    private String message;
}
