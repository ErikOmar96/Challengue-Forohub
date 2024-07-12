package pe.api.forohub.domain.topic;

public record ResponseListTopicDTO (
    Long id,
    String title,
    String message,
    TopicStatus status
){
    public ResponseListTopicDTO (Topic topic){
        this(topic.getId(), topic.getTitle(), topic.getMessage(), topic.getStatus());
    }
}
