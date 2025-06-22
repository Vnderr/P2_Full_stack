package com.neurotecno.cl.neurotecno.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.neurotecno.cl.neurotecno.model.Medico;
import com.neurotecno.cl.neurotecno.repository.MedicoRepository;
import com.neurotecno.cl.neurotecno.repository.AtencionRepository;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private AtencionRepository atencionRepository;

    public List<Medico> obtenerMedicos() {return medicoRepository.findAll(); }
    
    public Medico obtenerMedicoPorId(Long id) {return medicoRepository.findById(id).orElse(null);}

    public Medico guardarMedico(Medico atencion) {return medicoRepository.save(atencion);}
    
    public void eliminarMedico(Long id) {medicoRepository.deleteById(id);}    
    public List<Medico> obtenerPorEspecialidad(String especialidad) {
        return medicoRepository.findByEspecialidad(especialidad);
    }    


    public Medico actualizarMedico(Long id, Medico medico) {
        Medico medicoExistente = medicoRepository.findById(id).orElse(null);
        if (medicoExistente != null) {
            medicoExistente.setEspecialidad(medico.getEspecialidad());
            medicoExistente.setJefeTurno(medico.getJefeTurno());
            medicoExistente.setNombreCompleto(medico.getNombreCompleto());
            medicoExistente.setRun(medico.getRun());
            return medicoRepository.save(medicoExistente);
        } else {
            return null;
        }
    }
    public Medico editarMedico(Long id, Medico medico) {
        Medico medicoExistente = medicoRepository.findById(id).orElse(null);
        if (medicoExistente != null) {
            if (medico.getEspecialidad() != null) medicoExistente.setEspecialidad(medico.getEspecialidad());
            if (medico.getJefeTurno() != null) medicoExistente.setJefeTurno(medico.getJefeTurno());
            if (medico.getNombreCompleto() != null) medicoExistente.setNombreCompleto(medico.getNombreCompleto());
            if (medico.getRun() != null) medicoExistente.setRun(medico.getRun());
            return medicoRepository.save(medicoExistente);
        } else {
            return null;
        }
    }

    public void  deleteById(Long id) {
        Medico medico = medicoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Medico no encontrado"));

        atencionRepository.deleteByMedico(medico);

        medicoRepository.delete(medico);
    }
    



}
