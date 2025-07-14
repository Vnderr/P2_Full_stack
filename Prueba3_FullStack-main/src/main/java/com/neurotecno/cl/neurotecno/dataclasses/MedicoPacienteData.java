package com.neurotecno.cl.neurotecno.dataclasses;

import com.neurotecno.cl.neurotecno.model.Medico;
import com.neurotecno.cl.neurotecno.model.Paciente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicoPacienteData {
    Medico medico;
    Paciente paciente;

}
