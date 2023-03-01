package com.dynss.cloudtecnologia;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/hello")
public class GreetingResource {


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "EXECUTANDO...";
    }
}