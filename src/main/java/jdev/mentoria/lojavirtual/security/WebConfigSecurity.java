package jdev.mentoria.lojavirtual.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.servlet.http.HttpSessionListener;

@Configuration
@EnableWebSecurity
// Habilita a segurança global do método Spring. Isso permite que você use anotações de segurança
// nos métodos para controlar o acesso com base em regras de segurança específicas.
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter implements HttpSessionListener {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.GET, "/salvarAcesso", "/deleteAcesso")
                .antMatchers(HttpMethod.POST, "/salvarAcesso", "/deleteAcesso");
        /*Ignorando url no momento para nao autenticar*/
    }
}
