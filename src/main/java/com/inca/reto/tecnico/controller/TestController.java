package com.inca.reto.tecnico.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "Este es un endpoint público.";
    }

    @GetMapping("/private")
    @PreAuthorize("hasRole('USER')") // Agrega anotación de seguridad para verificar el rol del usuario
    public String privateEndpoint() {
        return "Este es un endpoint privado que requiere autenticación JWT y rol USER.";
    }

}
