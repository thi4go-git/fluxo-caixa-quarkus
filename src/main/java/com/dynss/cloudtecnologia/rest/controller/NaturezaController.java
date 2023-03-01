package com.dynss.cloudtecnologia.rest.controller;


import com.dynss.cloudtecnologia.exception.JaExisteNaturezaCadastradaParaUsername;
import com.dynss.cloudtecnologia.exception.UsuarioNaoEncontradoException;
import com.dynss.cloudtecnologia.model.entity.Natureza;
import com.dynss.cloudtecnologia.rest.dto.NaturezaDTO;
import com.dynss.cloudtecnologia.service.impl.NaturezaServiceImpl;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/naturezas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NaturezaController {

    @Inject
    private NaturezaServiceImpl naturezaService;


    @POST
    public Response save(@Valid NaturezaDTO dto)
            throws JaExisteNaturezaCadastradaParaUsername, UsuarioNaoEncontradoException {
        Natureza natureza = naturezaService.save(dto);
        if (natureza == null) {
            return Response.noContent().build();
        }
        return Response.ok(natureza).build();
    }

    @GET
    public Response getNaturezasByUsername(@QueryParam("username") String username) {
        List<Natureza> response = naturezaService.getNaturezasByUsername(username);
        if (response.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(response).build();
    }


}
