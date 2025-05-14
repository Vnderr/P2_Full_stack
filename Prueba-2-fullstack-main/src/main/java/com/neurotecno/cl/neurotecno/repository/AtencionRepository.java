package com.neurotecno.cl.neurotecno.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.neurotecno.cl.neurotecno.model.Atencion;
import org.springframework.data.jpa.repository.Query;


@Repository
public interface AtencionRepository extends JpaRepository<Atencion, Long> {

    @Query(value="SELECT * FROM ATENCION WHERE paciente_id = ?1",nativeQuery = true)
    List<Atencion> findByPacienteId(Long pacienteId);

    @Query(value="SELECT * FROM ATENCION WHERE medico_id = ?1",nativeQuery = true)
    List<Atencion> findByMedicoId(Long medicoId);
        
}

