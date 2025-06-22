package com.neurotecno.cl.neurotecno.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.neurotecno.cl.neurotecno.controller.AtencionControllerV2;
import com.neurotecno.cl.neurotecno.model.Atencion;
@Component
public class AtencionModelAssembler implements RepresentationModelAssembler<Atencion, EntityModel<Atencion>>{

    @SuppressWarnings("null")
    @Override
    public EntityModel<Atencion> toModel(Atencion atencion) {
        return EntityModel.of(atencion,
            linkTo(methodOn(AtencionControllerV2.class).buscarAtencionPorId(Long.valueOf(atencion.getId()))).withSelfRel(),
            linkTo(methodOn(AtencionControllerV2.class).listar()).withRel("Atenciones"),
            linkTo(methodOn(AtencionControllerV2.class).actualizarAtencion(Long.valueOf(atencion.getId()),atencion)).withRel("Actualizar"),
            linkTo(methodOn(AtencionControllerV2.class).eliminar(Long.valueOf(atencion.getId()))).withRel("Eliminar"),
            linkTo(methodOn(AtencionControllerV2.class).patchAtencion(Long.valueOf(atencion.getId()),atencion)).withRel("actualizar-parcial"));
        
    }

}
