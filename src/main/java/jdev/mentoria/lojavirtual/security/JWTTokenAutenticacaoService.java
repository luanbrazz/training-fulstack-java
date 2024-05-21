package jdev.mentoria.lojavirtual.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jdev.mentoria.lojavirtual.ApplicationContextLoad;
import jdev.mentoria.lojavirtual.model.Usuario;
import jdev.mentoria.lojavirtual.repository.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.logging.Logger;

@Service
public class JWTTokenAutenticacaoService {

    // Logger for logging errors and important information
    private static final Logger logger = Logger.getLogger(JWTTokenAutenticacaoService.class.getName());

    // TOKEN COM VALIDADE DE 10 DIAS
    private static final long EXPIRATION_TIME_MS = 10 * 24 * 60 * 60 * 1000;

    /*private static final String SECRET = System.getenv("JWT_SECRET_KEY");*/
    private static final String SECRET = "senhasecretadaempresa";

    private static final String TOKEN_PREFIX = "Bearer";

    private static final String HEADER_STRING = "Authorization";

    public void addAuthentication(HttpServletResponse response, String username) throws Exception {
        String JWT = Jwts.builder() /*chama o gerador de token*/
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        String token = TOKEN_PREFIX + " " + JWT;

        response.addHeader(HEADER_STRING, token);
        liberacaoCors(response);
        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
    }

    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {
        // Obtém o token do cabeçalho da requisição
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            try {
                // Remove o prefixo do token (ex: "Bearer ") e remove espaços em branco
                String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();

                /* Faz a validação do token do usuário na requisição e obtém o usuário */
                // Analisa o token para extrair as reivindicações (claims) e obtém o assunto (usuário)
                String user = Jwts.parser()
                        .setSigningKey(SECRET)  // Define a chave secreta usada para assinar o token
                        .parseClaimsJws(tokenLimpo)  // Faz o parsing do token JWT
                        .getBody()
                        .getSubject();

                if (user != null) {
                    Usuario usuario = ApplicationContextLoad
                            .getApplicationContext()
                            .getBean(UsuarioRepository.class)
                            .findUserByLogin(user);

                    if (usuario != null) {
                        return new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha(), usuario.getAuthorities());
                    }
                }
            } catch (Exception e) {
                logger.severe("O token JWT é inválido: " + e.getMessage());
            }
        }

        liberacaoCors(response);
        return null;
    }

    private void liberacaoCors(HttpServletResponse response) {
        addHeaderIfAbsent(response, "Access-Control-Allow-Origin", "*");
        addHeaderIfAbsent(response, "Access-Control-Allow-Headers", "*");
        addHeaderIfAbsent(response, "Access-Control-Request-Headers", "*");
        addHeaderIfAbsent(response, "Access-Control-Allow-Methods", "*");
    }

    private void addHeaderIfAbsent(HttpServletResponse response, String headerName, String headerValue) {
        if (response.getHeader(headerName) == null) {
            response.addHeader(headerName, headerValue);
        }
    }
}
