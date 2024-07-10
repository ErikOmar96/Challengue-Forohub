package pe.api.forohub.domain.user;

public record ResponseUserDTO(
    Long id,
    String name,
    String email
) {
}
