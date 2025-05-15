package com.neurotecno.cl.neurotecno.repository;
import com.neurotecno.cl.neurotecno.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;



@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long>  {
    @Query(value="SELECT * FROM MEDICO  WHERE m.especialidad = ?1",nativeQuery = true)
    List<Medico> findByEspecialidad(String especialidad);


}
