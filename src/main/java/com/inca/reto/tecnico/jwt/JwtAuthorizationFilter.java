/*
 * Esta clase se encarga de filtrar las solicitudes HTTP para autorizar el acceso basado en tokens JWT.
 * Comprueba la presencia y validez del token JWT en el encabezado de autorización de la solicitud.
 * Si el token es válido, verifica si el usuario tiene los roles necesarios para acceder a la URL solicitada.
 */
package com.inca.reto.tecnico.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private int order = Ordered.HIGHEST_PRECEDENCE;
    private final SecretKey secretKey;

    public JwtAuthorizationFilter(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            String username = extractUsername(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (validateToken(jwt)) {
                    // Token válido, verificamos que el usuario tenga los roles necesarios para acceder a la URL
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    // Agregar comprobación de autorización aquí
                    if (checkAuthorization(authenticationToken, request)) {
                        // Usuario autorizado, continúa con la cadena de filtros
                        filterChain.doFilter(request, response);
                        return;
                    } else {
                        // Usuario no autorizado, devolver error 403
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }
                }
            }
        }

        // Usuario no autenticado, continúa con la cadena de filtros
        filterChain.doFilter(request, response);
    }

    public boolean checkAuthorization(UsernamePasswordAuthenticationToken authenticationToken, HttpServletRequest request) {

        return authenticationToken.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"));
    }

    public String extractUsername(String jwt) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody().getSubject();
    }

    public boolean validateToken(String jwt) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt);
            return true;
        } catch (Exception e) {
            // Registra el error
            logger.error("Error al validar el token JWT: " + e.getMessage());
            return false;
        }
    }
}
