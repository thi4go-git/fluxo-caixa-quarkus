package com.dynss.cloudtecnologia.service.impl;

import com.dynss.cloudtecnologia.model.entity.Lancamento;
import com.dynss.cloudtecnologia.model.entity.Usuario;
import com.dynss.cloudtecnologia.model.enums.Natureza;
import com.dynss.cloudtecnologia.model.enums.Situacao;
import com.dynss.cloudtecnologia.model.enums.TipoLancamento;
import com.dynss.cloudtecnologia.model.repository.LancamentoRepository;
import com.dynss.cloudtecnologia.rest.dto.DashboardDTO;
import com.dynss.cloudtecnologia.rest.dto.LancamentoDTO;
import com.dynss.cloudtecnologia.rest.dto.LancamentoReflectionDTO;
import com.dynss.cloudtecnologia.service.LancamentoService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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
    public Response lancar(LancamentoDTO dto) {

        Usuario user = usuarioService.findByUsername(dto.getUsername()).list().get(0);
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
    public List<Lancamento> listarLancamentosByUsuarioDate(String username, String data_inicio, String data_fim) {
        if (username != null) {
            Usuario user = usuarioService.findByUsername(username).list().get(0);

            if (user != null) {
                PanacheQuery<Lancamento> response = lancamentoRepository
                        .find(" id_usuario = ?1 AND data_lancamento between '" + data_inicio + "' and '" + data_fim + "' " +
                                        " order by data_lancamento,id asc ",
                                user.getId());
                return response.list();
            }
        }
        return null;
    }

    @Override
    public List<Natureza> listarNaturezas() {
        return List.of(Natureza.values());
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
            Usuario user = usuarioService.findByUsername(username).list().get(0);
            if (user != null) {
                List<LancamentoReflectionDTO> lancamentos = lancamentoRepository.getLancamentosDashboard(user);
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
}
