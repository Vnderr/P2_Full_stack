package com.neurotecno.cl.neurotecno.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.neurotecno.cl.neurotecno.controller.TipoUsuarioControllerV2;
import com.neurotecno.cl.neurotecno.model.TipoUsuario;

@Component
public class TipoUsuarioModelAssemblers implements RepresentationModelAssembler<TipoUsuario, EntityModel<TipoUsuario>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<TipoUsuario> toModel(TipoUsuario tipoUsuario){
        /* no se me ocurre a que hacer link
        return EntityModel.of(tipoUsuario,
                linkTo(methodOn(TipoUsuarioControllerV2.class).));
        */

        return EntityModel.of(null);

    }
}
