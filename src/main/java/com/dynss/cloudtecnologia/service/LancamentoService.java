package com.dynss.cloudtecnologia.service;

import com.dynss.cloudtecnologia.model.entity.Lancamento;
import com.dynss.cloudtecnologia.model.enums.Natureza;
import com.dynss.cloudtecnologia.model.enums.Situacao;
import com.dynss.cloudtecnologia.model.enums.TipoLancamento;
import com.dynss.cloudtecnologia.rest.dto.DashboardDTO;
import com.dynss.cloudtecnologia.rest.dto.LancamentoDTO;



import javax.ws.rs.core.Response;
import java.util.List;

public interface LancamentoService {
    Response lancar(LancamentoDTO dto);


    List<Lancamento> listarLancamentosByUsuarioDate(String username,
                                                    String data_inicio, String data_fim);

    List<Natureza> listarNaturezas();

    List<Situacao> listarSituacao();

    List<TipoLancamento> listarTipoLancamento();

    DashboardDTO getLancamentosDashboard(String username);

}
