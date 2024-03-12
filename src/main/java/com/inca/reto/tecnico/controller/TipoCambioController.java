/*
 * Este controlador maneja las solicitudes relacionadas con los tipos de cambio.
 * Proporciona endpoints para crear, recuperar, actualizar y eliminar tipos de cambio.
 */
package com.inca.reto.tecnico.controller;

import com.inca.reto.tecnico.entities.TipoCambio;
import com.inca.reto.tecnico.service.TipoCambioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/tipos-cambio")
public class TipoCambioController {

    @Autowired
    private TipoCambioService tipoCambioService;

    // Endpoint para crear un nuevo tipo de cambio
    @PostMapping
    public TipoCambio createTipoCambio(@RequestBody TipoCambio tipoCambio) {
        return tipoCambioService.createTipoCambio(tipoCambio);
    }

    // Endpoint para obtener un tipo de cambio por su ID
    @GetMapping("/{id}")
    public TipoCambio getTipoCambio(@PathVariable Long id) {
        return tipoCambioService.getTipoCambio(id);
    }

    // Endpoint para obtener todos los tipos de cambio
    @GetMapping
    public List<TipoCambio> getAllTiposCambio() {
        return tipoCambioService.getAllTiposCambio();
    }

    // Endpoint para actualizar un tipo de cambio existente
    @PutMapping("/{id}")
    public TipoCambio updateTipoCambio(@PathVariable Long id, @RequestBody TipoCambio tipoCambio) {
        return tipoCambioService.updateTipoCambio(id, tipoCambio);
    }

    // Endpoint para eliminar un tipo de cambio por su ID
    @DeleteMapping("/{id}")
    public void deleteTipoCambio(@PathVariable Long id) {
        tipoCambioService.deleteTipoCambio(id);
    }
}
