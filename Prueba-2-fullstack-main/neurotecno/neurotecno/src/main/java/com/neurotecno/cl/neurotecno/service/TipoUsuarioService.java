package com.neurotecno.cl.neurotecno.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.neurotecno.cl.neurotecno.model.TipoUsuario;
import com.neurotecno.cl.neurotecno.repository.TipoUsuarioRepository;

@Service
public class TipoUsuarioService {
    @Autowired
    private TipoUsuarioRepository repository;

    public List<TipoUsuario> obtenerTipoUsuarios() {return repository.findAll(); }
    
    public TipoUsuario obtenerTipoUsuarioPorId(Long id) {return repository.findById(id).orElse(null);}

    public TipoUsuario guardarTipoUsuario(TipoUsuario atencion) {return repository.save(atencion);}
    
    public void eliminarTipoUsuario(Long id) {repository.deleteById(id);}    

    public TipoUsuario actualizarTipoUsuario(Long id, TipoUsuario tipoUsuario) {
        TipoUsuario tipoUsuarioExistente = repository.findById(id).orElse(null);
        if (tipoUsuarioExistente != null) {
            tipoUsuarioExistente.setNombre(tipoUsuario.getNombre());
            return repository.save(tipoUsuarioExistente);
        } else {
            return null;
        }
    }

    public TipoUsuario editarTipoUsuario(Long id, TipoUsuario tipoUsuario) {
        TipoUsuario tipoUsuarioExistente = repository.findById(id).orElse(null);
        if (tipoUsuarioExistente != null) {
            if (tipoUsuario.getNombre() != null) tipoUsuarioExistente.setNombre(tipoUsuario.getNombre());
            return repository.save(tipoUsuarioExistente);
        } else {
            return null;
        }
    }



}
