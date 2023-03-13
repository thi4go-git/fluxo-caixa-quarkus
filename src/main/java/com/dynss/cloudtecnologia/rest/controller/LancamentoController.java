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
            return Response.ok(new LancamentoDataDTO(response, inicio, fim)).build();
        }
        return Response.ok(new LancamentoDataDTO(response, inicio, fim)).build();
    }


    @POST
    @Path("/filter")
    public Response finByIdUserDataFilter(
            @Valid LancamentoFilterDTO dtoFilter,
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
        dtoFilter.setData_inicio(inicio);
        dtoFilter.setData_fim(fim);
        List<LancamentoDTOResponse> response = new ArrayList<>();
        for (Lancamento lancamento : service.listarLancamentosByUsuarioDateFilter(dtoFilter)) {
            response.add(new LancamentoDTOResponse(lancamento));
        }
        return Response.ok(new LancamentoDataDTO(response, inicio, fim)).build();
    }


    @GET
    @Path("/situacao")
    public Response findAllSituacao() {
        return Response.ok(
                service.listarSituacao()).build();
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
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteById(@PathParam("id") Long id) {
        service.deleteById(id);
        return Response.noContent().build();
    }


    @PUT
    public Response update(@Valid LancamentoDTO dto) {
        LancamentoDTO dtoUp = service.update(dto);
        System.out.println(dtoUp.toString());
        return Response.ok(dtoUp).build();
    }


}
