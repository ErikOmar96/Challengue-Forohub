package pe.api.forohub.domain.subject;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;
}
