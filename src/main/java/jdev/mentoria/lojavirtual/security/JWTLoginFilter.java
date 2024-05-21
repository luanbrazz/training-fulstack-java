package jdev.mentoria.lojavirtual.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdev.mentoria.lojavirtual.model.Usuario;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    /*configurando o gerenciador de autenticacao*/
    public JWTLoginFilter(String url, AuthenticationManager authenticationManager) {

        /*obriga autenticar a url*/
        super(new AntPathRequestMatcher(url));

        /*gerenciador de autenticacao*/
        setAuthenticationManager(authenticationManager);
    }

    /*retorna o usuario ao processar a autenticação*/
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);

        /*retorna o user com login e senha*/
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getSenha()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        try {
            new JWTTokenAutenticacaoService().addAuthentication(response, authResult.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

//        super.unsuccessfulAuthentication(request, response, failed);

        if (failed instanceof BadCredentialsException) {
            response.getWriter().write("Usuário e/ou senha não encontrado");
        } else {
            response.getWriter().write("Falha ao logar: " + failed.getMessage());
        }
    }
}
