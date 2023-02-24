package com.dynss.cloudtecnologia.anottation.impl;


import com.dynss.cloudtecnologia.anottation.UsuarioNaoLocalizado;
import com.dynss.cloudtecnologia.service.impl.UsuarioServiceImpl;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UsuarioNaoLocalizadoImpl implements ConstraintValidator<UsuarioNaoLocalizado, String> {

    @Inject
    private UsuarioServiceImpl usuarioService;


    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !usuarioService.findByUsername(username).list().isEmpty();
    }
}
