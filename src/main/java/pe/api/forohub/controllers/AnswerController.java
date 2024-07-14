package pe.api.forohub.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pe.api.forohub.domain.answer.Answer;
import pe.api.forohub.domain.answer.AnswerRepository;
import pe.api.forohub.domain.answer.CreateAnswerDTO;
import pe.api.forohub.domain.answer.ResponseAnswerDTO;
import pe.api.forohub.domain.topic.Topic;
import pe.api.forohub.domain.topic.TopicRepository;
import pe.api.forohub.domain.topic.TopicStatus;
import pe.api.forohub.domain.user.User;
import pe.api.forohub.domain.user.UserRepository;
import pe.api.forohub.exceptions.BadPayloadException;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;


    @PostMapping
    @Transactional
    public ResponseEntity<ResponseAnswerDTO> create(
        @RequestBody @Valid CreateAnswerDTO answerDTO,
        UriComponentsBuilder uriComponentsBuilder
    ){
        Optional<User> authorOfAnswer = userRepository.findById(answerDTO.idUser());
        Optional<Topic> topicOfAnswer = topicRepository.findByIdAndStatus(answerDTO.idTopic(), TopicStatus.PENDING);
        if(authorOfAnswer.isEmpty() || topicOfAnswer.isEmpty()) {
            throw new BadPayloadException("user id doesn't exists or topic id is not in status pending or doesn't exists");
        }
        User author = authorOfAnswer.get();
        Topic topic = topicOfAnswer.get();
        Answer newAnswer = new Answer(answerDTO.message(), answerDTO.solution(), topic, author);
        answerRepository.save(newAnswer);
        URI uriToAnswer = uriComponentsBuilder.path("/answers/{id}").buildAndExpand(newAnswer.getId()).toUri();
        return ResponseEntity.created(uriToAnswer).body(new ResponseAnswerDTO(newAnswer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseAnswerDTO> getById(@PathVariable Long id) {
        Answer answer = answerRepository.getReferenceById(id);
        return ResponseEntity.ok(new ResponseAnswerDTO(answer));
    }
}
