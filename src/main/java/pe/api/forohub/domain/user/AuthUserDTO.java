package pe.api.forohub.domain.user;

import jakarta.validation.constraints.NotBlank;

public record AuthUserDTO(
    @NotBlank
    String login,
    @NotBlank
    String password
) {
}
