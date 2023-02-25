package com.dynss.cloudtecnologia.model.repository;

import com.dynss.cloudtecnologia.model.entity.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;


@ApplicationScoped
@Transactional
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public PanacheQuery<Usuario> findByUsername(String username) {
        PanacheQuery<Usuario> panache = find("username =:username", Parameters.with("username", username));
        return panache;
    }


}
