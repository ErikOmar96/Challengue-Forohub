package pe.api.forohub.domain.topic;

import pe.api.forohub.domain.answer.ResponseAnswerDTO;
import pe.api.forohub.domain.subject.ResponseSubjectDTO;
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
    ResponseSubjectDTO subject,
    List<ResponseAnswerDTO> answers
) {
}
