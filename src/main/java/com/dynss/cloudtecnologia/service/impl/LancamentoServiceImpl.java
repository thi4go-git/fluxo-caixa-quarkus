package com.dynss.cloudtecnologia.service.impl;

import com.dynss.cloudtecnologia.exception.UsuarioNaoEncontradoException;
import com.dynss.cloudtecnologia.model.entity.Lancamento;
import com.dynss.cloudtecnologia.model.entity.Usuario;
import com.dynss.cloudtecnologia.model.enums.TipoLancamento;
import com.dynss.cloudtecnologia.model.repository.LancamentoRepository;
import com.dynss.cloudtecnologia.rest.dto.LancamentoDTO;
import com.dynss.cloudtecnologia.service.LancamentoService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;


@ApplicationScoped
public class LancamentoServiceImpl implements LancamentoService {

    @Inject
    private LancamentoRepository lancamentoRepository;

    @Inject
    private UsuarioServiceImpl usuarioService;


    @Override
    @Transactional
    public Response lancar(LancamentoDTO dto) {
        Usuario user = usuarioService.findById(dto.getId_usuario());
        if (user != null) {
            if (dto.getTipo() == TipoLancamento.DEBITO) {
                dto.setValor_total(dto.getValor_total().negate());
            }
            BigDecimal vlrParcelas = dto.getValor_total().divide(
                    new BigDecimal(dto.getQtde_parcelas()), MathContext.DECIMAL128).setScale(2, RoundingMode.HALF_EVEN);
            for (int index = 1; index <= dto.getQtde_parcelas(); index++) {
                int parcela = index;
                LocalDate data_lancamento;
                if (parcela == 1) {
                    data_lancamento = dto.getData_referencia();
                    Lancamento lancamento = new Lancamento(dto, parcela, user, vlrParcelas, data_lancamento);
                    lancamentoRepository.persist(lancamento);
                } else {
                    data_lancamento = dto.getData_referencia().plusMonths(parcela - 1);
                    Lancamento lancamento = new Lancamento(dto, parcela, user, vlrParcelas, data_lancamento);
                    lancamentoRepository.persist(lancamento);
                }
            }
            return Response.ok(dto).build();
        }
        return Response.noContent().build();
    }

    @Override
    @Transactional
    public List<Lancamento> listarLancamentos() {
        return lancamentoRepository.findAll().list();
    }

    @Override
    @Transactional
    public List<Lancamento> listarLancamentosByUsuario(Long idUser) {
        Usuario user = usuarioService.findById(idUser);
        if (user != null) {
            PanacheQuery<Lancamento> response = lancamentoRepository.findByUsuario(user);
            return response.list();
        }
        return null;
    }

    @Override
    public List<Lancamento> listarLancamentosByUsuarioDate(Long idUser, String data_inicio, String data_fim) {
        Usuario user = usuarioService.findById(idUser);
        if (user != null) {
            PanacheQuery<Lancamento> response = lancamentoRepository
                    .find(" id_usuario = ?1 AND data_lancamento between '" + data_inicio + "' and '" + data_fim + "' ",
                            idUser);
            return response.list();
        }
        return null;
    }
}
