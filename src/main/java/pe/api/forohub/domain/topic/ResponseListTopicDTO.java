package pe.api.forohub.domain.topic;

public record ResponseListTopicDTO (
    Long id,
    String title,
    String message,
    TopicStatus status
){
}
