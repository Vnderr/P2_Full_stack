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



@RestController
@RequestMapping("/api/v1/atenciones")
public class AtencionController {

    @Autowired
    private AtencionService atencionService;

    @GetMapping("")
    public ResponseEntity<List<Atencion>> listar() {
        List<Atencion> atenciones = atencionService.obtenerAtenciones();
        if (atenciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(atenciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atencion> buscarAtencionPorId(@PathVariable Long id) {
        Atencion atencion = atencionService.obtenerAtencionPorId(id);
        if (atencion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atencion);
    }

    @PostMapping
    public ResponseEntity<Atencion> guardar(@RequestBody Atencion atencion) {
        Atencion nuevaAtencion = atencionService.guardarAtencion(atencion);
        return ResponseEntity.status(201).body(nuevaAtencion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Atencion> actualizar(@PathVariable Long id,@RequestBody Atencion atencion) {
        Atencion atencionActualizada = atencionService.actualizarAtencion(id, atencion);
        if (atencionActualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atencionActualizada);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Atencion> patch(@PathVariable Long id,@RequestBody Atencion atencion) {
        Atencion atencionActualizada = atencionService.editarAtencion(id, atencion);
        if (atencionActualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atencionActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        System.out.println(id);
        atencionService.eliminarAtencion(id);
        return ResponseEntity.noContent().build();
    } 
    //@GetMapping("Resumen")



}
