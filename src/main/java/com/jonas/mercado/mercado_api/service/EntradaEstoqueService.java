package com.jonas.mercado.mercado_api.service;

import com.jonas.mercado.mercado_api.dto.EntradaEstoqueRequest;
import com.jonas.mercado.mercado_api.dto.EntradaEstoqueResponse;
import com.jonas.mercado.mercado_api.entity.*;
import com.jonas.mercado.mercado_api.exception.ProdutoNaoEncontradoException;
import com.jonas.mercado.mercado_api.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EntradaEstoqueService {
    private final EntradaEstoqueRepository entradaRepo;
    private final ProdutoRepository        produtoRepo;
    private final UsuarioRepository        usuarioRepo;

    public EntradaEstoqueService(EntradaEstoqueRepository e, ProdutoRepository p, UsuarioRepository u) {
        this.entradaRepo = e; this.produtoRepo = p; this.usuarioRepo = u;
    }

    @Transactional
    public EntradaEstoqueResponse registrar(EntradaEstoqueRequest req, Long usuarioId) {
        if (req.getQuantidade() == null || req.getQuantidade() <= 0)
            throw new RuntimeException("Quantidade deve ser maior que zero");
        Produto produto = produtoRepo.findById(req.getProdutoId())
                .orElseThrow(() -> new ProdutoNaoEncontradoException(req.getProdutoId()));
        Usuario resp = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        int anterior = produto.getQuantidade();
        int novo     = anterior + req.getQuantidade();
        produto.setQuantidade(novo);
        produtoRepo.save(produto);
        EntradaEstoque e = new EntradaEstoque();
        e.setProduto(produto);
        e.setResponsavel(resp);
        e.setQuantidadeEntrada(req.getQuantidade());
        e.setEstoqueAnterior(anterior);
        e.setEstoqueNovo(novo);
        e.setPrecoCompra(req.getPrecoCompra());
        e.setFornecedor(req.getFornecedor());
        e.setObservacao(req.getObservacao());
        e.setDataHora(LocalDateTime.now());
        return toResponse(entradaRepo.save(e));
    }

    public List<EntradaEstoqueResponse> listarTodas() {
        return entradaRepo.findAllByOrderByDataHoraDesc().stream().map(this::toResponse).toList();
    }

    public List<EntradaEstoqueResponse> listarPorProduto(Long produtoId) {
        return entradaRepo.findByProdutoIdOrderByDataHoraDesc(produtoId).stream().map(this::toResponse).toList();
    }

    private EntradaEstoqueResponse toResponse(EntradaEstoque e) {
        EntradaEstoqueResponse r = new EntradaEstoqueResponse();
        r.setId(e.getId()); r.setProdutoId(e.getProduto().getId());
        r.setProdutoNome(e.getProduto().getNome()); r.setResponsavelNome(e.getResponsavel().getNomeExibicao());
        r.setQuantidadeEntrada(e.getQuantidadeEntrada()); r.setEstoqueAnterior(e.getEstoqueAnterior());
        r.setEstoqueNovo(e.getEstoqueNovo()); r.setPrecoCompra(e.getPrecoCompra());
        r.setFornecedor(e.getFornecedor()); r.setObservacao(e.getObservacao());
        r.setDataHora(e.getDataHora()); return r;
    }
}
