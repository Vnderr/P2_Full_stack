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

import com.neurotecno.cl.neurotecno.model.TipoUsuario;
import com.neurotecno.cl.neurotecno.repository.TipoUsuarioRepository;
import com.neurotecno.cl.neurotecno.service.TipoUsuarioService;

@SpringBootTest
public class TipoUsuarioServiceTest {

    @Autowired
    private TipoUsuarioService tipoUsuarioService;

    @MockBean
    private TipoUsuarioRepository tipoUsuarioRepository;

    private TipoUsuario createTipoUsuario() {
        return new TipoUsuario(1,"admin");
    }

@Test
public void testFindAll(){
    when(tipoUsuarioRepository.findAll()).thenReturn(List.of(createTipoUsuario()));
    List<TipoUsuario> tipoUsuarios = tipoUsuarioService.obtenerTipoUsuarios();
    assertNotNull(tipoUsuarios);
    assertEquals(1, tipoUsuarios.size());
}

@Test
public void testFindById(){
    when(tipoUsuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(createTipoUsuario()));
    TipoUsuario tipoUsuario = tipoUsuarioService.obtenerTipoUsuarioPorId(1L);
    assertNotNull(tipoUsuario);
    assertEquals("Admin", tipoUsuario.getNombre());
}

@Test
public void testSave(){
    TipoUsuario tipoUsuario = createTipoUsuario();
    when(tipoUsuarioRepository.save(tipoUsuario)).thenReturn(tipoUsuario);
    TipoUsuario savedTipoUsuario = tipoUsuarioService.guardarTipoUsuario(tipoUsuario);
    assertNotNull(savedTipoUsuario);
    assertEquals("Admin", savedTipoUsuario.getNombre());
}

@Test
public void testPatchTipoUsuario(){
    TipoUsuario existeTipoUsuario = createTipoUsuario();
    TipoUsuario patchData = new TipoUsuario();
    patchData.setNombre("Tester");

    when(tipoUsuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(existeTipoUsuario));
    when(tipoUsuarioRepository.save(any(TipoUsuario.class))).thenReturn(existeTipoUsuario);

    TipoUsuario patchedTipoUsuario = tipoUsuarioService.actualizarTipoUsuario(1L, patchData);
    assertNotNull(patchedTipoUsuario);
    assertEquals("Tester", patchedTipoUsuario.getNombre());
}

@Test
public void deleteById() {
    doNothing().when(tipoUsuarioRepository).deleteById(1L);
    tipoUsuarioService.eliminarTipoUsuario(1L);
    verify(tipoUsuarioRepository, times(1)).deleteById(1L);
}

}
