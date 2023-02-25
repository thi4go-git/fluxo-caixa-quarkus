package com.dynss.cloudtecnologia.model.repository;


import com.dynss.cloudtecnologia.model.entity.Lancamento;
import com.dynss.cloudtecnologia.model.entity.Usuario;
import com.dynss.cloudtecnologia.rest.dto.LancamentoReflectionDTO;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
@Transactional
public class LancamentoRepository implements PanacheRepository<Lancamento> {

    public PanacheQuery<Lancamento> findByUsuario(Usuario usuario) {
        return find("usuario =:usuario order by data_lancamento asc", Parameters.with("usuario", usuario));
    }

    public List<LancamentoReflectionDTO> getLancamentosDashboard(Usuario usuario) {
        List<LancamentoReflectionDTO> listas = new ArrayList<>();
        //
        Integer ano = LocalDate.now().getYear();
        String query = "SELECT " +
                "to_char(data_lancamento,'MM/YYYY') AS mes," +
                "to_char(data_lancamento,'MM') AS mes_num," +
                "SUM(CASE WHEN tipo = 0 THEN valor_parcela ELSE 0 END) AS saldo_entradas, " +
                "SUM(CASE WHEN tipo = 1 THEN valor_parcela ELSE 0 END) AS saldo_saidas " +
                "from Lancamento " +
                "WHERE to_char(data_lancamento,'YYYY') = '" + ano + "'" +
                " AND usuario =:usuario  " +
                "GROUP BY mes,mes_num order by mes_num";
        //
        PanacheQuery<LancamentoReflectionDTO> panache = find
                (query, Parameters.with("usuario", usuario)).project(LancamentoReflectionDTO.class);
        //
        List<LancamentoReflectionDTO> lista = panache.list();
        return lista;
    }


}
