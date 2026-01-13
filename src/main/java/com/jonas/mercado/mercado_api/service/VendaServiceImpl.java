package com.jonas.mercado.mercado_api.service;

import com.jonas.mercado.mercado_api.dto.*;
import com.jonas.mercado.mercado_api.entity.*;
import com.jonas.mercado.mercado_api.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
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
public Venda criarVenda(VendaRequest request) {

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
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (produto.getQuantidade() < itemReq.getQuantidade()) {
            throw new RuntimeException("Estoque insuficiente");
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

    return vendaRepository.save(venda);
}

@Override
public List<Venda> listarVendas() {
    return vendaRepository.findAll();
}
}

