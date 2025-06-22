package com.neurotecno.cl.neurotecno.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.neurotecno.cl.neurotecno.controller.MedicoControllerV2;
import com.neurotecno.cl.neurotecno.model.Medico;

@Component
public class MedicoModelAssembler implements RepresentationModelAssembler<Medico, EntityModel<Medico>>{
    
    @SuppressWarnings("null")
    @Override
    public EntityModel<Medico> toModel(Medico medico) {
    return EntityModel.of(medico,
            linkTo(methodOn(MedicoControllerV2.class).buscar(Long.valueOf(medico.getId()))).withSelfRel(),
            linkTo(methodOn(MedicoControllerV2.class).listar()).withRel("Medicos"),
            linkTo(methodOn(MedicoControllerV2.class).actualizar(Long.valueOf(medico.getId()))).withRel("Actualizar"),
            linkTo(methodOn(MedicoControllerV2.class).eliminar(Long.valueOf(medico.getId()))).withRel("Eliminar"),
            linkTo(methodOn(MedicoControllerV2.class).patchMedico(Long.valueOf(medico.getId()))).withRel("actualizar-parcial")
    );
        
    }
}

