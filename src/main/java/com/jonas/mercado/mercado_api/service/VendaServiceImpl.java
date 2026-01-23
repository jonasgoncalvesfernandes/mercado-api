package com.jonas.mercado.mercado_api.service;

import com.jonas.mercado.mercado_api.dto.*;
import com.jonas.mercado.mercado_api.entity.*;
import com.jonas.mercado.mercado_api.exception.EstoqueInsuficienteException;
import com.jonas.mercado.mercado_api.exception.ProdutoNaoEncontradoException;
import com.jonas.mercado.mercado_api.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
public class VendaServiceImpl implements VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;

    public VendaServiceImpl(
            VendaRepository vendaRepository,
            ProdutoRepository produtoRepository
    ) {
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
    }

@Transactional
@Override
public VendaResponse criarVenda(VendaRequest request) {

    Venda venda = new Venda();
    venda.setDataHora(LocalDateTime.now());
    venda.setFormaPagamento(
        FormaPagamento.valueOf(request.getFormaPagamento().toUpperCase())
    );

    BigDecimal total = BigDecimal.ZERO;
    List<ItemVenda> itens = new ArrayList<>();

    for (ItemVendaRequest itemReq : request.getItens()) {

        if (itemReq.getQuantidade() == null || itemReq.getQuantidade() <= 0) {
            throw new RuntimeException("Quantidade inválida");
        }

        Produto produto = produtoRepository.findById(itemReq.getProdutoId())
                .orElseThrow(() -> new ProdutoNaoEncontradoException(itemReq.getProdutoId()));

        if (produto.getQuantidade() < itemReq.getQuantidade()) {
                throw new EstoqueInsuficienteException(produto.getNome());
        }

        // baixa de estoque
        produto.setQuantidade(produto.getQuantidade() - itemReq.getQuantidade());
        produtoRepository.save(produto);

        ItemVenda item = new ItemVenda();
        item.setVenda(venda);
        item.setProduto(produto);
        item.setQuantidade(itemReq.getQuantidade());
        item.setPrecoUnitario(produto.getPreco());

        total = total.add(item.getSubtotal());
        itens.add(item);
    }

    venda.setItens(itens);
    venda.setValorTotal(total);

    Venda vendaSalva = vendaRepository.save(venda);
    return toResponse(vendaSalva);
}

@Override
public List<VendaResponse> listarVendas() {
    return vendaRepository.findAll()
            .stream()
            .map(this::toResponse)
            .toList();
}


private VendaResponse toResponse(Venda venda) {
    VendaResponse response = new VendaResponse();
    response.setId(venda.getId());
    response.setDataHora(venda.getDataHora());
    response.setValorTotal(venda.getValorTotal());
    response.setFormaPagamento(venda.getFormaPagamento().name());

    List<ItemVendaResponse> itens = venda.getItens().stream().map(item -> {
        ItemVendaResponse itemResp = new ItemVendaResponse();
        itemResp.setProdutoId(item.getProduto().getId());
        itemResp.setNomeProduto(item.getProduto().getNome());
        itemResp.setQuantidade(item.getQuantidade());
        itemResp.setPrecoUnitario(item.getPrecoUnitario());
        itemResp.setSubtotal(item.getSubtotal());
        return itemResp;
    }).toList();

    response.setItens(itens);
    return response;
}

@Override
public List <VendaResponse> listarPorPeriodo(
        LocalDate inicio,
        LocalDate fim
    
) {
    LocalDateTime inicioDataHora = inicio.atStartOfDay();
    LocalDateTime fimDataHora = fim.atTime(23, 59, 59);

    return vendaRepository
            .findByDataHoraBetween(inicioDataHora, fimDataHora)
            .stream()
            .map(this::toResponse)
            .toList();
}

@Override
public RelatorioVendasResponse gerarRelatorio(
        LocalDate inicio,
        LocalDate fim
) {
    LocalDateTime inicioDataHora = inicio.atStartOfDay();
    LocalDateTime fimDataHora = fim.atTime(23, 59, 59);

    List<Venda> vendas = vendaRepository
            .findByDataHoraBetween(inicioDataHora, fimDataHora);

    BigDecimal valorTotal = vendas.stream()
            .map(Venda::getValorTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    long totalVendas = vendas.size();

    BigDecimal ticketMedio = totalVendas > 0
            ? valorTotal.divide(
                    BigDecimal.valueOf(totalVendas),
                    2,
                    BigDecimal.ROUND_HALF_UP
              )
            : BigDecimal.ZERO;

    RelatorioVendasResponse response = new RelatorioVendasResponse();
    response.setInicio(inicio);
    response.setFim(fim);
    response.setTotalVendas(totalVendas);
    response.setValorTotal(valorTotal);
    response.setTicketMedio(ticketMedio);

    return response;
}


}

