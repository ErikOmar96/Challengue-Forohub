package pe.api.forohub.domain.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAnswerDTO (
    @NotBlank
    String message,
    @NotBlank
    String solution,
    @NotNull
    Long idTopic,
    @NotNull
    Long idUser
){
}
