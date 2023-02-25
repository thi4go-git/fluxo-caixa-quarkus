package com.dynss.cloudtecnologia.rest.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DashboardDTO {
    List<LancamentoReflectionDTO> lancamentos;
    BigDecimal sumEntradas, sumSaidas;
    Integer ano;

    public DashboardDTO(
            List<LancamentoReflectionDTO> lancamentos,
            BigDecimal sumEntradas, BigDecimal sumSaidas, Integer ano) {
        this.lancamentos = lancamentos;
        this.sumEntradas = sumEntradas;
        this.sumSaidas = sumSaidas;
        this.ano = ano;
    }
}
