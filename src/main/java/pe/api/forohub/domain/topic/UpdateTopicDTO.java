package pe.api.forohub.domain.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateTopicDTO (
    @NotNull
    Long id,
    @NotBlank
    String title,
    @NotBlank
    String message,
    @NotNull
    TopicStatus status
){
}
