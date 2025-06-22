package com.neurotecno.cl.neurotecno.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
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
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import com.neurotecno.cl.neurotecno.assemblers.PacienteModelAssembler;
import com.neurotecno.cl.neurotecno.model.Paciente;
import com.neurotecno.cl.neurotecno.service.PacienteService;

@RestController
@RequestMapping("/api/v2/pacientes")
public class PacienteControllerV2 {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private PacienteModelAssembler assembler;
    
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Paciente>> listar(){
        List<EntityModel<Paciente>> pacientes = pacienteService.obtenerPacientes().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(
                pacientes,
                linkTo(methodOn(PacienteControllerV2.class).listar()).withSelfRel()
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Paciente>> buscar(@PathVariable Long id){
        Paciente paciente = pacienteService.obtenerPacientePorId(id);
        if (paciente == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(paciente));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Paciente>> guardar(@RequestBody Paciente paciente) {
        Paciente pacienteNuevo = pacienteService.guardarPaciente(paciente);
        return ResponseEntity.created(linkTo(methodOn(PacienteControllerV2.class).buscar((long)(pacienteNuevo.getId()))).toUri())
                .body(assembler.toModel(pacienteNuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Paciente>> actualizar(@PathVariable Long id, @RequestBody Paciente paciente){
        paciente.setId(id.intValue());
        Paciente updatePaciente = pacienteService.guardarPaciente(paciente);
        if(updatePaciente == null){
            return ResponseEntity.notFound().build();
        }
            return ResponseEntity.ok(assembler.toModel(updatePaciente));
        
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<EntityModel<Paciente>> patchPaciente(@PathVariable Long id, @RequestBody Paciente paciente) {
            Paciente updatedPaciente = pacienteService.actualizarPaciente(id, paciente);
            if (updatedPaciente == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(updatedPaciente));
    }
    
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        Paciente paciente = pacienteService.obtenerPacientePorId(id);
        if (paciente == null) {
            return ResponseEntity.notFound().build();
        }
        pacienteService.eliminarPaciente(id);
            return ResponseEntity.noContent().build();
        
    }
}