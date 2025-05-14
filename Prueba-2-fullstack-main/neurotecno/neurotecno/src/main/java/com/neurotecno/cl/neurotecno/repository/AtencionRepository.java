package com.neurotecno.cl.neurotecno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neurotecno.cl.neurotecno.model.Atencion;

@Repository
public interface AtencionRepository extends JpaRepository<Atencion, Long> {



    List<Atencion> findByPacienteId(Long pacienteId);
    List<Atencion> findByMedicoId(Long medicoId);
}

