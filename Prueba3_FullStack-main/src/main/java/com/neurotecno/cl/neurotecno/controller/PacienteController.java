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
import com.neurotecno.cl.neurotecno.model.Paciente;
import com.neurotecno.cl.neurotecno.service.PacienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;
    
    @GetMapping
    @Operation(summary = "Obtener todos los pacientes", description = "Obtiene una lista de todos los pacientes")
    public ResponseEntity<List<Paciente>> listar(){
        List <Paciente> pacientes = pacienteService.obtenerPacientes();
        if(pacientes.isEmpty()){return ResponseEntity.noContent().build();}
        return ResponseEntity.ok(pacientes);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar paciente", description = "Busca un paciente existente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Medico encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Medico no encontrado")
    })
    public ResponseEntity<Paciente> buscar(@PathVariable Long id){
        Paciente paciente = pacienteService.obtenerPacientePorId(id);
        if (paciente == null)return ResponseEntity.notFound().build();
        return ResponseEntity.ok(paciente);
    }

    @PostMapping
    @Operation(summary = "Crear paciente", description = "Crear un paciente inexistente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Paciente creado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Medico no encontrado")
    })
    public ResponseEntity<Paciente> crearPaciente(@RequestBody Paciente paciente) {
    
        Paciente pacienteNuevo = pacienteService.guardarPaciente(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteNuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un paciente", description = "Actualizar un paciente existente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Paciente actualizado exitosamente",
                        content = @Content(mediaType = "aplication/json",
                                schema = @Schema(implementation = Medico.class))),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    public ResponseEntity<Paciente> actualizar(@PathVariable Long id, @RequestBody Paciente paciente){
        try{
            pacienteService.guardarPaciente(paciente);
            return ResponseEntity.ok(paciente);
        }catch( Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente un paciente", description = "Actualiza especificamente un paciente existente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Paciente actualizado exitosamente",
                        content = @Content(mediaType = "aplication/json",
                                schema = @Schema(implementation = Medico.class))),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    public ResponseEntity<Paciente> patch(@PathVariable Long id, @RequestBody Paciente partialPaciente) {
        try {
            Paciente updatedPaciente = pacienteService.editarPaciente(id, partialPaciente);
            return ResponseEntity.ok(updatedPaciente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un paciente", description = "Eliminar un paciente existente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Paciente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/resumen")
    public ResponseEntity<List<Paciente>> resumen(){
        return this.listar();
    }

}
