package seguranca.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seguranca.token.model.Login;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Optional<Login> findByLogin(String login);
}
