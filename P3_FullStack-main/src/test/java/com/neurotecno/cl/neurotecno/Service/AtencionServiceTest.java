package com.neurotecno.cl.neurotecno.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.neurotecno.cl.neurotecno.model.Atencion;
import com.neurotecno.cl.neurotecno.model.Medico;
import com.neurotecno.cl.neurotecno.model.Paciente;
import com.neurotecno.cl.neurotecno.model.TipoUsuario;
import com.neurotecno.cl.neurotecno.repository.AtencionRepository;
import com.neurotecno.cl.neurotecno.service.AtencionService;

@SpringBootTest
public class AtencionServiceTest {

    @Autowired
    private AtencionService atencionService;

    @MockBean
    private AtencionRepository atencionRepository;

    private Atencion createAtencion() {
        return new Atencion(1,        
        LocalDate.of(2025, 6, 18), 
        LocalTime.of(14, 30, 0),   
        12000,                     
        new Paciente(),             
        new Medico(),               
        new TipoUsuario(),          
        "Atencion Particular" );
    }

    @Test
    public void testFindAll() {
        when(atencionRepository.findAll()).thenReturn(List.of(createAtencion()));
        List<Atencion> atenciones = atencionService.obtenerAtenciones();
        assertNotNull(atenciones);
        assertEquals(1, atenciones.size());
    }

    @Test
    public void testFindById() {
        when(atencionRepository.findById(1L)).thenReturn(java.util.Optional.of(createAtencion()));
        Atencion atencion = atencionService.obtenerAtencionPorId(1L);
        assertNotNull(atencion);
        assertEquals(1, atencion.getId());
    }

    @Test
    public void testSave() {
        Atencion atencion = createAtencion();
        when(atencionRepository.save(atencion)).thenReturn(atencion);
        Atencion savedAtencion = atencionService.guardarAtencion(atencion);
        assertNotNull(savedAtencion);
        assertEquals(1, savedAtencion.getId());
    }

    @Test
    public void testPatchAtencion() {
        Atencion existeAtencion = createAtencion();
        Atencion patchData = new Atencion();
        patchData.setCosto(15000);

        when(atencionRepository.findById(1L)).thenReturn(java.util.Optional.of(existeAtencion));
        when(atencionRepository.save(any(Atencion.class))).thenReturn(existeAtencion);

        Atencion updatedAtencion= atencionService.actualizarAtencion(1L, patchData);
        assertNotNull(updatedAtencion);
        assertEquals(15000, updatedAtencion.getCosto());
    }

}
