package pe.api.forohub.controllers.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record CreateTopicDTO(
    @NotNull
    Long idUser,
    @NotNull
    Long idCourse,
    @NotBlank
    String title,
    @NotBlank
    String message
) {
}
