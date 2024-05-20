package jdev.mentoria.lojavirtual.security;

import jdev.mentoria.lojavirtual.service.ImplementacaoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.http.HttpSessionListener;

@Configuration
@EnableWebSecurity
// Habilita a segurança global do método Spring. Isso permite que você use anotações de segurança
// nos métodos para controlar o acesso com base em regras de segurança específicas.
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter implements HttpSessionListener {

    @Autowired
    private ImplementacaoUserDetailsService implementacaoUserDetailsService;

    // Sobrescreve o método configure para configurar a segurança HTTP.
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Configura a proteção CSRF usando tokens armazenados em cookies, mas permite que os cookies sejam acessíveis via JavaScript.
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                // Desabilita a proteção CSRF. (Isso geralmente não é recomendado para aplicativos em produção.)
                .disable()
                // Configura as regras de autorização de requisições HTTP.
                .authorizeRequests()
                // Permite acesso irrestrito à URL raiz.
                .antMatchers("/").permitAll()
                // Permite acesso irrestrito à URL "/index".
                .antMatchers("/index").permitAll()
                // Permite que todas as requisições HTTP OPTIONS sejam acessadas sem autenticação.
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // Qualquer outra requisição deve ser autenticada.
                .anyRequest().authenticated()
                // Configura o logout para redirecionar para "/index" após o sucesso no logout.
                .and().logout().logoutSuccessUrl("/index")

                // Adiciona um filtro para processar as requisições de login JWT após a autenticação padrão do usuário e senha.
                .and().addFilterAfter(new JWTLoginFilter("/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)

                // Adiciona um filtro para processar a autenticação JWT antes do filtro de autenticação padrão.
                .addFilterBefore(new JwtApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /*ira consultar o user no banco com spring security*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(implementacaoUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /*ignora algumas url livre de autenticação*/
    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers(HttpMethod.GET, "/salvarAcesso", "/deleteAcesso")
//                .antMatchers(HttpMethod.POST, "/salvarAcesso", "/deleteAcesso");
        /*Ignorando url no momento para nao autenticar*/
    }
}
