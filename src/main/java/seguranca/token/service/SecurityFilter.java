package seguranca.token.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import seguranca.token.repository.LoginRepository;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenServico tokenServico;

    @Autowired
    private LoginRepository loginRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            var subject = tokenServico.getSubject(tokenJWT);
            var login = loginRepository.findByLogin(subject);

            var autenticacao = new UsernamePasswordAuthenticationToken(login, null, login.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(autenticacao);
        }


        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {

        var acesso = request.getHeader("Authorization");

        if ((acesso != null) ) {
            return acesso.replace("Bearer ", "").trim();
        }
        return null;

    }
}
