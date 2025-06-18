package com.neurotecno.cl.neurotecno.repository;
import com.neurotecno.cl.neurotecno.model.Paciente;
import com.neurotecno.cl.neurotecno.model.TipoUsuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Long> {


    
}
