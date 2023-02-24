package com.dynss.cloudtecnologia.service;

import com.dynss.cloudtecnologia.model.entity.Lancamento;
import com.dynss.cloudtecnologia.model.enums.Natureza;
import com.dynss.cloudtecnologia.model.enums.Situacao;
import com.dynss.cloudtecnologia.model.enums.TipoLancamento;
import com.dynss.cloudtecnologia.rest.dto.LancamentoDTO;
import com.dynss.cloudtecnologia.rest.dto.LancamentoReflectionDTO;


import javax.ws.rs.core.Response;
import java.util.List;

public interface LancamentoService {
    Response lancar(LancamentoDTO dto);

    List<Lancamento> listarLancamentos();

    List<Lancamento> listarLancamentosByUsuario(Long idUser);

    List<Lancamento> listarLancamentosByUsuarioDate(Long idUser,
                                                    String data_inicio, String data_fim);

    List<Natureza> listarNaturezas();

    List<Situacao> listarSituacao();

    List<TipoLancamento> listarTipoLancamento();

    List<LancamentoReflectionDTO> getLancamentosDashboard(Integer ano);

}
