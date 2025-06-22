package com.neurotecno.cl.neurotecno.repository;
import com.neurotecno.cl.neurotecno.model.Atencion;
import com.neurotecno.cl.neurotecno.model.Medico;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;



@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long>  {
    @Query(value="SELECT * FROM MEDICO  WHERE m.especialidad = ?1",nativeQuery = true)
    List<Medico> findByEspecialidad(String especialidad);

    
    // porque  m.[X] funciona si no esta definido como SELECT * FROM MEDICO m
    // acaso eso o simplemente no funciona lol 
    // query 1, ti
    @Query(value="SELECT * FROM MEDICO WHERE m.especialidad = ?1 AND m.jefe_turno = ?2",nativeQuery = true)
    List<Medico> findByJefeYEspecialidad(String especialidad,String jefe);

    //List<Medico> findByAtencion(Atencion atencion);
}
