package pe.api.forohub.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pe.api.forohub.domain.subject.ResponseSubjectDTO;
import pe.api.forohub.domain.topic.*;
import pe.api.forohub.domain.subject.Subject;
import pe.api.forohub.domain.user.ResponseUserDTO;
import pe.api.forohub.domain.user.User;
import pe.api.forohub.domain.subject.SubjectRepository;
import pe.api.forohub.domain.user.UserRepository;
import pe.api.forohub.exceptions.BadPayloadException;
import pe.api.forohub.exceptions.BadQueryParamValueException;

import java.net.URI;
import java.util.LinkedList;
import java.util.Optional;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    public TopicController(
        TopicRepository topicRepository,
        UserRepository userRepository,
        SubjectRepository subjectRepository
    ) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
    }

    @PostMapping
    public ResponseEntity<ResponseTopicDTO> create(
        @RequestBody @Valid CreateTopicDTO createTopicDTO,
        UriComponentsBuilder uriComponentsBuilder
    ) {
        Optional<User> authorOfTopic = userRepository.findById(createTopicDTO.idUser());
        Optional<Subject> subjectOfTopic = subjectRepository.findById(createTopicDTO.idCourse());
        if (authorOfTopic.isEmpty()) {
            throw new BadPayloadException("author id doesn't exists");
        }
        if (subjectOfTopic.isEmpty()) {
            throw new BadPayloadException("subject id doesn't exists");
        }
        User user = authorOfTopic.get();
        Subject subject = subjectOfTopic.get();
        Topic topic = new Topic(createTopicDTO.title(), createTopicDTO.message(), user, subject);
        topicRepository.save(topic);
        ResponseTopicDTO responseTopicDTO = new ResponseTopicDTO(
            topic.getId(),
            topic.getTitle(),
            topic.getMessage(),
            topic.getCreatedAt(),
            topic.getStatus(),
            new ResponseUserDTO(user.getId(), user.getName(), user.getEmail()),
            new ResponseSubjectDTO(subject.getId(), subject.getName(), subject.getCategory()),
            new LinkedList<>()
        );
        URI urlToTopicResource = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(urlToTopicResource).body(responseTopicDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ResponseListTopicDTO>> getTopics(
        @PageableDefault(size = 3) Pageable pageable,
        @RequestParam(name = "status", required = false, defaultValue = "ALL") String statusQueryParam
    ) {
        System.out.println("query param: " + statusQueryParam);
        if (statusQueryParam == null || statusQueryParam.equalsIgnoreCase("ALL") || statusQueryParam.isEmpty()) {
            return ResponseEntity.ok(topicRepository.findAll(pageable).map(ResponseListTopicDTO::new));
        }
        try {
            TopicStatus status = TopicStatus.fromString(statusQueryParam);
            return ResponseEntity.ok(topicRepository.findByStatus(pageable, status).map(ResponseListTopicDTO::new));
        } catch (IllegalArgumentException e) {
            throw new BadQueryParamValueException(String.format("param value: %s is not valid.", statusQueryParam), e);
        }
    }
}
