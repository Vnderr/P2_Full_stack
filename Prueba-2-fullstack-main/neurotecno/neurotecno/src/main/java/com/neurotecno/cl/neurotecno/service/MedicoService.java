package com.neurotecno.cl.neurotecno.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.neurotecno.cl.neurotecno.model.Medico;
import com.neurotecno.cl.neurotecno.repository.MedicoRepository;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository repository;

    public List<Medico> obtenerMedicos() {return repository.findAll(); }
    
    public Medico obtenerMedicoPorId(Long id) {return repository.findById(id).orElse(null);}

    public Medico guardarMedico(Medico atencion) {return repository.save(atencion);}
    
    public void eliminarMedico(Long id) {repository.deleteById(id);}    

    public Medico actualizarMedico(Long id, Medico medico) {
        Medico medicoExistente = repository.findById(id).orElse(null);
        if (medicoExistente != null) {
            medicoExistente.setEspecialidad(medico.getEspecialidad());
            medicoExistente.setJefeTurno(medico.getJefeTurno());
            medicoExistente.setNombreCompleto(medico.getNombreCompleto());
            medicoExistente.setRun(medico.getRun());
            return repository.save(medicoExistente);
        } else {
            return null;
        }
    }
    public Medico editarMedico(Long id, Medico medico) {
        Medico medicoExistente = repository.findById(id).orElse(null);
        if (medicoExistente != null) {
            if (medico.getEspecialidad() != null) medicoExistente.setEspecialidad(medico.getEspecialidad());
            if (medico.getJefeTurno() != null) medicoExistente.setJefeTurno(medico.getJefeTurno());
            if (medico.getNombreCompleto() != null) medicoExistente.setNombreCompleto(medico.getNombreCompleto());
            if (medico.getRun() != null) medicoExistente.setRun(medico.getRun());
            return repository.save(medicoExistente);
        } else {
            return null;
        }
    }


}
