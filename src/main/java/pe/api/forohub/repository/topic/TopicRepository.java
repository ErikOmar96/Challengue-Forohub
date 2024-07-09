package pe.api.forohub.repository.topic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.api.forohub.models.topic.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
}
