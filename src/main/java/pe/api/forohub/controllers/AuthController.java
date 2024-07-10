package pe.api.forohub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.api.forohub.domain.user.AuthUserDTO;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public <T> ResponseEntity<T> authUser(@RequestBody AuthUserDTO authUserDTO){
        Authentication token = new UsernamePasswordAuthenticationToken(authUserDTO.login(), authUserDTO.password());
        authenticationManager.authenticate(token);
        return ResponseEntity.ok().build();
    }
}
