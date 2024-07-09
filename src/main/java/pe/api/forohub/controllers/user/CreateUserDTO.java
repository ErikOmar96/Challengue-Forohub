package pe.api.forohub.controllers.user;

public record CreateUserDTO(
    String name,
    String email,
    String password
) {
}
