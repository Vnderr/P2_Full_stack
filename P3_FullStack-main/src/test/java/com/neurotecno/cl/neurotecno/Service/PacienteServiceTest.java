package com.neurotecno.cl.neurotecno.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import com.neurotecno.cl.neurotecno.model.Paciente;
import com.neurotecno.cl.neurotecno.model.TipoUsuario;
import com.neurotecno.cl.neurotecno.repository.PacienteRepository;
import com.neurotecno.cl.neurotecno.service.PacienteService;

@SpringBootTest
public class PacienteServiceTest {

    @Autowired
    private PacienteService pacienteService;

    @MockBean
    private PacienteRepository pacienteRepository;

    private Paciente createPaciente() {
        return new Paciente(
            1,
        "12345678-1",
        "Pepe",
        "lopez",
        new Date(),
        "p.lopez@gmail.com",
        new TipoUsuario());
    }

    @Test
    public void testFindAll() {
        when(pacienteRepository.findAll()).thenReturn(List.of(createPaciente()));
        List<Paciente> pacientes = pacienteService.obtenerPacientes();
        assertNotNull(pacientes);
        assertEquals(1, pacientes.size());
    }

    @Test
    public void testFindById() {
        when(pacienteRepository.findById(1L)).thenReturn(java.util.Optional.of(createPaciente()));
        Paciente paciente = pacienteService.obtenerPacientePorId(1L);
        assertNotNull(paciente);
        assertEquals(1, paciente.getId());
    }

   @Test
    public void testSave(){
        Paciente paciente = createPaciente();
        when(pacienteRepository.save(paciente)).thenReturn(paciente);
        Paciente savedPaciente = pacienteService.guardarPaciente(paciente);
        assertNotNull(savedPaciente);
        assertEquals("Pepe", savedPaciente.getNombres());
    }

      @Test
    public void testPatchPaciente(){
        Paciente existePaciente = createPaciente();
        Paciente patchData = new Paciente();
        patchData.setNombres("Jose lopez");

        when(pacienteRepository.findById(1L)).thenReturn(java.util.Optional.of(existePaciente));
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(existePaciente);

        Paciente patchedPaciente = pacienteService.actualizarPaciente(1L, patchData);
        assertNotNull(patchedPaciente);
        assertEquals("Jose lopez", patchedPaciente.getNombres());
    }

    @Test
    public void deleteById() {
        doNothing().when(pacienteRepository).deleteById(1L);
        pacienteService.eliminarPaciente(1L);
        verify(pacienteRepository, times(1)).deleteById(1L);
    }
}
