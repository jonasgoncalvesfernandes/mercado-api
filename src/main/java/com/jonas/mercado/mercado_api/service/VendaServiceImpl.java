package com.jonas.mercado.mercado_api.service;

import com.jonas.mercado.mercado_api.dto.*;
import com.jonas.mercado.mercado_api.entity.*;
import com.jonas.mercado.mercado_api.exception.EstoqueInsuficienteException;
import com.jonas.mercado.mercado_api.exception.ProdutoNaoEncontradoException;
import com.jonas.mercado.mercado_api.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VendaServiceImpl implements VendaService {
    private final VendaRepository         vendaRepo;
    private final ProdutoRepository       produtoRepo;
    private final CaixaOperacaoRepository caixaRepo;

    public VendaServiceImpl(VendaRepository v, ProdutoRepository p, CaixaOperacaoRepository c) {
        this.vendaRepo = v; this.produtoRepo = p; this.caixaRepo = c;
    }

    @Transactional
    @Override
    public VendaResponse criarVenda(VendaRequest req) {
        Venda venda = new Venda();
        venda.setDataHora(LocalDateTime.now());
        venda.setFormaPagamento(FormaPagamento.valueOf(req.getFormaPagamento().toUpperCase()));
        venda.setOperadorNome(req.getOperadorNome());
        if (req.getCaixaOperacaoId() != null)
            caixaRepo.findById(req.getCaixaOperacaoId()).ifPresent(venda::setCaixaOperacao);

        BigDecimal total = BigDecimal.ZERO;
        List<ItemVenda> itens = new ArrayList<>();
        for (ItemVendaRequest ir : req.getItens()) {
            if (ir.getQuantidade() == null || ir.getQuantidade() <= 0)
                throw new RuntimeException("Quantidade inválida para produto " + ir.getProdutoId());
            Produto p = produtoRepo.findById(ir.getProdutoId())
                    .orElseThrow(() -> new ProdutoNaoEncontradoException(ir.getProdutoId()));
            if (p.getQuantidade() < ir.getQuantidade())
                throw new EstoqueInsuficienteException(p.getNome());
            p.setQuantidade(p.getQuantidade() - ir.getQuantidade());
            produtoRepo.save(p);
            ItemVenda item = new ItemVenda();
            item.setVenda(venda); item.setProduto(p);
            item.setQuantidade(ir.getQuantidade()); item.setPrecoUnitario(p.getPreco());
            total = total.add(item.getSubtotal()); itens.add(item);
        }
        venda.setItens(itens); venda.setValorTotal(total);
        return toResponse(vendaRepo.save(venda));
    }

    @Override public List<VendaResponse> listarVendas() { return vendaRepo.findAll().stream().map(this::toResponse).toList(); }
    @Override public Optional<VendaResponse> buscarPorId(Long id) { return vendaRepo.findById(id).map(this::toResponse); }

    @Override
    public List<VendaResponse> listarPorPeriodo(LocalDate i, LocalDate f) {
        return vendaRepo.findByDataHoraBetween(i.atStartOfDay(), f.atTime(23,59,59)).stream().map(this::toResponse).toList();
    }

    @Override
    public RelatorioVendasResponse gerarRelatorio(LocalDate i, LocalDate f) {
        List<Venda> vs = vendaRepo.findByDataHoraBetween(i.atStartOfDay(), f.atTime(23,59,59));
        BigDecimal total = vs.stream().map(Venda::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        long qtd = vs.size();
        BigDecimal ticket = qtd > 0 ? total.divide(BigDecimal.valueOf(qtd), 2, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO;
        BigDecimal din = vs.stream().filter(v->v.getFormaPagamento()==FormaPagamento.DINHEIRO).map(Venda::getValorTotal).reduce(BigDecimal.ZERO,BigDecimal::add);
        BigDecimal deb = vs.stream().filter(v->v.getFormaPagamento()==FormaPagamento.DEBITO).map(Venda::getValorTotal).reduce(BigDecimal.ZERO,BigDecimal::add);
        BigDecimal cre = vs.stream().filter(v->v.getFormaPagamento()==FormaPagamento.CREDITO).map(Venda::getValorTotal).reduce(BigDecimal.ZERO,BigDecimal::add);
        RelatorioVendasResponse r = new RelatorioVendasResponse();
        r.setInicio(i); r.setFim(f); r.setTotalVendas(qtd); r.setValorTotal(total);
        r.setTicketMedio(ticket); r.setTotalDinheiro(din); r.setTotalDebito(deb); r.setTotalCredito(cre);
        return r;
    }

    private VendaResponse toResponse(Venda v) {
        VendaResponse r = new VendaResponse();
        r.setId(v.getId()); r.setDataHora(v.getDataHora()); r.setValorTotal(v.getValorTotal());
        r.setFormaPagamento(v.getFormaPagamento().name()); r.setOperadorNome(v.getOperadorNome());
        if (v.getCaixaOperacao() != null) r.setCaixaOperacaoId(v.getCaixaOperacao().getId());
        r.setItens(v.getItens().stream().map(it -> {
            ItemVendaResponse ir = new ItemVendaResponse();
            ir.setProdutoId(it.getProduto().getId()); ir.setNomeProduto(it.getProduto().getNome());
            ir.setQuantidade(it.getQuantidade()); ir.setPrecoUnitario(it.getPrecoUnitario());
            ir.setSubtotal(it.getSubtotal()); return ir;
        }).toList());
        return r;
    }
}
