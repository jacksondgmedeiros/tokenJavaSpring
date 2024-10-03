package seguranca.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import seguranca.token.model.Login;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
    UserDetails findByLogin(String login);
}
