package com.dynss.cloudtecnologia;

import com.dynss.cloudtecnologia.rest.dto.LancamentoDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(LancamentoDTO dto) {
        return "Olá";
    }
}