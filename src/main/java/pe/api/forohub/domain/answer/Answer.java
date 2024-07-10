package pe.api.forohub.domain.answer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pe.api.forohub.domain.topic.Topic;
import pe.api.forohub.domain.user.User;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    private Topic topic;

    private LocalDateTime createdAt;

    @ManyToOne
    private User author;

    private String solution;
}
