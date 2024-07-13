package pe.api.forohub.domain.topic;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import pe.api.forohub.domain.answer.Answer;
import pe.api.forohub.domain.subject.Subject;
import pe.api.forohub.domain.user.User;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String message;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TopicStatus status;

    @ManyToOne
    private User author;

    @ManyToOne
    private Subject subject;

    @OneToMany
    private List<Answer> answers;

    public Topic(String title, String message, User author, Subject subject) {
        this.title = title;
        this.message = message;
        this.author = author;
        this.subject = subject;
        this.status = TopicStatus.PENDING;
        this.answers = new LinkedList<>();
    }
}
