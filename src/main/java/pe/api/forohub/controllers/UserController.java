package pe.api.forohub.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.api.forohub.domain.user.CreateUserDTO;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    public void create(@RequestBody CreateUserDTO newUser){
        System.out.println(newUser);
    }
}
