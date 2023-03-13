package com.dynss.cloudtecnologia.model.repository;

import com.dynss.cloudtecnologia.model.enums.Situacao;
import com.dynss.cloudtecnologia.model.enums.TipoLancamento;

import javax.ws.rs.QueryParam;
import java.math.BigDecimal;
import java.time.LocalDate;


public class LancamentoFilter {
    @QueryParam("tipo")
    public TipoLancamento tipo;

    @QueryParam("descricao")
    public String descricao;

    @QueryParam("data_lancamento")
    public LocalDate data_lancamento;

    @QueryParam("valor_parcela")
    public BigDecimal valor_parcela;

    @QueryParam("qtde_parcelas")
    public Integer qtde_parcelas;

    @QueryParam("nr_parcela")
    public Integer nr_parcela;

    @QueryParam("id_natureza")
    public Long id_natureza;

    @QueryParam("id_usuario")
    public Long id_usuario;

    @QueryParam("situacao")
    public Situacao situacao;
}
