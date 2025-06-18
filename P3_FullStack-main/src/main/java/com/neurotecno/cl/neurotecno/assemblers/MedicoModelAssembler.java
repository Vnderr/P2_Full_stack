package com.neurotecno.cl.neurotecno.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.neurotecno.cl.neurotecno.model.Medico;

@Component
public class MedicoModelAssembler implements RepresentationModelAssembler<Medico, EntityModel<Medico>>{
    
    @SuppressWarnings("null")
    public EntityModel<Medico> toModel(Medico m)
    {
        // no me acuerdo como se hacia, vere los ppt despues
        throw new UnsupportedOperationException();
    } 
}
