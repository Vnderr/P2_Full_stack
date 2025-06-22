package com.neurotecno.cl.neurotecno.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.neurotecno.cl.neurotecno.controller.PacienteControllerV2;
import com.neurotecno.cl.neurotecno.model.Paciente;

@Component
public class PacienteModelAssembler implements RepresentationModelAssembler<Paciente, EntityModel<Paciente>> {
@SuppressWarnings("null")
    @Override
    public EntityModel<Paciente> toModel(Paciente paciente) {
    return EntityModel.of(paciente,
            linkTo(methodOn(PacienteControllerV2.class).buscar(Long.valueOf(paciente.getId()))).withSelfRel(),
            linkTo(methodOn(PacienteControllerV2.class).listar()).withRel("Pacientes"),
            linkTo(methodOn(PacienteControllerV2.class).actualizar(Long.valueOf(paciente.getId()))).withRel("Actualizar"),
            linkTo(methodOn(PacienteControllerV2.class).eliminar(Long.valueOf(paciente.getId()))).withRel("Eliminar"),
            linkTo(methodOn(PacienteControllerV2.class).patchPaciente(Long.valueOf(paciente.getId()))).withRel("actualizar-parcial")
    );
        
    }
}
