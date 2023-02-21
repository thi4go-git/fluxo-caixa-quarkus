package com.dynss.cloudtecnologia.rest.controller;


import com.dynss.cloudtecnologia.model.entity.Lancamento;
import com.dynss.cloudtecnologia.rest.dto.LancamentoDTO;
import com.dynss.cloudtecnologia.rest.dto.LancamentoDTOResponse;
import com.dynss.cloudtecnologia.rest.dto.LancamentoDataDTO;
import com.dynss.cloudtecnologia.service.impl.LancamentoServiceImpl;


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
    public Response save(LancamentoDTO dto) {
        service.lancar(dto);
        return Response.ok().build();
    }

    @GET
    public Response findAll() {
        List<Lancamento> lista = service.listarLancamentos();
        if (lista.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(lista).build();
    }

    @GET
    @Path("/usuario/{idUser}")
    public Response finByIdUser(@PathParam("idUser") Long idUser) {
        List<LancamentoDTOResponse> response = new ArrayList<>();
        for (Lancamento lancamento : service.listarLancamentosByUsuario(idUser)) {
            response.add(new LancamentoDTOResponse(lancamento));
        }
        return Response.ok(response).build();
    }

    @GET
    @Path("/usuario/{idUser}/data")
    public Response finByIdUserData(
            @PathParam("idUser") Long idUser,
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
        for (Lancamento lancamento : service.listarLancamentosByUsuarioDate(idUser, inicio, fim)) {
            response.add(new LancamentoDTOResponse(lancamento));
        }
        return Response.ok(new LancamentoDataDTO(response, inicio, fim)).build();
    }

}
