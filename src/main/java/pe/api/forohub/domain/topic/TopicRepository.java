package pe.api.forohub.domain.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.api.forohub.domain.subject.Subject;
import pe.api.forohub.domain.user.User;

import java.util.Optional;


@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Page<Topic> findByStatus(Pageable status, TopicStatus topicStatus);
    Page<Topic> findAllByStatusNot(Pageable status, TopicStatus topicStatus);
    boolean existsByTitleAndAuthorAndSubject(String title, User author, Subject subject);
    Optional<Topic> findByIdAndStatus(Long id, TopicStatus topicStatus);
}
