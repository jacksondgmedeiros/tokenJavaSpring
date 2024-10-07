package seguranca.token.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import seguranca.token.dto.DadosCadastroLogin;
import seguranca.token.dto.LoginDTO;
import seguranca.token.model.Login;
import seguranca.token.service.LoginService;
import seguranca.token.service.TokenServico;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService servico;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenServico tokenServico;


    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid DadosCadastroLogin dados) {
        return servico.cadastrar(dados);
    }

    @PostMapping("/autenticar")
    public ResponseEntity<?> login(@RequestBody @Valid DadosCadastroLogin dados){

        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var tokenJWT = tokenServico.gerarToken((Login) authentication.getPrincipal());

        // esse código retorna o success para validar no front, mas apenas o  return ResponseEntity.ok().build(); funcionaria
        try {
            // Valida o login e retorna o DTO se for bem-sucedido
            var loginDTO = servico.validaLogin(dados);

//             Retorna o DTO com os dados do usuário autenticado, com loginDTO faz a validação
            return ResponseEntity.ok(Collections.singletonMap("success",tokenJWT));
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
