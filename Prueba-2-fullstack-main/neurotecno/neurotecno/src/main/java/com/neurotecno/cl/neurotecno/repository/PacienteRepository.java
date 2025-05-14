package com.neurotecno.cl.neurotecno.repository;
import com.neurotecno.cl.neurotecno.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

}
