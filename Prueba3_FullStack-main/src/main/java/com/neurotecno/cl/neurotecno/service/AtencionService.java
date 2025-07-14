package com.neurotecno.cl.neurotecno.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neurotecno.cl.neurotecno.model.Atencion;
import com.neurotecno.cl.neurotecno.model.Medico;
import com.neurotecno.cl.neurotecno.model.Paciente;
import com.neurotecno.cl.neurotecno.repository.AtencionRepository;
import com.neurotecno.cl.neurotecno.repository.MedicoRepository;
import com.neurotecno.cl.neurotecno.repository.PacienteRepository;

@Service
public class AtencionService {

    @Autowired
    private AtencionRepository atencionRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private MedicoRepository medicoRepository;

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
            atencionExistente.setCosto(atencion.getCosto());
            atencionExistente.setComentario(atencion.getComentario());
            atencionExistente.setTipousuario(atencion.getTipousuario());
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
            if (atencion.getCosto() != null)atencionExistente.setCosto(atencion.getCosto());
            if (atencion.getComentario() != null)atencionExistente.setComentario(atencion.getComentario());
            if (atencion.getTipousuario() != null)atencionExistente.setTipousuario(atencion.getTipousuario());

            return atencionRepository.save(atencionExistente);
        } else {
            return null;
        }
    }


    public List<Atencion> obtenerAtencionesPorPacienteId(Long pacienteId) {
        return atencionRepository.findByPacienteId(pacienteId);
    }
    public List<Atencion> obtenerAtencionesPorMedicoId(Long medicoId) {
        return atencionRepository.findByMedicoId(medicoId);
    }
    public List<Atencion> obtenerAtencionesPorPacienteIdYMedicoId(Long pacienteId, Long medicoId) {
        return atencionRepository.findByPacienteIdAndMedicoId(pacienteId,medicoId);
    }

    public List<Atencion> obtenerAtencionesPorFechayHora(LocalDate fechaAtencion, LocalTime horaAtencion) {
        return atencionRepository.findByFechaAtencionAndHoraAtencion(fechaAtencion,horaAtencion);
    }

    public List<Atencion> obtenerAtencionesPorFechayMedicoID(LocalDate fechaAtencion, Long medicoId) {
        return atencionRepository.findByFechayMedicoId(fechaAtencion,medicoId);
    }




   //eliminar por cascada
  public void deleteById(Long id) {
    Atencion atencion = atencionRepository.findById(id)
    .orElseThrow(() -> new RuntimeException("Atencion no encontrada"));

    List<Paciente> pacientes  =  pacienteRepository.findByAtencionID(atencion.getId());
    List<Medico> medicos  =  medicoRepository.findByAtencionID(atencion.getId());

    for (Paciente paciente : pacientes) {
            pacienteService.deleteById(Long.valueOf(paciente.getId()));
        }


    for (Medico medico : medicos) {
            medicoService.deleteById(Long.valueOf(medico.getId()));
    }

    atencionRepository.delete(atencion);
    }






}
