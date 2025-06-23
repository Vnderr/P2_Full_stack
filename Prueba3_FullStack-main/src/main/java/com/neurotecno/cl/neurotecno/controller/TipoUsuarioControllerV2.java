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
import com.neurotecno.cl.neurotecno.model.Medico;
import com.neurotecno.cl.neurotecno.model.TipoUsuario;
import com.neurotecno.cl.neurotecno.service.TipoUsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v2/tipousuario")
@Tag(name = "Tipo Usuario", description = "Operaciones relacionadas con los Tipos de Usuario")
public class TipoUsuarioControllerV2 {

    @Autowired
    private TipoUsuarioService tipousuarioService;

    @Autowired
    private TipoUsuarioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los tipos de usuarios", description = "Obtener una lista de todos los tipos de usuarios")
    public CollectionModel<EntityModel<TipoUsuario>> listar(){
        List <EntityModel<TipoUsuario>> tipoUsuarios = tipousuarioService.obtenerTipoUsuarios().stream().map(assembler::toModel)
        .collect(Collectors.toList());
        return CollectionModel.of(
            tipoUsuarios,
            linkTo(methodOn(TipoUsuarioControllerV2.class).listar()).withSelfRel()
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_FORMS_JSON_VALUE)
    @Operation(summary = "Buscar un tipo de usuario", description = "Buscar un tipo de usuario existente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Tipo de usuario encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuario no encontrado")
    })
    public ResponseEntity<EntityModel<TipoUsuario>> buscar(@PathVariable Long id){
        TipoUsuario tipoUsuario = tipousuarioService.obtenerTipoUsuarioPorId(id);
        if (tipoUsuario == null) {
        return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(tipoUsuario));
    } 
    

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear un tipo de usuario", description = "Actualizar un tipo de usuario existente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Tipo de usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuario no encontrado")
    })
    public ResponseEntity<EntityModel<TipoUsuario>> crearTipoUsuario(@RequestBody TipoUsuario tipoUsuario) {
        TipoUsuario nuevoTipoUsuario = tipousuarioService.guardarTipoUsuario(tipoUsuario);
        return ResponseEntity
            .created(linkTo(methodOn(TipoUsuarioControllerV2.class).buscar((long)(nuevoTipoUsuario.getId()))).toUri())
            .body(assembler.toModel(nuevoTipoUsuario));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar un Tipo de usuario", description = "Actualizar un tipo de usuario existente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Tipo de usuario actualizado exitosamente",
                        content = @Content(mediaType = "aplication/json",
                                schema = @Schema(implementation = Medico.class))),
            @ApiResponse(responseCode = "404", description = "Tipo de usuario no encontrado")
    })
    public ResponseEntity<EntityModel<TipoUsuario>> actualizar(@PathVariable Long id, @RequestBody TipoUsuario tipoUsuario){
        tipoUsuario.setId(id.intValue());
        TipoUsuario updateTipoUsuario = tipousuarioService.guardarTipoUsuario(tipoUsuario);
        if(updateTipoUsuario == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updateTipoUsuario));
        
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
     @Operation(summary = "Actualizar parcialmente un tipo de usuario", description = "Actualizar especificamente un tipo de usuario existente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Tipo de usuario actualizado exitosamente",
                        content = @Content(mediaType = "aplication/json",
                                schema = @Schema(implementation = Medico.class))),
            @ApiResponse(responseCode = "404", description = "Tipo de usuario no encontrado")
    })
    public ResponseEntity<EntityModel<TipoUsuario>> patchTipoUsuario(@PathVariable Long id, @RequestBody TipoUsuario tipoUsuario) {
        TipoUsuario actualizar = tipousuarioService.editarTipoUsuario(id, tipoUsuario);
        if (actualizar == null) {
            return ResponseEntity.notFound().build();
        }   
        return ResponseEntity.ok(assembler.toModel(actualizar));
        
    }
    
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar un tipo de usuario", description = "Eliminar un tipo de usuario existente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Tipo de usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuario no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        TipoUsuario tipoUsuario = tipousuarioService.obtenerTipoUsuarioPorId(id);
        if (tipoUsuario == null ){
            return ResponseEntity.notFound().build();
        }
        tipousuarioService.eliminarTipoUsuario(id);
        return ResponseEntity.noContent().build();
    }





}
