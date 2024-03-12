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

    @PostMapping
    public TipoCambio createTipoCambio(@RequestBody TipoCambio tipoCambio) {
        return tipoCambioService.createTipoCambio(tipoCambio);
    }

    @GetMapping("/{id}")
    public TipoCambio getTipoCambio(@PathVariable Long id) {
        return tipoCambioService.getTipoCambio(id);
    }

    @GetMapping
    public List<TipoCambio> getAllTiposCambio() {
        return tipoCambioService.getAllTiposCambio();
    }

    @PutMapping("/{id}")
    public TipoCambio updateTipoCambio(@PathVariable Long id, @RequestBody TipoCambio tipoCambio) {
        return tipoCambioService.updateTipoCambio(id, tipoCambio);
    }

    @DeleteMapping("/{id}")
    public void deleteTipoCambio(@PathVariable Long id) {
        tipoCambioService.deleteTipoCambio(id);
    }
}
