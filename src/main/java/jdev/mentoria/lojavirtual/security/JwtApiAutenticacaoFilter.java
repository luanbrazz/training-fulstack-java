package jdev.mentoria.lojavirtual.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*FILTRO ONDE TODAS AS REQUISIÇÕES SERAO CAPTURADAS PARA AUTENTICAR*/
public class JwtApiAutenticacaoFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        try {
            /*Estabelece a autenticacao do user*/
            Authentication authentication = new JWTTokenAutenticacaoService()
                    .getAuthentication((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse);

            /*coloca o processo de autenticacao para o spring security*/
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            e.printStackTrace();
            servletResponse.getWriter().write("Ocorreu um erro no sistema, avise o administrador: \n" + e.getMessage());
        }

    }
}
