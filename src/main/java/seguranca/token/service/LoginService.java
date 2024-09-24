package seguranca.token.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import seguranca.token.dto.DadosCadastroLogin;
import seguranca.token.dto.LoginDTO;
import seguranca.token.model.Login;
import seguranca.token.repository.LoginRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    public ResponseEntity<LoginDTO> cadastrar(@RequestBody @Valid DadosCadastroLogin dados) {
        var login = new Login(dados);
        loginRepository.save(login);
        return ResponseEntity.ok().build();
    }

    public List<LoginDTO> obterTodosLogins() {
        return converteDados(loginRepository.findAll());
    }

    private List<LoginDTO> converteDados(List<Login> logins) {
        return logins.stream().map(l -> new LoginDTO(l.getId(), l.getLogin(), l.getSenha()))
                .collect(Collectors.toList()) ;
    }

}
