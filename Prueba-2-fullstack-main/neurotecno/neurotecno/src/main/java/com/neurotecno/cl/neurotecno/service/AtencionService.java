package com.neurotecno.cl.neurotecno.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neurotecno.cl.neurotecno.model.Atencion;
import com.neurotecno.cl.neurotecno.repository.AtencionRepository;

@Service
public class AtencionService {

    @Autowired
    private AtencionRepository atencionRepository;

    public List<Atencion> obtenerAtenciones() {
        return atencionRepository.findAll();
    }
    public Atencion obtenerAtencionPorId(Long id) {
        return atencionRepository.findById(id).orElse(null);
    }
    public Atencion guardarAtencion(Atencion atencion) {
        return atencionRepository.save(atencion);
    }
    public void eliminarAtencion(Long id) {
        atencionRepository.deleteById(id);
    }
    // put
    public Atencion actualizarAtencion(Long id, Atencion atencion) {
        Atencion atencionExistente = atencionRepository.findById(id).orElse(null);
        if (atencionExistente != null) {
            atencionExistente.setFechaAtencion(atencion.getFechaAtencion());
            atencionExistente.setHoraAtencion(atencion.getHoraAtencion());
            atencionExistente.setPaciente(atencion.getPaciente());
            atencionExistente.setMedico(atencion.getMedico());
            return atencionRepository.save(atencionExistente);
        } else {
            return null;
        }
    }
    // patch
    public Atencion editarAtencion(Long id, Atencion atencion) {
        Atencion atencionExistente = atencionRepository.findById(id).orElse(null);
        if (atencionExistente != null) {
            if (atencion.getFechaAtencion() != null) atencionExistente.setFechaAtencion(atencion.getFechaAtencion());
            if (atencion.getHoraAtencion() != null) atencionExistente.setHoraAtencion(atencion.getHoraAtencion());
            if (atencion.getPaciente() != null) atencionExistente.setPaciente(atencion.getPaciente());
            if (atencion.getMedico() != null) atencionExistente.setMedico(atencion.getMedico());

            return atencionRepository.save(atencionExistente);
        } else {
            return null;
        }
    }
















}
