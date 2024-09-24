package seguranca.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seguranca.token.model.Login;

public interface LoginRepository extends JpaRepository<Login, Long> {
}
