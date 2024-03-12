/*
 * Esta clase se ejecuta al arrancar el proyecto y se encarga de agregar usuarios en la base de datos
 * si no existen previamente.
 */
package com.inca.reto.tecnico;

import com.inca.reto.tecnico.entities.Usuario;
import com.inca.reto.tecnico.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;

    public StartupRunner(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen usuarios en la base de datos
        if (usuarioRepository.count() == 0) {
            // Agregar usuarios solo si no existen
            Usuario usuario1 = new Usuario();
            usuario1.setNombre("Nombre1");
            usuario1.setApellido("Apellido1");
            usuario1.setCorreoElectronico("correo1@example.com");
            usuario1.setNombreUsuario("usuario1");
            usuario1.setContraseña("contraseña1");
            usuarioRepository.save(usuario1);

            Usuario usuario2 = new Usuario();
            usuario2.setNombre("Nombre2");
            usuario2.setApellido("Apellido2");
            usuario2.setCorreoElectronico("correo2@example.com");
            usuario2.setNombreUsuario("usuario2");
            usuario2.setContraseña("contraseña2");
            usuarioRepository.save(usuario2);

            Usuario usuario3 = new Usuario();
            usuario3.setNombre("Nombre3");
            usuario3.setApellido("Apellido3");
            usuario3.setCorreoElectronico("correo3@example.com");
            usuario3.setNombreUsuario("usuario3");
            usuario3.setContraseña("contraseña3");
            usuarioRepository.save(usuario3);

            System.out.println("Usuarios agregados correctamente.");
        } else {
            System.out.println("Los usuarios ya existen en la base de datos. No se agregaron nuevos usuarios.");
        }
    }
}
