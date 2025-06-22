package com.neurotecno.cl.neurotecno.repository;
import com.neurotecno.cl.neurotecno.model.Atencion;
import com.neurotecno.cl.neurotecno.model.Paciente;
import com.neurotecno.cl.neurotecno.model.TipoUsuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    @Query("SELECT p FROM Paciente p where p.correo = ?1")
    Paciente findByCorreo(String correo);
    
    @Query(value="SELECT * FROM PACIENTE WHERE apellidos = ?1 ",nativeQuery=true)
    List<Paciente> findByApellidos(String apellidos);

    @Query(value="SELECT * FROM PACIENTE WHERE apellidos = ?1 and nombres = ?2 ",nativeQuery=true)
    List<Paciente> findByNombresAndApellidos(String nombres, String apellidos);
 
    //@Query("SELECT * from Paciente p where p.tipo_usuario = ?1")
    // List<Paciente> findByTipoUsuario(TipoUsuario tipoUsuario); 

    //@Query("SELECT * FROM Paciente p JOIN p.atenciones a WHERE a = :atencion")
     //List<Paciente> findByAtencion(@Param("atencion") Atencion atencion);

    //List<Paciente> findByAtencion(Atencion atencion);
}

// t,
// controller