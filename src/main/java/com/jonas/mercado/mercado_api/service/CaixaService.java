package com.jonas.mercado.mercado_api.service;

import com.jonas.mercado.mercado_api.dto.AbrirCaixaRequest;
import com.jonas.mercado.mercado_api.dto.CaixaResponse;
import com.jonas.mercado.mercado_api.dto.FecharCaixaRequest;
import com.jonas.mercado.mercado_api.entity.CaixaOperacao;
import com.jonas.mercado.mercado_api.entity.CaixaOperacao.StatusCaixa;
import com.jonas.mercado.mercado_api.entity.FormaPagamento;
import com.jonas.mercado.mercado_api.entity.Usuario;
import com.jonas.mercado.mercado_api.repository.CaixaOperacaoRepository;
import com.jonas.mercado.mercado_api.repository.UsuarioRepository;
import com.jonas.mercado.mercado_api.repository.VendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CaixaService {
    private final CaixaOperacaoRepository caixaRepo;
    private final UsuarioRepository       usuarioRepo;
    private final VendaRepository         vendaRepo;

    public CaixaService(CaixaOperacaoRepository caixaRepo, UsuarioRepository usuarioRepo, VendaRepository vendaRepo) {
        this.caixaRepo = caixaRepo; this.usuarioRepo = usuarioRepo; this.vendaRepo = vendaRepo;
    }

    public Optional<CaixaResponse> caixaAberto() {
        return caixaRepo.findFirstByStatusOrderByAberturaDesc(StatusCaixa.ABERTO).map(this::toResponse);
    }

    @Transactional
    public CaixaResponse abrirCaixa(AbrirCaixaRequest req, Long usuarioId) {
        caixaRepo.findFirstByStatusOrderByAberturaDesc(StatusCaixa.ABERTO)
                .ifPresent(c -> { throw new RuntimeException("Já existe um caixa aberto (ID " + c.getId() + "). Feche-o primeiro."); });
        Usuario op = usuarioRepo.findById(usuarioId).orElseThrow(() -> new RuntimeException("Operador não encontrado"));
        CaixaOperacao c = new CaixaOperacao();
        c.setOperador(op);
        c.setAbertura(LocalDateTime.now());
        c.setSaldoAbertura(req.getSaldoAbertura() != null ? req.getSaldoAbertura() : BigDecimal.ZERO);
        c.setStatus(StatusCaixa.ABERTO);
        return toResponse(caixaRepo.save(c));
    }

    @Transactional
    public CaixaResponse fecharCaixa(Long caixaId, FecharCaixaRequest req) {
        CaixaOperacao c = caixaRepo.findById(caixaId).orElseThrow(() -> new RuntimeException("Caixa não encontrado"));
        if (c.getStatus() == StatusCaixa.FECHADO) throw new RuntimeException("Caixa já fechado");

        // Calcula ao vivo no momento do fechamento
        BigDecimal din = vendaRepo.sumByCaixaAndForma(caixaId, FormaPagamento.DINHEIRO);
        BigDecimal deb = vendaRepo.sumByCaixaAndForma(caixaId, FormaPagamento.DEBITO);
        BigDecimal cre = vendaRepo.sumByCaixaAndForma(caixaId, FormaPagamento.CREDITO);
        Long       qty = vendaRepo.countByCaixa(caixaId);

        c.setFechamento(LocalDateTime.now());
        c.setStatus(StatusCaixa.FECHADO);
        c.setTotalVendasDinheiro(din);
        c.setTotalVendasDebito(deb);
        c.setTotalVendasCredito(cre);
        c.setQuantidadeVendas(qty);
        c.setSaldoFechamento(req.getSaldoFechamento() != null ? req.getSaldoFechamento() : BigDecimal.ZERO);
        c.setObservacaoFechamento(req.getObservacao());
        return toResponse(caixaRepo.save(c));
    }

    public List<CaixaResponse> listarHistorico() {
        return caixaRepo.findAllByOrderByAberturaDesc().stream().map(this::toResponse).toList();
    }

    public CaixaResponse buscarPorId(Long id) {
        return caixaRepo.findById(id).map(this::toResponse).orElseThrow(() -> new RuntimeException("Caixa não encontrado"));
    }

    /**
     * Monta o CaixaResponse.
     *
     * Regra: se o caixa ainda está ABERTO, os campos totalVendas* na entidade
     * ainda são nulos (só são gravados no fechamento). Por isso, calculamos
     * sempre ao vivo via VendaRepository para qualquer caixa — assim o modal
     * de "Fechar Caixa" exibe os valores reais antes de confirmar.
     *
     * Para caixas já FECHADOS os campos gravados são usados como fallback caso
     * a query retorne zero (situação improvável mas segura).
     */
    private CaixaResponse toResponse(CaixaOperacao c) {
        CaixaResponse r = new CaixaResponse();
        r.setId(c.getId());
        r.setOperadorNome(c.getOperador().getNomeExibicao());
        r.setOperadorId(c.getOperador().getId());
        r.setAbertura(c.getAbertura());
        r.setFechamento(c.getFechamento());
        r.setSaldoAbertura(c.getSaldoAbertura());
        r.setSaldoFechamento(c.getSaldoFechamento());
        r.setStatus(c.getStatus().name());
        r.setObservacaoFechamento(c.getObservacaoFechamento());

        // Sempre consulta o banco ao vivo — garante valor correto tanto para
        // caixas abertos (campos nulos) quanto para os já fechados.
        BigDecimal din = vendaRepo.sumByCaixaAndForma(c.getId(), FormaPagamento.DINHEIRO);
        BigDecimal deb = vendaRepo.sumByCaixaAndForma(c.getId(), FormaPagamento.DEBITO);
        BigDecimal cre = vendaRepo.sumByCaixaAndForma(c.getId(), FormaPagamento.CREDITO);
        Long       qty = vendaRepo.countByCaixa(c.getId());

        // Se a query retornar zero mas a entidade tiver valor gravado (caixa fechado),
        // usa o valor gravado como fallback confiável.
        if (c.getStatus() == StatusCaixa.FECHADO) {
            din = din.compareTo(BigDecimal.ZERO) == 0 ? nvl(c.getTotalVendasDinheiro()) : din;
            deb = deb.compareTo(BigDecimal.ZERO) == 0 ? nvl(c.getTotalVendasDebito())  : deb;
            cre = cre.compareTo(BigDecimal.ZERO) == 0 ? nvl(c.getTotalVendasCredito()) : cre;
            if (qty == 0L && c.getQuantidadeVendas() != null) qty = c.getQuantidadeVendas();
        }

        r.setTotalVendasDinheiro(din);
        r.setTotalVendasDebito(deb);
        r.setTotalVendasCredito(cre);
        r.setTotalGeral(din.add(deb).add(cre));
        r.setQuantidadeVendas(qty);

        if (c.getSaldoFechamento() != null) {
            BigDecimal esperado = nvl(c.getSaldoAbertura()).add(din);
            r.setDiferenca(c.getSaldoFechamento().subtract(esperado));
        }
        return r;
    }

    private BigDecimal nvl(BigDecimal v) { return v != null ? v : BigDecimal.ZERO; }
}