package com.neurotecno.cl.neurotecno.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.neurotecno.cl.neurotecno.repository.MedicoRepository;
import com.neurotecno.cl.neurotecno.service.MedicoService;

@SpringBootTest
public class MedicoServiceTest {

    @Autowired
    private MedicoService medicoService;

    @MockBean
    private MedicoRepository Medicorepository;

}
