package pe.api.forohub.controllers;

import jakarta.validation.Valid;
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
import pe.api.forohub.domain.user.User;
import pe.api.forohub.infrastructure.security.ResponseTokenDTO;
import pe.api.forohub.infrastructure.security.TokenService;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<ResponseTokenDTO> authUser(@RequestBody @Valid AuthUserDTO authUserDTO) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(authUserDTO.login(), authUserDTO.password());
        Authentication authUser = authenticationManager.authenticate(authToken);
        String jwtToken = tokenService.generateToken((User) authUser.getPrincipal());
        return ResponseEntity.ok(new ResponseTokenDTO(jwtToken));
    }
}
