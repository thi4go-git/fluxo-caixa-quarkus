package com.dynss.cloudtecnologia.rest.dto;

import com.dynss.cloudtecnologia.model.enums.Natureza;
import com.dynss.cloudtecnologia.model.enums.TipoLancamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoDTO {
    private TipoLancamento tipo;
    private String descricao;
    private LocalDate data_referencia;
    private BigDecimal valor_total;
    private Integer qtde_parcelas;
    private Natureza natureza;
    private Long id_usuario;
}
