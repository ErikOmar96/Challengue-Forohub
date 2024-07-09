package pe.api.forohub.controllers.topic;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.api.forohub.models.course.Course;
import pe.api.forohub.models.topic.Topic;
import pe.api.forohub.models.user.User;
import pe.api.forohub.repository.course.CourseRepository;
import pe.api.forohub.repository.topic.TopicRepository;
import pe.api.forohub.repository.user.UserRepository;

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
    public void create(@RequestBody @Valid CreateTopicDTO createTopicDTO) {
        Optional<User> user = userRepository.findById(createTopicDTO.idUser());
        Optional<Course> course = courseRepository.findById(createTopicDTO.idCourse());
        if(user.isPresent() && course.isPresent()) {
            Topic topic = new Topic(createTopicDTO.title(), createTopicDTO.message(), user.get(), course.get());
            topicRepository.save(topic);
        }else{
            throw new RuntimeException("user or course not found");
        }
    }
}
