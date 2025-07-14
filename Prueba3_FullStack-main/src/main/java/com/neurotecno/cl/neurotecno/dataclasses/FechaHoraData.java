package com.neurotecno.cl.neurotecno.dataclasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FechaHoraData {
    LocalDate fechaAtencion;
    LocalTime horaAtencion;
}
