package com.neurotecno.cl.neurotecno.Service;

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

}
