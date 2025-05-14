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

import com.neurotecno.cl.neurotecno.model.Paciente;
import com.neurotecno.cl.neurotecno.service.PacienteService;

@RestController
@RequestMapping("/api/v1/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;
    
    @GetMapping
    public ResponseEntity<List<Paciente>> listar(){
        List <Paciente> pacientes = pacienteService.obtenerPacientes();
        if(pacientes.isEmpty()){return ResponseEntity.noContent().build();}
        return ResponseEntity.ok(pacientes);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscar(@PathVariable Long id){
        Paciente paciente = pacienteService.obtenerPacientePorId(id);
        if (paciente == null)return ResponseEntity.notFound().build();
        return ResponseEntity.ok(paciente);
    }

    @PostMapping
    public ResponseEntity<Paciente> guardar(@RequestBody Paciente paciente) {
    
        Paciente pacienteNuevo = pacienteService.guardarPaciente(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteNuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizar(@PathVariable Long id, @RequestBody Paciente paciente){
        try{
            pacienteService.guardarPaciente(paciente);
            return ResponseEntity.ok(paciente);
        }catch( Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Paciente> patch(@PathVariable Long id, @RequestBody Paciente partialPaciente) {
        try {
            Paciente updatedPaciente = pacienteService.editarPaciente(id, partialPaciente);
            return ResponseEntity.ok(updatedPaciente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
