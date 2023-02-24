package com.dynss.cloudtecnologia;

import com.dynss.cloudtecnologia.model.repository.LancamentoRepository;
import com.dynss.cloudtecnologia.rest.dto.LancamentoDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/hello")
public class GreetingResource {

    @Inject
    private LancamentoRepository re;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(LancamentoDTO dto) {
        return "Olá";
    }
}