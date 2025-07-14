package com.neurotecno.cl.neurotecno.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.neurotecno.cl.neurotecno.dataclasses.FechaHoraData;
import com.neurotecno.cl.neurotecno.dataclasses.MedicoFechaData;
import com.neurotecno.cl.neurotecno.dataclasses.MedicoPacienteData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import com.neurotecno.cl.neurotecno.assemblers.AtencionModelAssembler;
import com.neurotecno.cl.neurotecno.model.Atencion;
import com.neurotecno.cl.neurotecno.service.AtencionService;


@RestController
@RequestMapping("/api/v2/atenciones")
@Tag(name = "Atenciones", description = "Operaciones relacionadas con las atenciones")
public class AtencionControllerV2 {

    @Autowired
    private AtencionService atencionService;

    @Autowired
    private AtencionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las atenciones", description = "Obtiene una lista de todas las atenciones")
    public CollectionModel<EntityModel<Atencion>> listar(){
        atencionService.obtenerAtenciones().stream().map(assembler::toModel);
        List<EntityModel<Atencion>> atenciones = atencionService.obtenerAtenciones().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());
        
        return CollectionModel.of(atenciones,
            linkTo(methodOn(TipoUsuarioControllerV2.class).listar()).withSelfRel()
        );
    }
    
    @GetMapping(value = "/{id}", produces =MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar atencion", description = "Buscar una atención existente")
    public ResponseEntity<EntityModel<Atencion>> buscarAtencionPorId(@PathVariable Long id) {
        Atencion atencion = atencionService.obtenerAtencionPorId(id);
        if (atencion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(atencion));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear atencion", description = "Crear una atención")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atencion creada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Atencion no encontrada")
    })
    public ResponseEntity<EntityModel<Atencion>> crearAtencion(@RequestBody Atencion atencion) {
        Atencion nuevaAtencion = atencionService.guardarAtencion(atencion);
        return ResponseEntity
                .created(linkTo(methodOn(AtencionControllerV2.class).buscarAtencionPorId((long)(nuevaAtencion.getId()))).toUri())
                .body(assembler.toModel(nuevaAtencion));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar atencion", description = "Actualizar una atención existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atencion actualizada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Atencion.class))),
            @ApiResponse(responseCode = "404", description = "Atencion no encontrada")
    })
    public ResponseEntity<EntityModel<Atencion>> actualizarAtencion (@PathVariable Long id,@RequestBody Atencion atencion) {
        atencion.setId(id.intValue());
        Atencion atencionActualizada = atencionService.guardarAtencion(atencion);
        if(atencionActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(atencionActualizada));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente una atencion", description = "Actualizar especificamente una atención")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atencion actualizada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Atencion.class))),
            @ApiResponse(responseCode = "404", description = "Atencion no encontrada")
    })
    public ResponseEntity<EntityModel<Atencion>> patchAtencion (@PathVariable Long id,@RequestBody Atencion atencion) {
        Atencion atencionActualizada = atencionService.editarAtencion(id, atencion);
        if (atencionActualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(atencionActualizada));
    }

    @DeleteMapping(value ="/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar atencion", description = "Eliminar una atención existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atencion eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Atencion no encontrada")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Atencion atencion = atencionService.obtenerAtencionPorId(id);
        if(atencion == null){
            return ResponseEntity.notFound().build();
        }
        atencionService.eliminarAtencion(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping(value = "/por-paciente/{pacienteId}", produces =MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "obtener atenciones paciente", description = "obtener todas las atenciones en la que un paciente ha estado involucrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atenciones encontradas"),
            @ApiResponse(responseCode = "404", description = "No hay atenciones")
    })
    public ResponseEntity<List<Atencion>> BuscarAtencionesPorPacienteId(@PathVariable Long pacienteId) {
        List<Atencion> atenciones = atencionService.obtenerAtencionesPorPacienteId(pacienteId);
        if (atenciones.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(atenciones);
    }

    @GetMapping(value = "/por-medico/{medicoId}", produces =MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "obtener atenciones medico", description = "obtener todas las atenciones en la que un medico ha estado involucrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atenciones encontradas"),
            @ApiResponse(responseCode = "404", description = "No hay atenciones")
    })
    public ResponseEntity<List<Atencion>> BuscarAtencionesPorMedico(@PathVariable Long medicoId) {
        List<Atencion> atenciones = atencionService.obtenerAtencionesPorMedicoId(medicoId);
        if (atenciones.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(atenciones);
    }

    @GetMapping(value = "/por-medico-paciente/", produces =MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "obtener atenciones medico y paciente ", description = "obtener todas las atenciones en la que un medico ha estado involucrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atenciones encontradas"),
            @ApiResponse(responseCode = "404", description = "el paciente y medico no han tenido ninguna atencion")
    })

    public ResponseEntity<List<Atencion>> BuscarAtencionesPorPacienteYMedico(@RequestBody MedicoPacienteData info) {
        List<Atencion> atenciones = atencionService.obtenerAtencionesPorPacienteIdYMedicoId(info.getMedico().getId().longValue(),info.getPaciente().getId().longValue());
        if (atenciones.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(atenciones);
    }

    @GetMapping(value = "/por-fecha-hora/", produces =MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "obtener atenciones en fecha y hora", description = "obtener todas las atenciones en una fecha y hora")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atenciones encontradas"),
            @ApiResponse(responseCode = "404", description = "no hay atenciones en esa fecha y/o hora.")
    })
    public ResponseEntity<List<Atencion>> BuscarAtencionesPorFechayHora(@RequestBody FechaHoraData datos) {
        List<Atencion> atenciones = atencionService.obtenerAtencionesPorFechayHora(datos.getFechaAtencion(), datos.getHoraAtencion());
        if (atenciones.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(atenciones);
    }


    @GetMapping(value = "/por-medico-fecha/", produces =MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "obtener atenciones en fecha y hora", description = "obtener todas las atenciones en una fecha y hora")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atenciones encontradas"),
            @ApiResponse(responseCode = "404", description = "no hay atenciones en esa fecha y/o hora.")
    })
    public ResponseEntity<List<Atencion>> BuscarAtencionesPorFechayMedico(@RequestBody MedicoFechaData datos) {
        System.out.println(datos.getFechaAtencion());
        System.out.println(datos.getMedico());

        List<Atencion> atenciones = atencionService.obtenerAtencionesPorFechayMedicoID(datos.getFechaAtencion(), datos.getMedico().getId().longValue());
        if (atenciones.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(atenciones);
    }





}
