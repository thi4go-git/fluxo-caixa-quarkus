package com.dynss.cloudtecnologia.rest.controller;


import com.dynss.cloudtecnologia.model.entity.Lancamento;
import com.dynss.cloudtecnologia.rest.dto.*;
import com.dynss.cloudtecnologia.service.impl.LancamentoServiceImpl;


import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Path("/lancamentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LancamentoController {


    @Inject
    private LancamentoServiceImpl service;

    @POST
    public Response save(@Valid LancamentoDTO dto) {
        return service.lancar(dto);
    }


    @GET
    public Response finByIdUserData(
            @QueryParam("username") String username,
            @QueryParam("inicio") String inicio,
            @QueryParam("fim") String fim) {
        //
        if (inicio == null || fim == null) {
            LocalDate dataAtual = LocalDate.now();
            inicio = dataAtual.withDayOfMonth(1)
                    .format(DateTimeFormatter.ISO_DATE);
            fim = dataAtual.withDayOfMonth(dataAtual.lengthOfMonth())
                    .format(DateTimeFormatter.ISO_DATE);
        }
        List<LancamentoDTOResponse> response = new ArrayList<>();
        for (Lancamento lancamento : service.listarLancamentosByUsuarioDate(username, inicio, fim)) {
            response.add(new LancamentoDTOResponse(lancamento));
        }
        if (response.isEmpty()) {
            Response.noContent().build();
        }
        return Response.ok(new LancamentoDataDTO(response, inicio, fim)).build();
    }

    @GET
    @Path("/natureza")
    public Response findAllNaturezas() {
        return Response.ok(service.listarNaturezas()).build();
    }

    @GET
    @Path("/situacao")
    public Response findAllSituacao() {
        return Response.ok(service.listarSituacao()).build();
    }


    @GET
    @Path("/tipo")
    public Response findAllTipo() {
        return Response.ok(service.listarTipoLancamento()).build();
    }


    @GET
    @Path("/dashboard")
    public Response getLancamentosDashboard(@QueryParam("username") String username) {
        DashboardDTO response =
                service.getLancamentosDashboard(username);
        if (response == null) {
            return Response.noContent().build();
        }
        return Response.ok(response).build();
    }
}
