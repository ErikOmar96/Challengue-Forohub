package pe.api.forohub.domain.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pe.api.forohub.domain.answer.ResponseAnswerDTO;
import pe.api.forohub.domain.course.ResponseCourseDTO;
import pe.api.forohub.domain.user.ResponseUserDTO;

import java.time.LocalDateTime;
import java.util.List;

public record ResponseTopicDTO(
    Long id,
    String title,
    String message,
    LocalDateTime createdAt,
    TopicStatus status,
    ResponseUserDTO author,
    ResponseCourseDTO course,
    List<ResponseAnswerDTO> answers
) {
}
