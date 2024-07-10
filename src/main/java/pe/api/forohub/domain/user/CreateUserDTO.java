package pe.api.forohub.domain.user;

public record CreateUserDTO(
    String name,
    String email,
    String password
) {
}
