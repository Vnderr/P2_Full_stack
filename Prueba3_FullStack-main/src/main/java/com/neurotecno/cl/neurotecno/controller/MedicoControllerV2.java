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
import com.neurotecno.cl.neurotecno.assemblers.MedicoModelAssembler;
import com.neurotecno.cl.neurotecno.model.Medico;
import com.neurotecno.cl.neurotecno.service.MedicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v2/medicos")
@Tag(name = "Medicos", description = "Operaciones relacionadas con los medicos")
public class MedicoControllerV2 {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private MedicoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los medicos", description = "Obtiene una lista de todos los medicos")
    
    public CollectionModel<EntityModel<Medico>> listar() {
        List<EntityModel<Medico>> medicos = medicoService.obtenerMedicos().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        return CollectionModel.of(medicos,
                linkTo(methodOn(MedicoControllerV2.class).listar()).withSelfRel()
        );
    }
    @GetMapping(value ="/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar un medico", description = "Buscar un medico existente")
    public ResponseEntity<EntityModel<Medico>> buscar(@PathVariable Long id) {
        Medico medico = medicoService.obtenerMedicoPorId( id);
        if (medico == null){
            return ResponseEntity.notFound().build();
        }      
        return ResponseEntity.ok(assembler.toModel(medico));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear un medico", description = "Crear un medico inexistente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Medico creado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Medico no encontrado")
    })
    public ResponseEntity<EntityModel<Medico>> crearMedico(@RequestBody Medico medico) {
        Medico medicoNuevo = medicoService.guardarMedico(medico);
        return ResponseEntity.created(linkTo(methodOn(MedicoControllerV2.class).buscar((long)(medicoNuevo.getId()))).toUri())
                .body(assembler.toModel(medicoNuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar un medico", description = "Actualizar un medico existente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Medico actualizado exitosamente",
                        content = @Content(mediaType = "aplication/json",
                                schema = @Schema(implementation = Medico.class))),
            @ApiResponse(responseCode = "404", description = "Medico no encontrado")
    })
    public ResponseEntity<EntityModel<Medico>> actualizar(@PathVariable Long id, @RequestBody Medico medico) {
        medico.setId(id.intValue());
        Medico medicoActualizado = medicoService.guardarMedico(medico);
        if(medicoActualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(medicoActualizado));
        
    }
    

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente un medico", description = "Actualiza especificamente un medico existente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Medico actualizado exitosamente",
                        content = @Content(mediaType = "aplication/json",
                                schema = @Schema(implementation = Medico.class))),
            @ApiResponse(responseCode = "404", description = "Medico no encontrado")
    })
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