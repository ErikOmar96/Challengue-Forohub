package pe.api.forohub.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import pe.api.forohub.domain.course.ResponseCourseDTO;
import pe.api.forohub.domain.topic.CreateTopicDTO;
import pe.api.forohub.domain.course.Course;
import pe.api.forohub.domain.topic.ResponseTopicDTO;
import pe.api.forohub.domain.topic.Topic;
import pe.api.forohub.domain.user.ResponseUserDTO;
import pe.api.forohub.domain.user.User;
import pe.api.forohub.domain.course.CourseRepository;
import pe.api.forohub.domain.topic.TopicRepository;
import pe.api.forohub.domain.user.UserRepository;

import java.net.URI;
import java.util.LinkedList;
import java.util.Optional;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public TopicController(
        TopicRepository topicRepository,
        UserRepository userRepository,
        CourseRepository courseRepository
    ) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @PostMapping
    public ResponseEntity<ResponseTopicDTO> create(
        @RequestBody @Valid CreateTopicDTO createTopicDTO,
        UriComponentsBuilder uriComponentsBuilder
    ) {
        Optional<User> authorOfTopic = userRepository.findById(createTopicDTO.idUser());
        Optional<Course> courseOfTopic = courseRepository.findById(createTopicDTO.idCourse());
        if (authorOfTopic.isPresent() && courseOfTopic.isPresent()) {
            User user = authorOfTopic.get();
            Course course = courseOfTopic.get();
            Topic topic = new Topic(createTopicDTO.title(), createTopicDTO.message(), user, course);
            topicRepository.save(topic);
            ResponseTopicDTO responseTopicDTO = new ResponseTopicDTO(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreatedAt(),
                topic.getStatus(),
                new ResponseUserDTO(user.getId(), user.getName(), user.getEmail()),
                new ResponseCourseDTO(course.getId(), course.getName(), course.getCategory()),
                new LinkedList<>()
            );
            URI urlToTopicResource = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();
            return ResponseEntity.created(urlToTopicResource).body(responseTopicDTO);
        } else {
            //TODO: enhance this
            return ResponseEntity.notFound().build();
        }
    }
}
