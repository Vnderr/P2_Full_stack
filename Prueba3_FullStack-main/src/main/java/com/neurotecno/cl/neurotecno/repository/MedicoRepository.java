package com.neurotecno.cl.neurotecno.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.neurotecno.cl.neurotecno.model.Medico;



@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long>  {
    @Query
    List<Medico> findByEspecialidad(String especialidad);
    List<Medico> findByJefeTurnoAndEspecialidad(String especialidad,String jefe);

    @Query(value="SELECT * FROM medico WHERE id IN (SELECT medico_id FROM atencion WHERE atencion.id = ?1);",nativeQuery = true)
    List<Medico> findByAtencionID(long atencionID);
}
