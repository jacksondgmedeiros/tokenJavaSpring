package seguranca.token.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import seguranca.token.dto.DadosCadastroLogin;
import seguranca.token.dto.LoginDTO;
import seguranca.token.model.Login;
import seguranca.token.repository.LoginRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<LoginDTO> cadastrar(@RequestBody @Valid DadosCadastroLogin dados) {

        String senhaCriptografada = passwordEncoder.encode(dados.senha());
        DadosCadastroLogin dadosCadastroLoginCriptografado = new DadosCadastroLogin(dados.login(), senhaCriptografada);
        var login = new Login(dadosCadastroLoginCriptografado);
        loginRepository.save(login);
        return ResponseEntity.ok().build();
    }



    public DadosCadastroLogin validaLogin(@RequestBody @Valid DadosCadastroLogin dados) {

        UserDetails obterLogin = loginRepository.findByLogin(dados.login());
        if (obterLogin != null) {
            if (passwordEncoder.matches(dados.senha(), obterLogin.getPassword())) {
                return new DadosCadastroLogin(obterLogin.getUsername(), obterLogin.getPassword());
            }
            else {
                throw new RuntimeException("Senha incorreta");

            }
        }else {
            throw new RuntimeException("Login Não encontrado");
        }

    }

    public List<LoginDTO> obterTodosLogins() {

        return converteDados(loginRepository.findAll());
    }

    private List<LoginDTO> converteDados(List<Login> logins) {
        return logins.stream().map(l -> new LoginDTO(l.getId(), l.getLogin()))
                .collect(Collectors.toList()) ;
    }



}
