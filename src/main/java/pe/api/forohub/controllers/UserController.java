package pe.api.forohub.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pe.api.forohub.domain.user.CreateUserDTO;
import pe.api.forohub.domain.user.ResponseUserDTO;
import pe.api.forohub.domain.user.User;
import pe.api.forohub.domain.user.UserRepository;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<ResponseUserDTO> create(
        @RequestBody @Valid CreateUserDTO newUser,
        UriComponentsBuilder uriComponentsBuilder
    ){
        User user = new User();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setName(newUser.name());
        user.setEmail(newUser.email());
        user.setPassword(encoder.encode(newUser.password()));
        userRepository.save(user);
        URI urlToUserResource = uriComponentsBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(urlToUserResource).body(new ResponseUserDTO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> getUserById(@PathVariable Long id){
        User user = userRepository.getReferenceById(id);
        return ResponseEntity.ok(new ResponseUserDTO(user));
    }

}
