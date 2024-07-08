package pe.api.forohub.models.topic;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pe.api.forohub.models.answer.Answer;
import pe.api.forohub.models.course.Course;
import pe.api.forohub.models.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String message;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private TopicStatus status;

    @ManyToOne
    private User author;

    @ManyToOne
    private Course course;

    @OneToMany
    private List<Answer> answers;
}
