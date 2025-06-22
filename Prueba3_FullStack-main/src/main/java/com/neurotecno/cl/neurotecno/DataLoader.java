package com.neurotecno.cl.neurotecno;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.neurotecno.cl.neurotecno.model.Atencion;
import com.neurotecno.cl.neurotecno.model.Medico;
import com.neurotecno.cl.neurotecno.model.Paciente;
import com.neurotecno.cl.neurotecno.model.TipoUsuario;
import com.neurotecno.cl.neurotecno.repository.AtencionRepository;
import com.neurotecno.cl.neurotecno.repository.MedicoRepository;
import com.neurotecno.cl.neurotecno.repository.PacienteRepository;
import com.neurotecno.cl.neurotecno.repository.TipoUsuarioRepository;

import net.datafaker.Faker;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AtencionRepository atencionRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;
        
    private final Faker faker = new Faker();
    private final Random random = new Random();

    @Override
    public void run(String... args) throws Exception {
     
        for (int i = 0; i < 3; i++) {
            TipoUsuario tipo = new TipoUsuario();
            tipo.setNombre(faker.job().position());
            tipoUsuarioRepository.save(tipo);
            }

        List<TipoUsuario> tipos = tipoUsuarioRepository.findAll();
            for (int i = 0; i < 10; i++) {
                Paciente paciente = new Paciente();
                paciente.setRun(faker.idNumber().valid());
                paciente.setNombres(faker.name().firstName());
                paciente.setApellidos(faker.name().lastName());
                paciente.setCorreo(faker.internet().emailAddress());
                paciente.setContraseña(faker.internet().password());
                paciente.setFechaNacimiento(faker.date().birthday(18,70));
                paciente.setTipoUsuario(tipos.get(random.nextInt(tipos.size())));
                pacienteRepository.save(paciente);
            }
 
            for (int i = 0; i < 5; i++) {
                Medico medico = new Medico();
                medico.setRun(faker.idNumber().valid());
                medico.setNombreCompleto(faker.name().fullName());
                medico.setContraseña(faker.internet().password());
                medico.setEspecialidad("Psicología"); ;
                medico.setJefeTurno(faker.name().fullName());
                medicoRepository.save(medico);
            }

        List<Paciente> pacientes = pacienteRepository.findAll();
        List<Medico> medicos = medicoRepository.findAll();


            for (int i = 0; i < 20; i++) {
                Atencion atencion = new Atencion();
                atencion.setFechaAtencion(LocalDate.now().plusDays(random.nextInt(30)));
                atencion.setHoraAtencion(LocalTime.of(random.nextInt(8) + 9, 0)); // Entre 9:00 y 17:00
                atencion.setCosto(faker.number().numberBetween(10000, 50000));
                atencion.setComentario(faker.lorem().sentence());
                atencion.setPaciente(pacientes.get(random.nextInt(pacientes.size())));
                atencion.setMedico(medicos.get(random.nextInt(medicos.size())));
                atencion.setTipousuario(tipos.get(random.nextInt(tipos.size())));
                atencionRepository.save(atencion);
        }
    }
}

