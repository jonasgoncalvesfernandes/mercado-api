package com.jonas.mercado.mercado_api.service;

import com.jonas.mercado.mercado_api.entity.Produto;
import com.jonas.mercado.mercado_api.exception.ProdutoNaoEncontradoException;
import com.jonas.mercado.mercado_api.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public Produto salvar(Produto produto) {
        if (produtoRepository.findByCodigoBarras(produto.getCodigoBarras()).isPresent()) {
                throw new RuntimeException("Já existe produto com esse código de barras");
            }
            return produtoRepository.save(produto);
        }

    @Override
    public Optional<Produto> buscarPorCodigoBarras(String codigoBarras) {
        return produtoRepository.findByCodigoBarras(codigoBarras);
    }

    @Override
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    @Override
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    @Override
    public List<Produto> listarAtivos() {
        return produtoRepository.findAll()
                .stream()
                .filter(Produto::getAtivo)
                .toList();
    }

    @Override
    public void desativar(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));

        produto.setAtivo(false);
        produtoRepository.save(produto);
    }

    @Override
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setNome(produtoAtualizado.getNome());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setQuantidade(produtoAtualizado.getQuantidade());
        produto.setCodigoBarras(produtoAtualizado.getCodigoBarras());
        produto.setAtivo(produtoAtualizado.getAtivo());

        return produtoRepository.save(produto);
    }
}
