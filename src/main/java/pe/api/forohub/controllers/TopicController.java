package pe.api.forohub.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pe.api.forohub.domain.answer.ResponseAnswerDTO;
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
        Optional<Subject> subjectOfTopic = subjectRepository.findById(createTopicDTO.isSubject());
        if (authorOfTopic.isEmpty()) {
            throw new BadPayloadException("author id doesn't exists");
        }
        if (subjectOfTopic.isEmpty()) {
            throw new BadPayloadException("subject id doesn't exists");
        }
        User user = authorOfTopic.get();
        Subject subject = subjectOfTopic.get();
        if(topicRepository.existsByTitleAndAuthorAndSubject(createTopicDTO.title(), user, subject)) {
            throw new BadPayloadException("There is already a topic with similar title, user and subject");
        }
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
    public ResponseEntity<Page<ResponseListTopicDTO>> getAll(
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(name = "status", required = false) String statusQueryParam
    ) {
        if (statusQueryParam == null || statusQueryParam.isEmpty()) {
            return ResponseEntity.ok(topicRepository.findAllByStatusNot(pageable, TopicStatus.DELETED).map(ResponseListTopicDTO::new));
        }
        try {
            TopicStatus status = TopicStatus.fromString(statusQueryParam);
            if (status == TopicStatus.DELETED) {
                throw new IllegalArgumentException("status deleted is not valid");
            }
            return ResponseEntity.ok(topicRepository.findByStatus(pageable, status).map(ResponseListTopicDTO::new));
        } catch (IllegalArgumentException e) {
            throw new BadQueryParamValueException(String.format("param value: %s is not valid. Details: %s", statusQueryParam, e.getMessage()), e);
        }
    }


    @PutMapping
    @Transactional
    public ResponseEntity<ResponseTopicDTO> update(@RequestBody @Valid UpdateTopicDTO updateTopicDTO) {
        Topic topic = topicRepository.getReferenceById(updateTopicDTO.id());
        if (topic.getStatus() == TopicStatus.DELETED) {
            throw new EntityNotFoundException("This topic doesn't exists, it was deleted.");
        }
        if (topic.getStatus() != TopicStatus.PENDING) {
            throw new BadPayloadException("This topic cannot be updated, because current status is not PENDING.");
        }
        try {
            topic.setTitle(updateTopicDTO.title());
            topic.setMessage(updateTopicDTO.message());
            topic.setStatus(TopicStatus.fromString(updateTopicDTO.status()));
            User user = topic.getAuthor();
            Subject subject = topic.getSubject();
            return ResponseEntity.ok(new ResponseTopicDTO(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreatedAt(),
                topic.getStatus(),
                new ResponseUserDTO(user.getId(), user.getName(), user.getEmail()),
                new ResponseSubjectDTO(subject.getId(), subject.getName(), subject.getCategory()),
                topic.getAnswers().stream().map(ResponseAnswerDTO::new).toList()
            ));
        } catch (IllegalArgumentException e) {
            throw new BadPayloadException("status value is not valid");
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseTopicDTO> delete(@PathVariable Long id) {
        Topic topic = topicRepository.getReferenceById(id);
        topic.setStatus(TopicStatus.DELETED);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTopicDTO> getById(@PathVariable Long id) {
        Topic topic = topicRepository.getReferenceById(id);
        if (topic.getStatus() == TopicStatus.DELETED) {
            throw new EntityNotFoundException("This topic doesn't exists, it was deleted.");
        }
        return ResponseEntity.ok(new ResponseTopicDTO(
            topic.getId(),
            topic.getTitle(),
            topic.getMessage(),
            topic.getCreatedAt(),
            topic.getStatus(),
            new ResponseUserDTO(topic.getAuthor()),
            new ResponseSubjectDTO(topic.getSubject()),
            topic.getAnswers().stream().map(ResponseAnswerDTO::new).toList()
        ));
    }
}
