package com.neurotecno.cl.neurotecno.repository;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.neurotecno.cl.neurotecno.model.Paciente;
import com.neurotecno.cl.neurotecno.model.TipoUsuario;



@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Paciente findByCorreo(String correo);
    List<Paciente> findByApellidos(String apellidos);
    List<Paciente> findByNombresAndApellidos(String nombres, String apellidos);

    List<Paciente> findByTipoUsuario(TipoUsuario tipoUsuario);

    @Query(value="SELECT * FROM PACIENTE WHERE id IN (SELECT paciente_id FROM atencion WHERE atencion.id = ?1);",nativeQuery = true)
    List<Paciente> findByAtencionID(long atencionID);

}

// t,
// controller