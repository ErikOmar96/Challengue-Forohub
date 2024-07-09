package pe.api.forohub.controllers.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthUserDTO(
    String login,
    String password
) {
}
