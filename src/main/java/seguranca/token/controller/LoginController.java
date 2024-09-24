package seguranca.token.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seguranca.token.dto.DadosCadastroLogin;
import seguranca.token.dto.LoginDTO;
import seguranca.token.model.Login;
import seguranca.token.repository.LoginRepository;
import seguranca.token.service.LoginService;

import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService servico;


    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroLogin dados) {
        return servico.cadastrar(dados);
    }

    @GetMapping
    public List<LoginDTO> listar() {
        return servico.obterTodosLogins();
    }
}
