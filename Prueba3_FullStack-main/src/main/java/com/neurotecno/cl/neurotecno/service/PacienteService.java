package com.neurotecno.cl.neurotecno.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.neurotecno.cl.neurotecno.model.Atencion;
import com.neurotecno.cl.neurotecno.model.Paciente;
import com.neurotecno.cl.neurotecno.model.TipoUsuario;
import com.neurotecno.cl.neurotecno.repository.AtencionRepository;
import com.neurotecno.cl.neurotecno.repository.PacienteRepository;


@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienterepository;

    @Autowired
    private AtencionRepository atencionRepository;


    public List<Paciente> obtenerPacientes() {return pacienterepository.findAll(); }
    
    public Paciente obtenerPacientePorId(Long id) {return pacienterepository.findById(id).orElse(null);}

    public Paciente guardarPaciente(Paciente paciente) {
        return pacienterepository.save(paciente);
        }
    
    public void eliminarPaciente(Long id) {pacienterepository.deleteById(id);}    

    public Paciente actualizarPaciente(Long id, Paciente paciente) {
        Paciente pacienteExistente = pacienterepository.findById(id).orElse(null);
        if (pacienteExistente != null) {
            pacienteExistente.setApellidos(paciente.getApellidos());
            pacienteExistente.setCorreo(paciente.getCorreo());
            pacienteExistente.setFechaNacimiento(paciente.getFechaNacimiento());
            pacienteExistente.setNombres(paciente.getNombres());
            pacienteExistente.setRun(paciente.getRun());
            pacienteExistente.setTipoUsuario(paciente.getTipoUsuario());
            return pacienterepository.save(pacienteExistente);
        } else {
            return null;
        }
    }

    public Paciente editarPaciente(Long id, Paciente paciente) {
        Paciente pacienteExistente = pacienterepository.findById(id).orElse(null);
        if (pacienteExistente != null) {
            if (paciente.getApellidos() != null)pacienteExistente.setApellidos(paciente.getApellidos());
            if (paciente.getCorreo() != null)pacienteExistente.setCorreo(paciente.getCorreo());
            if (paciente.getFechaNacimiento() != null)pacienteExistente.setFechaNacimiento(paciente.getFechaNacimiento());
            if (paciente.getNombres() != null)pacienteExistente.setNombres(paciente.getNombres());
            if (paciente.getRun() != null)pacienteExistente.setRun(paciente.getRun());
            if (paciente.getTipoUsuario() != null)pacienteExistente.setTipoUsuario(paciente.getTipoUsuario());
            return pacienterepository.save(pacienteExistente);
        } else {
            return null;
        }
    }

    public List<Paciente> findByAtencion (Atencion atencion) {
        return pacienterepository.findByAtencionID(atencion.getId());
    }

    public List<Paciente> findByTipoUsuario (TipoUsuario tipoUsuario) {
        return pacienterepository.findByTipoUsuario(tipoUsuario) ;
    }

    public void  deleteById(Long id) {
    Paciente paciente = pacienterepository.findById(id)
    .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

    atencionRepository.deleteByPaciente(paciente);

    pacienterepository.delete(paciente);
    }


}
