package com.neurotecno.cl.neurotecno.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
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
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import com.neurotecno.cl.neurotecno.assemblers.MedicoModelAssembler;
import com.neurotecno.cl.neurotecno.model.Medico;
import com.neurotecno.cl.neurotecno.service.MedicoService;

@RestController
@RequestMapping("api/v2/medicos")
public class MedicoControllerV2 {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private MedicoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Medico>>> listar() {
        List<EntityModel<Medico>> medicos = medicoService.obtenerMedicos().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());
        if (medicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                medicos,
                linkTo(methodOn(MedicoControllerV2.class).listar()).withSelfRel()
        ));
    }
    @GetMapping(value ="/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Medico>> buscar(@PathVariable Long id) {
        Medico medico = medicoService.obtenerMedicoPorId( id);
        if (medico == null){
            return ResponseEntity.notFound().build();
        }      
        return ResponseEntity.ok(assembler.toModel(medico));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Medico>> guardar(@RequestBody Medico medico) {
        Medico medicoNuevo = medicoService.guardarMedico(medico);
        return ResponseEntity.created(linkTo(methodOn(MedicoControllerV2.class).guardar(medicoNuevo.getId())).toUri())
                .body(assembler.toModel(medicoNuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Medico>> actualizar(@PathVariable Long id, @RequestBody Medico medico) {
        medico.setId(id);
        Medico medicoActualizado = medicoService.actualizarMedico(id, medico);
        return ResponseEntity.ok(assembler.toModel(medicoActualizado));
        
    }
    

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Medico>> patchMedico(@PathVariable Long id, @RequestBody Medico medico) {
        Medico medicoActualizado = medicoService.editarMedico(id, medico);
        if (medicoActualizado == null) {
            return ResponseEntity.notFound().build();
        } 
        return ResponseEntity.ok(assembler.toModel(medicoActualizado));
        
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Medico medico = medicoService.obtenerMedicoPorId(id);
        if (medico == null) {
            return ResponseEntity.notFound().build();
        } 
        medicoService.eliminarMedico(id);
        return ResponseEntity.noContent().build();
        }
    }