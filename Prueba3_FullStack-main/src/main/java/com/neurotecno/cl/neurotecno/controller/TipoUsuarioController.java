package com.neurotecno.cl.neurotecno.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.neurotecno.cl.neurotecno.model.Medico;
import com.neurotecno.cl.neurotecno.model.TipoUsuario;
import com.neurotecno.cl.neurotecno.service.TipoUsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/tipousuario")
public class TipoUsuarioController {

    @Autowired
    private TipoUsuarioService tipousuarioService;

    @GetMapping
    @Operation(summary = "Obtener todos los tipos de usuarios", description = "Obtener una lista de todos los tipos de usuarios")
    public ResponseEntity<List<TipoUsuario>> listar(){
        List <TipoUsuario> tipoUsuarios = tipousuarioService.obtenerTipoUsuarios();
        if(tipoUsuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tipoUsuarios);
    }

    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar un tipo de usuario", description = "Buscar un tipo de usuario existente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Tipo de usuario encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuario no encontrado")
    })
    public ResponseEntity<TipoUsuario> buscar(@PathVariable Long id){
        TipoUsuario tipoUsuario = tipousuarioService.obtenerTipoUsuarioPorId(id);
        if (tipoUsuario == null)return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tipoUsuario);
    }   
    

    @PostMapping
    @Operation(summary = "Crear un tipo de usuario", description = "Actualizar un tipo de usuario existente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Tipo de usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuario no encontrado")
    })
    public ResponseEntity<TipoUsuario> crearTipoUsuario(@RequestBody TipoUsuario tipoUsuario) {
        
        TipoUsuario tipoUsuario2 = tipousuarioService.guardarTipoUsuario(tipoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoUsuario2);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un Tipo de usuario", description = "Actualizar un tipo de usuario existente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Tipo de usuario actualizado exitosamente",
                        content = @Content(mediaType = "aplication/json",
                                schema = @Schema(implementation = Medico.class))),
            @ApiResponse(responseCode = "404", description = "Tipo de usuario no encontrado")
    })
    public ResponseEntity<TipoUsuario> actualizar(@PathVariable Long id, @RequestBody TipoUsuario tipoUsuario){
        try{
            tipousuarioService.guardarTipoUsuario(tipoUsuario);
            return ResponseEntity.ok(tipoUsuario);
        }catch( Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente un tipo de usuario", description = "Actualizar especificamente un tipo de usuario existente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Tipo de usuario actualizado exitosamente",
                        content = @Content(mediaType = "aplication/json",
                                schema = @Schema(implementation = Medico.class))),
            @ApiResponse(responseCode = "404", description = "Tipo de usuario no encontrado")
    })
    public ResponseEntity<TipoUsuario> patch(@PathVariable Long id, @RequestBody TipoUsuario tipoUsuarioExistente) {
        try {
            TipoUsuario actualizar = tipousuarioService.editarTipoUsuario(id, tipoUsuarioExistente);
            return ResponseEntity.ok(actualizar);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un tipo de usuario", description = "Eliminar un tipo de usuario existente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Tipo de usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuario no encontrado")
    })
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            tipousuarioService.eliminarTipoUsuario(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }


















}

