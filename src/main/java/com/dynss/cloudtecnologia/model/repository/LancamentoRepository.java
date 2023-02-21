package com.dynss.cloudtecnologia.model.repository;


import com.dynss.cloudtecnologia.model.entity.Lancamento;
import com.dynss.cloudtecnologia.model.entity.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class LancamentoRepository implements PanacheRepository<Lancamento> {

    public PanacheQuery<Lancamento> findByUsuario(Usuario usuario) {
        return find("usuario =:usuario", Parameters.with("usuario", usuario));
    }

}
