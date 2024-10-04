package seguranca.token.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import seguranca.token.repository.LoginRepository;

@Service
public class AutenticacaoService implements UserDetailsService {

//    A injeção da dependencia pode ser feita com o @Autoriwed ou criando o construtor abaixo
    private final LoginRepository loginRepository;

    public AutenticacaoService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loginRepository.findByLogin(username);
    }
}
