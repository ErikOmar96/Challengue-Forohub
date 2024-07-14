package pe.api.forohub.domain.subject;

import jakarta.validation.constraints.NotBlank;

public record CreateSubjectDTO (
    @NotBlank
    String name,
    @NotBlank
    String category
){

}
