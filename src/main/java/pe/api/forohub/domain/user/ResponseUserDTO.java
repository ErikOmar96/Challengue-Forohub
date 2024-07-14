package pe.api.forohub.domain.user;

public record ResponseUserDTO(
    Long id,
    String name,
    String email
) {
    public ResponseUserDTO(User user){
        this(user.getId(), user.getName(), user.getEmail());
    }
}
