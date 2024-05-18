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

@Service
public class JWTTokenAutenticacaoService {

    // TOKEN COM VALIDADE DE 10 DIAS
    private static final long EXPIRATION_TIME_MS = 10 * 24 * 60 * 60 * 1000;

    private static final String SECRET = "senhasecretadaempresa";

    private static final String TOKEN_PREFIX = "Bearer";

    private static final String HEADER_STRING = "Authorization";

    public void addAuthentication(HttpServletResponse response, String username) throws Exception {
        String JWT = Jwts.builder() /*chama o gerador de token*/
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();

        String token = TOKEN_PREFIX + " " + JWT;

        response.addHeader(HEADER_STRING, token);

        liberacaoCors(response);

        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
    }

    /* Retorna o usuário validado com token ou, caso não seja válido, retorna null */
    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {

        // Obtém o token do cabeçalho da requisição
        String token = request.getHeader(HEADER_STRING);

        // Verifica se o token não é nulo
        if (token != null) {
            // Remove o prefixo do token (ex: "Bearer ") e remove espaços em branco
            String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();

            /* Faz a validação do token do usuário na requisição e obtém o usuário */
            // Analisa o token para extrair as reivindicações (claims) e obtém o assunto (usuário)
            String user = Jwts.parser()
                    .setSigningKey(SECRET)  // Define a chave secreta usada para assinar o token
                    .parseClaimsJws(tokenLimpo)  // Faz o parsing do token JWT
                    .getBody().getSubject();  // Obtém o assunto (subject) do corpo do token; ex: "ADMIN" ou "Luan"

            if (user != null) {
                Usuario usuario = ApplicationContextLoad.
                        getApplicationContext().
                        getBean(UsuarioRepository.class).findUserByLogin(user);

                if (usuario != null) {
                    // Retorna uma instância de UsernamePasswordAuthenticationToken que implementa Authentication
                    return new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha(), usuario.getAuthorities());
                }
            }
        }

        // Chama o método para liberar CORS na resposta
        liberacaoCors(response);

        // Retorna null caso a autenticação não seja válida
        return null;
    }

    /* Fazendo liberação contra erro de CORS no navegador */
    private void liberacaoCors(HttpServletResponse response) {
        if (response.getHeader("Access-Control-Allow-Origin") == null) {
            response.addHeader("Access-Control-Allow-Origin", "*");
        }

        if (response.getHeader("Access-Control-Allow-Headers") == null) {
            response.addHeader("Access-Control-Allow-Headers", "*");
        }

        if (response.getHeader("Access-Control-Request-Headers") == null) {
            response.addHeader("Access-Control-Request-Headers", "*");
        }

        if (response.getHeader("Access-Control-Allow-Methods") == null) {
            response.addHeader("Access-Control-Allow-Methods", "*");
        }
    }
}
