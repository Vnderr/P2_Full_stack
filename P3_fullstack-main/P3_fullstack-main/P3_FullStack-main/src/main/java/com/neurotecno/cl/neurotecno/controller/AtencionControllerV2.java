package com.neurotecno.cl.neurotecno.controller;


import java.util.List;

import org.apache.tomcat.util.http.parser.MediaType;
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



@RestController
@RequestMapping("/api/v2/atenciones")
public class AtencionControllerV2 {

    @Autowired
    private AtencionService atencionService;

    @Autowired
    private AtencionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Atencion>>> listar() {
        List<EntityModel<Atencion>> atenciones = atencionService.obtenerAtenciones().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (atenciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
               atenciones, 
               linkTo(methodOn(AtencionControllerV2.class).listar()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces =MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Atencion>> buscarAtencionPorId(@PathVariable Long id) {
        Atencion atencion = atencionService.obtenerAtencionPorId(id);
        if (atencion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(atencion));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Atencion>> guardar(@RequestBody Atencion atencion) {
        Atencion nuevaAtencion = atencionService.guardarAtencion(atencion);
        return ResponseEntity
                .created(linkTo(methodOn(AtencionControllerV2.class).buscarAtencionPorId(nuevaAtencion.getId())).toUri())
                .body(assembler.toModel(nuevaAtencion));
    }

    @PutMapping(value = "/{id}", produces = MeiaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Atencion>> actualizarAtencion (@PathVariable Long id,@RequestBody Atencion atencion) {
        Atencion atencionActualizada = atencionService.actualizarAtencion(id, atencion);
        atencion.setId(id);
        return ResponseEntity.ok(assembler.toModel(actualizarAtencion));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Atencion>> patchAtencion (@PathVariable Long id,@RequestBody Atencion atencion) {
        Atencion atencionActualizada = atencionService.editarAtencion(id, atencion);
        if (atencionActualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(atencionActualizada));
    }

    @DeleteMapping(value ="/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Atencion atencion = atencionService.findById(id);
        if(atencion == null){
            return ResponseEntity.notFound().build();
        }
        atencionService.eliminarAtencion(id);
        return ResponseEntity.noContent().build();
    } 

}
