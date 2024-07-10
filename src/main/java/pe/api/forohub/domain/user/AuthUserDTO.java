package pe.api.forohub.domain.user;

public record AuthUserDTO(
    String login,
    String password
) {
}
