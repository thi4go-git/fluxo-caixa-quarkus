package com.dynss.cloudtecnologia.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Data
public class LancamentoDataDTO {

    private LocalDate data_inicio;
    private LocalDate data_fim;
    private Integer total_lancamentos;
    private List<LancamentoDTOResponse> lancamentos;


    public LancamentoDataDTO(List<LancamentoDTOResponse> listaDto,
                             String data_inicio, String data_fim) {
        this.data_inicio = LocalDate.parse(data_inicio);
        this.data_fim = LocalDate.parse(data_fim);
        this.total_lancamentos = listaDto.size();
        this.lancamentos = listaDto;
    }

}
