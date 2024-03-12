package com.inca.reto.tecnico.repository;
import com.inca.reto.tecnico.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Aquí puedes agregar métodos personalizados de consulta si los necesitas
}
