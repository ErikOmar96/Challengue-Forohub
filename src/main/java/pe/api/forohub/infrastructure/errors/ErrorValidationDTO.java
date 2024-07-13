package pe.api.forohub.infrastructure.errors;

import org.springframework.validation.FieldError;

public record ErrorValidationDTO(String field, String error) {

    public ErrorValidationDTO(FieldError fieldError) {
        this(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
