package seguranca.token.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import seguranca.token.dto.DadosCadastroLogin;
import seguranca.token.dto.LoginDTO;
import seguranca.token.service.LoginService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService servico;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("")
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid DadosCadastroLogin dados) {
        return servico.cadastrar(dados);
    }

    @PostMapping("/autenticar")
    public ResponseEntity<?> login(@RequestBody @Valid DadosCadastroLogin dados){

        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = authenticationManager.authenticate(token);
        try {
            // Valida o login e retorna o DTO se for bem-sucedido
            var loginDTO = servico.validaLogin(dados);

            // Retorna o DTO com os dados do usuário autenticado
            return ResponseEntity.ok(Collections.singletonMap("success",loginDTO));
        } catch (RuntimeException e) {
            // Caso a autenticação falhe, retorna uma mensagem de erro
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping
    public List<LoginDTO> listar() {
        return servico.obterTodosLogins();
    }
}
