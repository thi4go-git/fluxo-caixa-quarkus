package com.dynss.cloudtecnologia.service.impl;

import com.dynss.cloudtecnologia.exception.JaExisteNaturezaCadastradaParaUsername;
import com.dynss.cloudtecnologia.exception.UsuarioNaoEncontradoException;
import com.dynss.cloudtecnologia.model.entity.Natureza;
import com.dynss.cloudtecnologia.model.entity.Usuario;
import com.dynss.cloudtecnologia.model.repository.NaturezaRepository;
import com.dynss.cloudtecnologia.rest.dto.NaturezaDTO;
import com.dynss.cloudtecnologia.service.NaturezaService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class NaturezaServiceImpl implements NaturezaService {

    @Inject
    private NaturezaRepository repository;

    @Inject
    private UsuarioServiceImpl usuarioService;


    @Override
    @Transactional
    public Natureza save(NaturezaDTO dto) {
        Usuario usuario = usuarioService.findByUsername(dto.getUsername());
        if (usuario.getId() != null) {
            if (repository.findByUsuarioAndDescricao(usuario, dto.getDescricao()).getId() == null) {
                Natureza nova = new Natureza(dto, usuario);
                repository.persist(nova);
                return nova;
            }
            throw new JaExisteNaturezaCadastradaParaUsername();
        }
        throw new UsuarioNaoEncontradoException();
    }

    @Override
    public Natureza findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Natureza getNaturezaByUsuario(Usuario usuario) {
        return repository.getNaturezaByUsuario(usuario);
    }

    @Override
    public Natureza getNaturezaByUsuarioAndID(Usuario usuario, Long id) {
        return repository.findByUsuarioAndID(usuario, id);
    }

    @Override
    public Natureza getNaturezaByUsuarioAndIDOrThrow(Usuario usuario, Long id) {
        return repository.findByUsuarioAndIDOrThrow(usuario, id);
    }

    @Override
    public List<Natureza> getNaturezasByUsername(String username) {
        Usuario usuario = usuarioService.findByUsername(username);
        if (usuario.getId() != null) {
            return repository.getNaturezasByUsuario(usuario);
        }
        throw new UsuarioNaoEncontradoException();
    }

}
