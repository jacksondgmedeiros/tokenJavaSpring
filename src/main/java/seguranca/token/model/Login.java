package seguranca.token.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seguranca.token.dto.DadosCadastroLogin;

@Entity
@Table(name = "login")

//anotações do lombok para a criação de getter,setter, construtor e equals
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String login;

    private String senha;

    public Login(DadosCadastroLogin dados) {
        this.login = dados.login();
        this.senha = dados.senha();
    }
}
