package com.neurotecno.cl.neurotecno.dataclasses;

import com.neurotecno.cl.neurotecno.model.Medico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicoFechaData {
    Medico medico;
    LocalDate fechaAtencion;

}
