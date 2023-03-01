package com.dynss.cloudtecnologia.service;

import com.dynss.cloudtecnologia.model.entity.Natureza;
import com.dynss.cloudtecnologia.model.entity.Usuario;
import com.dynss.cloudtecnologia.rest.dto.NaturezaDTO;

import java.util.List;


public interface NaturezaService {
    Natureza save(NaturezaDTO dto);

    Natureza findById(Long id);

    Natureza getNaturezaByUsuario(Usuario usuario);

    Natureza getNaturezaByUsuarioAndID(Usuario usuario, Long id);

    List<Natureza> getNaturezasByUsername(String username);
}
