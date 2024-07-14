package pe.api.forohub.domain.answer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import pe.api.forohub.domain.topic.Topic;
import pe.api.forohub.domain.user.User;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String solution;

    @ManyToOne
    private Topic topic;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    private User author;

    public Answer(String message, String solution, Topic topic, User author){
        this.author = author;
        this.topic = topic;
        this.message = message;
        this.solution = solution;
        this.createdAt = LocalDateTime.now();
    }
}
