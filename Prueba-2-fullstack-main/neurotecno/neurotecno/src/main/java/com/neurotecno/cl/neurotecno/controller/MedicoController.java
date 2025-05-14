package com.neurotecno.cl.neurotecno.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neurotecno.cl.neurotecno.model.Medico;
import com.neurotecno.cl.neurotecno.service.MedicoService;

@RestController
@RequestMapping("api/v1/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    public ResponseEntity<List<Medico>> listar() {
        List<Medico> medicos = medicoService.obtenerMedicos();
        if (medicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(medicos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Medico> buscar(@PathVariable Long id) {
        try {
            Medico medico = medicoService.obtenerMedicoPorId( id);
            return ResponseEntity.ok(medico);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Medico> guardar(@RequestBody Medico medico) {
        Medico medicoNuevo = medicoService.guardarMedico(medico);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoNuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medico> actualizar(@PathVariable Long id, @RequestBody Medico medico) {
        try {
            Medico medicoActualizado = medicoService.actualizarMedico(id, medico);
            return ResponseEntity.ok(medicoActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Medico> patch(@PathVariable Long id, @RequestBody Medico parcialMedico) {
        Medico medicoActualizado = medicoService.editarMedico(id, parcialMedico);
        if (medicoActualizado != null) {
            return ResponseEntity.ok(medicoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            medicoService.eliminarMedico(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }





}
