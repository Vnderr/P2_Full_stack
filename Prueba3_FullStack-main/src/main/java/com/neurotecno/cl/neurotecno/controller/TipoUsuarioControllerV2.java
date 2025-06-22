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

import com.neurotecno.cl.neurotecno.assemblers.TipoUsuarioModelAssembler;
import com.neurotecno.cl.neurotecno.model.TipoUsuario;
import com.neurotecno.cl.neurotecno.service.TipoUsuarioService;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v2/tipousuario")
public class TipoUsuarioControllerV2 {

    @Autowired
    private TipoUsuarioService tipousuarioService;

    @Autowired
    private TipoUsuarioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<TipoUsuario>> listar(){
        List <EntityModel<TipoUsuario>> tipoUsuarios = tipousuarioService.obtenerTipoUsuarios().stream().map(assembler::toModel)
        .collect(Collectors.toList());
        return CollectionModel.of(
            tipoUsuarios,
            linkTo(methodOn(TipoUsuarioControllerV2.class).listar()).withSelfRel()
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_FORMS_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoUsuario>> buscar(@PathVariable Long id){
        TipoUsuario tipoUsuario = tipousuarioService.obtenerTipoUsuarioPorId(id);
        if (tipoUsuario == null) {
        return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(tipoUsuario));
    } 
    

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoUsuario>> guardar(@RequestBody TipoUsuario tipoUsuario) {
        TipoUsuario nuevoTipoUsuario = tipousuarioService.guardarTipoUsuario(tipoUsuario);
        return ResponseEntity
            .created(linkTo(methodOn(TipoUsuarioControllerV2.class).buscar((long)(nuevoTipoUsuario.getId()))).toUri())
            .body(assembler.toModel(nuevoTipoUsuario));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoUsuario>> actualizar(@PathVariable Long id, @RequestBody TipoUsuario tipoUsuario){
        tipoUsuario.setId(id.intValue());
        TipoUsuario updateTipoUsuario = tipousuarioService.guardarTipoUsuario(tipoUsuario);
        if(updateTipoUsuario == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updateTipoUsuario));
        
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoUsuario>> patchTipoUsuario(@PathVariable Long id, @RequestBody TipoUsuario tipoUsuario) {
        TipoUsuario actualizar = tipousuarioService.editarTipoUsuario(id, tipoUsuario);
        if (actualizar == null) {
            return ResponseEntity.notFound().build();
        }   
        return ResponseEntity.ok(assembler.toModel(actualizar));
        
    }
    
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        TipoUsuario tipoUsuario = tipousuarioService.obtenerTipoUsuarioPorId(id);
        if (tipoUsuario == null ){
            return ResponseEntity.notFound().build();
        }
        tipousuarioService.eliminarTipoUsuario(id);
        return ResponseEntity.noContent().build();
    }

}
