package com.inca.reto.tecnico.service;

import com.inca.reto.tecnico.entities.TipoCambio;
import com.inca.reto.tecnico.exception.TipoCambioNotFoundException;
import com.inca.reto.tecnico.repository.TipoCambioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoCambioService {

    @Autowired
    private TipoCambioRepository tipoCambioRepository;

    public TipoCambio createTipoCambio(TipoCambio tipoCambio) {
        return tipoCambioRepository.save(tipoCambio);
    }

    public TipoCambio getTipoCambio(Long id) {
        return tipoCambioRepository.findById(id)
                .orElseThrow(() -> new TipoCambioNotFoundException("Tipo de cambio no encontrado"));
    }

    public List<TipoCambio> getAllTiposCambio() {
        return tipoCambioRepository.findAll();
    }

    public TipoCambio updateTipoCambio(Long id, TipoCambio tipoCambio) {
        tipoCambio.setId(id);
        return tipoCambioRepository.save(tipoCambio);
    }

    public void deleteTipoCambio(Long id) {
        tipoCambioRepository.deleteById(id);
    }
}
