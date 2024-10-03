package seguranca.token.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import seguranca.token.dto.DadosCadastroLogin;
import seguranca.token.dto.LoginDTO;
import seguranca.token.service.LoginService;
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

        return ResponseEntity.ok().build();
//        String login = dados.login();
//        String senha = dados.senha();
//
//        if (servico.validaLogin(dados)){
//            return ResponseEntity.ok(Collections.singletonMap("success", true));
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("success", false));
//        }
    }

    @GetMapping
    public List<LoginDTO> listar() {
        return servico.obterTodosLogins();
    }
}
