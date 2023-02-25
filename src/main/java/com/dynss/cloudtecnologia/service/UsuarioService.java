package com.dynss.cloudtecnologia.service;

import com.dynss.cloudtecnologia.exception.UsuarioNaoEncontradoException;
import com.dynss.cloudtecnologia.model.entity.Usuario;
import com.dynss.cloudtecnologia.rest.dto.UsuarioDTO;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import java.util.List;

public interface UsuarioService {
    Usuario save(UsuarioDTO dto);
    Usuario findById(Long id);
    List<Usuario> findAll();
    PanacheQuery<Usuario> findByUsername(String username) throws UsuarioNaoEncontradoException;
}
