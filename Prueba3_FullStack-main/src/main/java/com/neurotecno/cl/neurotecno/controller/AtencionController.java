package com.neurotecno.cl.neurotecno.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.neurotecno.cl.neurotecno.model.Atencion;
import com.neurotecno.cl.neurotecno.service.AtencionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;



@RestController
@RequestMapping("/api/v1/atenciones")
@Tag(name = "Atenciones", description = "Operaciones relacionadas con las atenciones")
public class AtencionController {

    @Autowired
    private AtencionService atencionService;

    @GetMapping("")
    @Operation(summary = "Obtener todas las carreras", description = "Obtiene una lista de todas las atenciones")
    public ResponseEntity<List<Atencion>> listar() {
        List<Atencion> atenciones = atencionService.obtenerAtenciones();
        if (atenciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(atenciones);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar una atencion", description = "Buscar una atencion existente")
    public ResponseEntity<Atencion> buscarAtencionPorId(@PathVariable Long id) {
        Atencion atencion = atencionService.obtenerAtencionPorId(id);
        if (atencion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atencion);
    }

    @PostMapping
    @Operation(summary = "Crear atencion", description = "Crear una atención")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atencion creada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Atencion no encontrada")
    })

    public ResponseEntity<Atencion> crearAtencion(@RequestBody Atencion atencion) {
        Atencion nuevaAtencion = atencionService.guardarAtencion(atencion);
        return ResponseEntity.status(201).body(nuevaAtencion);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una atencion", description = "Actualizar una atención existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atencion actualizada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Atencion.class))),
            @ApiResponse(responseCode = "404", description = "Atencion no encontrada")
    })
    public ResponseEntity<Atencion> actualizar(@PathVariable Long id,@RequestBody Atencion atencion) {
        Atencion atencionActualizada = atencionService.actualizarAtencion(id, atencion);
        if (atencionActualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atencionActualizada);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente una atencion", description = "Actualizar especificamente una atención")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atencion actualizada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Atencion.class))),
            @ApiResponse(responseCode = "404", description = "Atencion no encontrada")
    })
    public ResponseEntity<Atencion> patch(@PathVariable Long id,@RequestBody Atencion atencion) {
        Atencion atencionActualizada = atencionService.editarAtencion(id, atencion);
        if (atencionActualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atencionActualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar atencion", description = "Eliminar una atención existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atencion eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Atencion no encontrada")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        System.out.println(id);
        atencionService.eliminarAtencion(id);
        return ResponseEntity.noContent().build();
    } 



}
