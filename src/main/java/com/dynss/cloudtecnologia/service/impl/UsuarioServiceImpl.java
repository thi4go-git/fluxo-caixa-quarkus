package com.dynss.cloudtecnologia.service.impl;

import com.dynss.cloudtecnologia.exception.UsuarioNaoEncontradoException;
import com.dynss.cloudtecnologia.model.entity.Usuario;
import com.dynss.cloudtecnologia.model.repository.UsuarioRepository;
import com.dynss.cloudtecnologia.rest.dto.UsuarioDTO;
import com.dynss.cloudtecnologia.service.UsuarioService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    private UsuarioRepository repository;

    @Override
    public Usuario save(UsuarioDTO dto) {
        Usuario user = new Usuario();
        user.setUsername(dto.getUsername().trim());
        repository.persist(user);
        return user;
    }

    @Override
    public Usuario findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Usuario> findAll() {
        return repository.findAll().list();
    }

    @Override
    public PanacheQuery<Usuario> findByUsername(String username) throws UsuarioNaoEncontradoException {
        return repository.findByUsername(username);
    }
}
