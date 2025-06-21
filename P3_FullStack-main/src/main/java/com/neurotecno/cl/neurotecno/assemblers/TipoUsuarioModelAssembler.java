package com.neurotecno.cl.neurotecno.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.neurotecno.cl.neurotecno.controller.TipoUsuarioControllerV2;
import com.neurotecno.cl.neurotecno.model.TipoUsuario;

@Component
public class TipoUsuarioModelAssembler implements RepresentationModelAssembler<TipoUsuario, EntityModel<TipoUsuario>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<TipoUsuario> toModel(TipoUsuario tipoUsuario){
        return EntityModel.of(tipoUsuario,
            linkTo(methodOn(TipoUsuarioControllerV2.class).buscar(Long.valueOf(tipoUsuario.getId()))).withSelfRel(),
            linkTo(methodOn(TipoUsuarioControllerV2.class).listar()).withRel("Tipos de usuarios"),
            linkTo(methodOn(TipoUsuarioControllerV2.class).actualizar(Long.valueOf(tipoUsuario.getId()))).withRel("Actualizar"),
            linkTo(methodOn(TipoUsuarioControllerV2.class).eliminar(Long.valueOf(tipoUsuario.getId()))).withRel("Eliminar"),
            linkTo(methodOn(TipoUsuarioControllerV2.class).patchtipoUsuario(Long.valueOf(tipoUsuario.getId()))).withRel("actualizar-parcial"));
    }

}
