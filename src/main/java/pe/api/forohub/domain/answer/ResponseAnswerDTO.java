package pe.api.forohub.domain.answer;

import pe.api.forohub.domain.user.ResponseUserDTO;

import java.time.LocalDateTime;

public record ResponseAnswerDTO(
    String message,
    LocalDateTime createdAt,
    ResponseUserDTO author,
    String solution,
    Long idTopic
){
}
