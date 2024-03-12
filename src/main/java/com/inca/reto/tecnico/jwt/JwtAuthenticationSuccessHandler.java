/*
 * Esta clase implementa la interfaz AuthenticationSuccessHandler de Spring Security,
 * lo que significa que es responsable de manejar el éxito de la autenticación.
 * Cuando un usuario se autentica con éxito, esta clase genera un token JWT y lo
 * incluye en la respuesta HTTP.
 */
package com.inca.reto.tecnico.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final SecretKey secretKey;

    /*
     * Constructor de la clase que recibe una clave secreta (SecretKey).
     * Esta clave se utilizará para firmar el token JWT.
     */
    public JwtAuthenticationSuccessHandler(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    /*
     * Método que se ejecuta cuando la autenticación es exitosa.
     * Genera un token JWT utilizando la información de autenticación del usuario
     * y lo incluye en el encabezado de autorización de la respuesta HTTP.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Generar el token JWT
        String token = generateToken(authentication);

        // Incluir el token en el encabezado de autorización de la respuesta HTTP
        response.addHeader("Authorization", "Bearer " + token);

        // Establecer el código de estado OK en la respuesta
        response.setStatus(HttpServletResponse.SC_OK);

        // Mostrar en la consola el token generado (para propósitos de prueba)
        System.out.println("Token generado: " + token);
        System.out.println("RESPONSE generado: " + response);
    }

    /*
     * Método privado que genera un token JWT utilizando la información de autenticación
     * del usuario y la clave secreta proporcionada en el constructor.
     */
    private String generateToken(Authentication authentication) {
        // Obtener el usuario autenticado
        User principal = (User) authentication.getPrincipal();

        // Construir el token JWT con la información del usuario
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .claim("authorities", authentication.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JwtAuthenticationFilter.EXPIRATION_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }
}
