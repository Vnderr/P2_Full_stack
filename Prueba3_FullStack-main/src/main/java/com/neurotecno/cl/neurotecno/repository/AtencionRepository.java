package com.neurotecno.cl.neurotecno.repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.neurotecno.cl.neurotecno.model.Atencion;
import com.neurotecno.cl.neurotecno.model.Medico;
import com.neurotecno.cl.neurotecno.model.Paciente;






@Repository
public interface AtencionRepository extends JpaRepository<Atencion, Long> {

    List<Atencion> findByPacienteId(Long pacienteId);

    List<Atencion> findByMedicoId(Long medicoId);


    List<Atencion> findByPacienteIdAndMedicoId(Long medicoId,Long pacienteId);


    //@Query(value="SELECT * FROM ATENCION WHERE a.fecha_atencion = ?1 AND a.hora_atencion = ?2;",nativeQuery = true)
    List<Atencion> findByFechaAtencionAndHoraAtencion(LocalDate fechaAtencion, LocalTime horaAtencion);

    @Query(value="SELECT * FROM ATENCION a WHERE a.fecha_atencion = ?1 AND a.medico_id = ?2;",nativeQuery = true)
    List<Atencion> findByFechayMedicoId(LocalDate fechaAtencion,Long medicoId);


    void deleteByMedico (Medico medico);
    void deleteByPaciente (Paciente paciente);
}

