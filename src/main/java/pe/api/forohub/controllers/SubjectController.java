package pe.api.forohub.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pe.api.forohub.domain.subject.CreateSubjectDTO;
import pe.api.forohub.domain.subject.ResponseSubjectDTO;
import pe.api.forohub.domain.subject.Subject;
import pe.api.forohub.domain.subject.SubjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.net.URI;

@RestController
@RequestMapping("/subjects")
@SecurityRequirement(name = "bearer-key")
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;

    @PostMapping
    public ResponseEntity<ResponseSubjectDTO> create(
        @RequestBody @Valid CreateSubjectDTO subjectDTO,
        UriComponentsBuilder uriComponentsBuilder
    ){
        Subject newSubject = new Subject();
        newSubject.setName(subjectDTO.name());
        newSubject.setCategory(subjectDTO.category());
        subjectRepository.save(newSubject);
        URI uriToSubject = uriComponentsBuilder.path("/subjects/{id}").buildAndExpand(newSubject.getId()).toUri();
        return ResponseEntity.created(uriToSubject).body(new ResponseSubjectDTO(newSubject));
    }

    @GetMapping
    public ResponseEntity<Page<ResponseSubjectDTO>> getAll(@PageableDefault(size = 5) Pageable pageable) {
        return ResponseEntity.ok(subjectRepository.findAll(pageable).map(ResponseSubjectDTO::new));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseSubjectDTO> getSubjectById(@PathVariable Long id){
        Subject subject = subjectRepository.getReferenceById(id);
        return ResponseEntity.ok(new ResponseSubjectDTO(subject));
    }
}
