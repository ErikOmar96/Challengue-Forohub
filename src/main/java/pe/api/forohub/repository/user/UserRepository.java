package pe.api.forohub.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import pe.api.forohub.models.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByName(String username);
}
