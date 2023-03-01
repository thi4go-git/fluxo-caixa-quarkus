package com.dynss.cloudtecnologia.rest.controller;


import com.dynss.cloudtecnologia.model.entity.Usuario;
import com.dynss.cloudtecnologia.rest.dto.UsuarioDTO;
import com.dynss.cloudtecnologia.service.impl.UsuarioServiceImpl;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;


@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioController {

    @Inject
    private UsuarioServiceImpl userService;


    @POST
    public Response save(@Valid UsuarioDTO dto) {
        Usuario user = userService.save(dto);
        return Response.created(UriBuilder.fromResource(UsuarioController.class)
                        .path("" + user.getId().toString())
                        .build())
                .entity(user)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Usuario user = userService.findById(id);
        if (user.getId() == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }


    @GET
    public Response findAll() {
        List<Usuario> usuarios = userService.findAll();
        if (usuarios.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(usuarios).build();
    }


}
