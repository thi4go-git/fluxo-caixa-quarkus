package com.dynss.cloudtecnologia.model.repository;

import com.dynss.cloudtecnologia.model.entity.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {
    
}
