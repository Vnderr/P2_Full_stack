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
import com.neurotecno.cl.neurotecno.assemblers.AtencionModelAssembler;
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
                .created(linkTo(methodOn(AtencionControllerV2.class).buscarAtencionPorId((long)(nuevaAtencion.getId()))).toUri())
                .body(assembler.toModel(nuevaAtencion));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Atencion>> actualizarAtencion (@PathVariable Long id,@RequestBody Atencion atencion) {
        atencion.setId(id.intValue());
        Atencion atencionActualizada = atencionService.guardarAtencion(atencion);
        if(atencionActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(atencionActualizada));
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
        Atencion atencion = atencionService.obtenerAtencionPorId(id);
        if(atencion == null){
            return ResponseEntity.notFound().build();
        }
        atencionService.eliminarAtencion(id);
        return ResponseEntity.noContent().build();
    } 

}
