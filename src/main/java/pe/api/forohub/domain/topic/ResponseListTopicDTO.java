package pe.api.forohub.domain.topic;

import java.time.LocalDateTime;

public record ResponseListTopicDTO (
    Long id,
    String title,
    String message,
    TopicStatus status,
    LocalDateTime createdAt
){
    public ResponseListTopicDTO (Topic topic){
        this(topic.getId(), topic.getTitle(), topic.getMessage(), topic.getStatus(), topic.getCreatedAt());
    }
}
