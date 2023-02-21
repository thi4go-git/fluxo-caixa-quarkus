package com.dynss.cloudtecnologia.rest.controller;


import com.dynss.cloudtecnologia.model.entity.Lancamento;
import com.dynss.cloudtecnologia.rest.dto.LancamentoDTO;
import com.dynss.cloudtecnologia.rest.dto.UsuarioDTO;
import com.dynss.cloudtecnologia.service.impl.UsuarioServiceImpl;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioController {

    @Inject
    private UsuarioServiceImpl userService;


    @POST
    public Response save(UsuarioDTO dto) {
        return Response.ok(userService.save(dto)).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(userService.findById(id)).build();
    }


    @GET
    public Response findAll() {
        return Response.ok(userService.findAll()).build();
    }


}
