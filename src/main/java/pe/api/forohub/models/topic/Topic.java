package pe.api.forohub.models.topic;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import pe.api.forohub.models.answer.Answer;
import pe.api.forohub.models.course.Course;
import pe.api.forohub.models.user.User;

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

    private String title;

    private String message;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private TopicStatus status;

    @ManyToOne
    private User author;

    @ManyToOne
    private Course course;

    @OneToMany
    private List<Answer> answers;

    public Topic(String title, String message, User author, Course course) {
        this.title = title;
        this.message = message;
        this.author = author;
        this.course = course;
        this.status = TopicStatus.PENDING;
        this.answers = new LinkedList<>();
    }
}
