package com.neurotecno.cl.neurotecno.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.neurotecno.cl.neurotecno.model.Medico;
import com.neurotecno.cl.neurotecno.repository.MedicoRepository;
import com.neurotecno.cl.neurotecno.service.MedicoService;

@SpringBootTest
public class MedicoServiceTest {

    @Autowired
    private MedicoService medicoService;

    @MockBean
    private MedicoRepository medicoRepository;

    private Medico createMedico(){
        return new Medico(1, 
        "21456789-9", 
        "Jose Retamal", 
        "Psicologia infantil", 
        "Pedro Fuentes");
    }

    @Test
    public void testFindAll(){
        when(medicoRepository.findAll()).thenReturn(List.of(createMedico()));
        List<Medico> medicos = medicoService.obtenerMedicos();
        assertNotNull(medicos);
        assertEquals(1, medicos.size());
    }

    @Test
    public void testFindById(){
        when(medicoRepository.findById(1L)).thenReturn(java.util.Optional.of(createMedico()));
        Medico medico = medicoService.obtenerMedicoPorId(1L);
        assertNotNull(medico);
        assertEquals("Jose Retamal", medico.getNombreCompleto());
    }

    @Test
    public void testSave(){
        Medico medico = createMedico();
        when(medicoRepository.save(medico)).thenReturn(medico);
        Medico savedMedico = medicoService.guardarMedico(medico);
        assertNotNull(savedMedico);
        assertEquals("Jose Retamal", savedMedico.getNombreCompleto());
    }

    @Test
    public void testPatchMedico(){
        Medico existeMedico = createMedico();
        Medico patchData = new Medico();
        patchData.setNombreCompleto("Jose Ignacio");

        when(medicoRepository.findById(1L)).thenReturn(java.util.Optional.of(existeMedico));
        when(medicoRepository.save(any(Medico.class))).thenReturn(existeMedico);

        Medico patchedMedico = medicoService.actualizarMedico(1L, patchData);
        assertNotNull(patchedMedico);
        assertEquals("Jose Ignacio", patchedMedico.getNombreCompleto());
    }

    @Test
    public void deleteById() {
        doNothing().when(medicoRepository).deleteById(1L);
        medicoService.eliminarMedico(1L);
        verify(medicoRepository, times(1)).deleteById(1L);
    }
}