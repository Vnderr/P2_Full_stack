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

import com.neurotecno.cl.neurotecno.assemblers.TipoUsuarioModelAssembler;
import com.neurotecno.cl.neurotecno.model.TipoUsuario;
import com.neurotecno.cl.neurotecno.service.TipoUsuarioService;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/tipousuario")
public class TipoUsuarioControllerV2 {

    @Autowired
    private TipoUsuarioService tipousuarioService;

    @Autowired
    private TipoUsuarioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_FORMS_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<TipoUsuario>>> getListarTipoUsuario(){
        List <EntityModel<TipoUsuario>> tipoUsuarios = tipousuarioService.obtenerTipoUsuarios().stream()
                  .map(assembler::toModel)
                  .collect(Collectors.toList());
        if(tipoUsuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
            tipoUsuarios,
            linkTo(methodOn(TipoUsuarioControllerV2.class).getListarTipoUsuario()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_FORMS_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoUsuario>> getBuscarporID(@PathVariable Long id){
        /*
        * 
        TipoUsuario tipoUsuario = TipoUsuarioService.obtenerTipoUsuarioPorId(id) {
            if (tipoUsuario == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(tipoUsuario));
        */
        return null
    }   
    

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoUsuario>> guardar(@RequestBody TipoUsuario tipoUsuario) {
        TipoUsuario newTipoUsuario = tipousuarioService.guardarTipoUsuario(tipoUsuario);
        return ResponseEntity
            .created(linkTo(methodOn(TipoUsuarioControllerV2.class).getBuscarporID(newTipoUsuario.getId())).toUri()
            .body(assembler.toModel(newTipoUsuario)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoUsuario> actualizar(@PathVariable Long id, @RequestBody TipoUsuario tipoUsuario){
        try{
            tipousuarioService.guardarTipoUsuario(tipoUsuario);
            return ResponseEntity.ok(tipoUsuario);
        }catch( Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TipoUsuario> patch(@PathVariable Long id, @RequestBody TipoUsuario tipoUsuarioExistente) {
        try {
            TipoUsuario actualizar = tipousuarioService.editarTipoUsuario(id, tipoUsuarioExistente);
            return ResponseEntity.ok(actualizar);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            tipousuarioService.eliminarTipoUsuario(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }


















}
