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

import com.neurotecno.cl.neurotecno.model.TipoUsuario;
import com.neurotecno.cl.neurotecno.service.TipoUsuarioService;

@RestController
@RequestMapping("/api/v1/tipousuario")
public class TipoUsuarioController {

    @Autowired
    private TipoUsuarioService tipousuarioService;

    @GetMapping
    public ResponseEntity<List<TipoUsuario>> listar(){
        List <TipoUsuario> tipoUsuarios = tipousuarioService.obtenerTipoUsuarios();
        if(tipoUsuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tipoUsuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoUsuario> buscar(@PathVariable Long id){
        try{
            TipoUsuario tipoUsuario = tipousuarioService.obtenerTipoUsuarioPorId(id);
            return ResponseEntity.ok(tipoUsuario);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TipoUsuario> guardar(@RequestBody TipoUsuario tipoUsuario) {
        TipoUsuario tipoUsuario2 = tipousuarioService.guardarTipoUsuario(tipoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoUsuario2);
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

