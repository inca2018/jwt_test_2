package com.inca.reto.tecnico.repository;

import com.inca.reto.tecnico.entities.TipoCambio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoCambioRepository extends JpaRepository<TipoCambio, Long> {
}
