package com.dynss.cloudtecnologia.service.impl;

import com.dynss.cloudtecnologia.exception.LancamentoNaoEncontradoException;
import com.dynss.cloudtecnologia.exception.NaturezaNaoEncontrada;
import com.dynss.cloudtecnologia.exception.UsuarioNaoEncontradoException;
import com.dynss.cloudtecnologia.model.entity.Lancamento;
import com.dynss.cloudtecnologia.model.entity.Natureza;
import com.dynss.cloudtecnologia.model.entity.Usuario;
import com.dynss.cloudtecnologia.model.enums.Situacao;
import com.dynss.cloudtecnologia.model.enums.TipoLancamento;
import com.dynss.cloudtecnologia.model.repository.LancamentoRepository;
import com.dynss.cloudtecnologia.rest.dto.DashboardDTO;
import com.dynss.cloudtecnologia.rest.dto.LancamentoDTO;
import com.dynss.cloudtecnologia.rest.dto.LancamentoReflectionDTO;
import com.dynss.cloudtecnologia.service.LancamentoService;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
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

    @Inject
    private NaturezaServiceImpl naturezaService;


    @Override
    public Response lancar(LancamentoDTO dto) {

        Usuario usuario = usuarioService.findByUsername(dto.getUsername());
        if (usuario != null) {
            Natureza natureza = naturezaService
                    .findById(dto.getId_natureza());
            if (natureza != null) {
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
                        Lancamento lancamento = new Lancamento(dto, parcela, usuario, vlrParcelas, data_lancamento, natureza);
                        lancamentoRepository.persist(lancamento);
                    } else {
                        data_lancamento = dto.getData_referencia().plusMonths(parcela - 1);
                        Lancamento lancamento = new Lancamento(dto, parcela, usuario, vlrParcelas, data_lancamento, natureza);
                        lancamentoRepository.persist(lancamento);
                    }
                }
                return Response.ok(dto).build();


            }
            throw new NaturezaNaoEncontrada();
        }
        throw new UsuarioNaoEncontradoException();
    }


    @Override
    public List<Lancamento> listarLancamentosByUsuarioDate(String username, String data_inicio, String data_fim) {
        if (username != null) {
            Usuario usuario = usuarioService.findByUsername(username);
            if (usuario != null) {
                return lancamentoRepository
                        .listarLancamentosByUsuarioDate(usuario, data_inicio, data_fim);
            }
        }
        return null;
    }


    @Override
    public List<Situacao> listarSituacao() {
        return List.of(Situacao.values());
    }

    @Override
    public List<TipoLancamento> listarTipoLancamento() {
        return List.of(TipoLancamento.values());
    }

    @Override
    public DashboardDTO getLancamentosDashboard(String username) {
        if (username != null) {
            Usuario usuario = usuarioService.findByUsername(username);
            if (usuario != null) {
                List<LancamentoReflectionDTO> lancamentos = lancamentoRepository
                        .getLancamentosDashboard(usuario);
                BigDecimal sumEntradas = new BigDecimal(0);
                BigDecimal sumSaidas = new BigDecimal(0);
                Integer ano = LocalDate.now().getYear();
                for (LancamentoReflectionDTO refle : lancamentos) {
                    sumEntradas = sumEntradas.add(refle.getSaldo_entradas());
                    sumSaidas = sumSaidas.add(refle.getSaldo_saidas());
                }
                return new DashboardDTO(lancamentos, sumEntradas, sumSaidas, ano);
            }
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        Lancamento lancamento = lancamentoRepository.findById(id);
        if (lancamento != null) {
            lancamentoRepository.deleteById(lancamento.getId());
        } else {
            throw new LancamentoNaoEncontradoException();
        }
    }
}
