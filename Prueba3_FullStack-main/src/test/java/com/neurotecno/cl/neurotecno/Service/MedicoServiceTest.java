package com.neurotecno.cl.neurotecno.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.neurotecno.cl.neurotecno.model.Atencion;
import com.neurotecno.cl.neurotecno.model.Paciente;
import com.neurotecno.cl.neurotecno.model.TipoUsuario;
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
        "123456Abcdef",
        "Psicologia infantil", 
        "Pedro Fuentes");
    }
    private Atencion createAtencion(Medico med) {
        return new Atencion(
                1,
                LocalDate.of(2025,7,1),
                LocalTime.of(14, 30, 0),
                0,
                new Paciente(),
                med,
                new TipoUsuario(),
                "nose");
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

    @Test
    public void testFindByEspecialidad() {
        Medico med = createMedico();
        List<Medico> funciona = medicoService.findByEspecialidad(med.getEspecialidad());
        assertNotNull(funciona);
        assertEquals(1,funciona.size());
        assertEquals(funciona.get(0), med);
    }

    @Test
    public void testFindByJefeTurnoAndEspecialidad() {
        Medico med = createMedico();
        List<Medico> funciona = medicoService.findByJefeTurnoAndEspecialidad(med.getEspecialidad(),med.getJefeTurno());
        assertNotNull(funciona);
        assertEquals(1,funciona.size());
        assertEquals(funciona.get(0), med);
    }
    @Test
    public void testFindByAtencionId() {
        Medico med = createMedico();
        Atencion ate = createAtencion(med);
        List<Medico> funciona = medicoService.obtenerPorIdAtencion(ate.getId().longValue());
        assertNotNull(funciona);
        assertEquals(1,funciona.size());
        assertEquals(funciona.get(0), med);
    }



}