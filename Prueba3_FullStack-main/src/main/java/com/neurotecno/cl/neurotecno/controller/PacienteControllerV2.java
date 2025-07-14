package com.neurotecno.cl.neurotecno.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.neurotecno.cl.neurotecno.dataclasses.MedicoFechaData;
import com.neurotecno.cl.neurotecno.model.Atencion;
import com.neurotecno.cl.neurotecno.model.TipoUsuario;
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
import com.neurotecno.cl.neurotecno.model.Medico;
import com.neurotecno.cl.neurotecno.model.Paciente;
import com.neurotecno.cl.neurotecno.service.PacienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v2/pacientes")
@Tag(name = "Pacientes", description = "Operaciones relacionadas con los pacientes")
public class PacienteControllerV2 {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private PacienteModelAssembler assembler;
    
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los pacientes", description = "Obtener una lista de todos los pacientes")
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
    @Operation(summary = "Buscar paciente", description = "Buscar un paciente existente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Paciente encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    public ResponseEntity<EntityModel<Paciente>> buscar(@PathVariable Long id){
        Paciente paciente = pacienteService.obtenerPacientePorId(id);
        if (paciente == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(paciente));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear paciente", description = "Crear paciente inexistente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Paciente creado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    public ResponseEntity<EntityModel<Paciente>> crearPaciente(@RequestBody Paciente paciente) {
        Paciente pacienteNuevo = pacienteService.guardarPaciente(paciente);
        return ResponseEntity.created(linkTo(methodOn(PacienteControllerV2.class).buscar((long)(pacienteNuevo.getId()))).toUri())
                .body(assembler.toModel(pacienteNuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar un paciente", description = "Actualizar un paciente existente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Paciente actualizado exitosamente",
                        content = @Content(mediaType = "aplication/json",
                                schema = @Schema(implementation = Medico.class))),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    public ResponseEntity<EntityModel<Paciente>> actualizar(@PathVariable Long id, @RequestBody Paciente paciente){
        paciente.setId(id.intValue());
        Paciente updatePaciente = pacienteService.guardarPaciente(paciente);
        if(updatePaciente == null){
            return ResponseEntity.notFound().build();
        }
            return ResponseEntity.ok(assembler.toModel(updatePaciente));
        
    }

    @PatchMapping(value = "/{id}")
    @Operation(summary = "Actualizar parcialmente un paciente", description = "Actualizar especificamente un paciente existente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Paciente actualizado exitosamente",
                        content = @Content(mediaType = "aplication/json",
                                schema = @Schema(implementation = Medico.class))),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    public ResponseEntity<EntityModel<Paciente>> patchPaciente(@PathVariable Long id, @RequestBody Paciente paciente) {
            Paciente updatedPaciente = pacienteService.actualizarPaciente(id, paciente);
            if (updatedPaciente == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(updatedPaciente));
    }
    
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar un paciente", description = "Eliminar un paciente existente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Paciente actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        Paciente paciente = pacienteService.obtenerPacientePorId(id);
        if (paciente == null) {
            return ResponseEntity.notFound().build();
        }
        pacienteService.eliminarPaciente(id);
            return ResponseEntity.noContent().build();
        
    }

    @GetMapping(value = "/por-atencion/", produces =MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "obtener el paciente de una atencion", description = "obtener el paciente involucrado con una atencion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "paciente encontrado"),
    })
    public ResponseEntity<List<Paciente>> buscarPorAtencion(@RequestBody Atencion ate) {
        List<Paciente> pacientes = pacienteService.findByAtencion(ate);
        if (pacientes.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(pacientes);
    }


    @GetMapping(value = "/por-tipo-usuario/", produces =MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "obtiene a todos los usuarios de un tipo", description = "obtiene a todos los usuarios de un tipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "pacientes encontrados"),
            @ApiResponse(responseCode = "404", description = "no hay pacientes con ese tipo de usuario"),
    })
    public ResponseEntity<List<Paciente>> buscarPorTipoUsuario (@RequestBody TipoUsuario tipoUsuario) {
        List<Paciente> pacientes = pacienteService.findByTipoUsuario(tipoUsuario);
        if (pacientes.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(pacientes);
    }


}