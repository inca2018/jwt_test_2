/*
 * Esta clase extiende UsernamePasswordAuthenticationFilter de Spring Security,
 * lo que significa que se utiliza para procesar las solicitudes de autenticación
 * basadas en nombre de usuario y contraseña.
 */
package com.inca.reto.tecnico.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // Tiempo de expiración del token JWT (10 días en milisegundos)
    public static final long EXPIRATION_TIME = 864_000_000;
    // Nombre del encabezado que contiene el token JWT
    public static final String HEADER_STRING = "Authorization";
    // Prefijo utilizado en el encabezado para indicar que el valor es un token JWT
    public static final String TOKEN_PREFIX = "Bearer ";
    // Clave secreta para firmar y verificar el token JWT
    public static final String SECRET = "Mundo2024.";

    private final AuthenticationManager authenticationManager;
    private final SecretKey secretKey;

    /*
     * Constructor de la clase que recibe un AuthenticationManager y una SecretKey.
     */
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, SecretKey secretKey) {
        this.authenticationManager = authenticationManager;
        this.secretKey = secretKey;
    }

    /*
     * Método invocado al intentar autenticar al usuario con las credenciales proporcionadas.
     * Lee las credenciales del cuerpo de la solicitud y crea un token de autenticación,
     * que luego se pasa al AuthenticationManager para la autenticación.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // Lee las credenciales del cuerpo de la solicitud
            UserCredentials credentials = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);

            // Crea un token de autenticación con las credenciales proporcionadas
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    credentials.getUsername(),
                    credentials.getPassword(),
                    Collections.emptyList()
            );

            // Autentica al usuario llamando al AuthenticationManager
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Método invocado cuando la autenticación es exitosa.
     * Genera un token JWT con el nombre de usuario y los roles del usuario autenticado,
     * y lo incluye en la respuesta HTTP.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 10 días
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
    }

    // Clase interna para representar las credenciales del usuario
    private static class UserCredentials {
        private String username;
        private String password;

        // Getters y setters

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
