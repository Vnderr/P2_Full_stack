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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/medicos")
@Tag(name = "Medicos", description = "Operaciones relacionadas con los medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    @Operation(summary = "Obtener todas los medicos", description = "Obtiene una lista de todas los medicos")
    public ResponseEntity<List<Medico>> listar() {
        List<Medico> medicos = medicoService.obtenerMedicos();
        if (medicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(medicos);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar medico", description = "Buscar un medico existente")
    
    public ResponseEntity<Medico> buscar(@PathVariable Long id) {
        Medico medico = medicoService.obtenerMedicoPorId( id);
        if (medico == null)return ResponseEntity.notFound().build();      
        return ResponseEntity.ok(medico);
    }

    @PostMapping
    @Operation(summary = "Crear medico", description = "Crear un medico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medico agregado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Medico no encontrado")
    })
    public ResponseEntity<Medico> crearMedico(@RequestBody Medico medico) {
        Medico medicoNuevo = medicoService.guardarMedico(medico);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoNuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un medico", description = "Actualiza una carrera existente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Medico actualizado exitosamente",
                        content = @Content(mediaType = "aplication/json",
                                schema = @Schema(implementation = Medico.class))),
            @ApiResponse(responseCode = "404", description = "Medico no encontrado")
    })
    public ResponseEntity<Medico> actualizar(@PathVariable Long id, @RequestBody Medico medico) {
        try {
            Medico medicoActualizado = medicoService.actualizarMedico(id, medico);
            return ResponseEntity.ok(medicoActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente un medico", description = "Actualiza especificamente un medico existente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Medico actualizado exitosamente",
                        content = @Content(mediaType = "aplication/json",
                                schema = @Schema(implementation = Medico.class))),
            @ApiResponse(responseCode = "404", description = "Medico no encontrado")
    })
    public ResponseEntity<Medico> patch(@PathVariable Long id, @RequestBody Medico parcialMedico) {
        Medico medicoActualizado = medicoService.editarMedico(id, parcialMedico);
        if (medicoActualizado != null) {
            return ResponseEntity.ok(medicoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un medico", description = "Eliminar un medico existente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Medico eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Medico no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            medicoService.eliminarMedico(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }





}
