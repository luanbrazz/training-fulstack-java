package jdev.mentoria.lojavirtual.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
public class JWTTokenAutenticacaoService {

    //    TOKEN COM VALIDADE DE 10 DIAS
    private static final long EXPIRATION_TIME_MS = 10 * 24 * 60 * 60 * 1000;

    private static final String SECRET = "senhasecretadaempresa";

    private static final String TOKEN_PREFIX = "Bearer";

    private static final String HEADER_STRING = "Authorization";

    public void addAuthentication(HttpServletResponse response, String username) throws Exception {

        String JWT = Jwts.builder() /*chama o gerador de token*/
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                .signWith(SignatureAlgorithm.ES512, SECRET).compact();

        String token = TOKEN_PREFIX + " " + JWT;

        response.addHeader(HEADER_STRING, token);

        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
    }

}
