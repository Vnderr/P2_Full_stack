package com.neurotecno.cl.neurotecno.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.neurotecno.cl.neurotecno.model.Paciente;
import com.neurotecno.cl.neurotecno.repository.PacienteRepository;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienterepository;

    public List<Paciente> obtenerPacientes() {return pacienterepository.findAll(); }
    
    public Paciente obtenerPacientePorId(Long id) {return pacienterepository.findById(id).orElse(null);}

    public Paciente guardarPaciente(Paciente atencion) {return pacienterepository.save(atencion);}
    
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


}
