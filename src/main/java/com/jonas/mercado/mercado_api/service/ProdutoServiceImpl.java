package com.jonas.mercado.mercado_api.service;

import com.jonas.mercado.mercado_api.entity.Produto;
import com.jonas.mercado.mercado_api.exception.CodigoDeBarrasExistente;
import com.jonas.mercado.mercado_api.exception.ProdutoNaoEncontradoException;
import com.jonas.mercado.mercado_api.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoServiceImpl implements ProdutoService {
    private final ProdutoRepository repo;
    public ProdutoServiceImpl(ProdutoRepository repo) { this.repo = repo; }

    @Override public List<Produto> listarTodos()  { return repo.findAll(); }
    @Override public List<Produto> listarAtivos()  { return repo.findByAtivoTrue(); }
    @Override public Optional<Produto> buscarPorId(Long id) { return repo.findById(id); }
    @Override public Optional<Produto> buscarPorCodigoBarras(String c) { return repo.findByCodigoBarras(c); }

    @Override
    public Produto salvar(Produto p) {
        if (p.getCodigoBarras() != null && !p.getCodigoBarras().isBlank()) {
            repo.findByCodigoBarras(p.getCodigoBarras())
                .ifPresent(ex -> { throw new CodigoDeBarrasExistente(p.getCodigoBarras()); });
        } else {
            p.setCodigoBarras(null);
        }
        return repo.save(p);
    }

    @Override
    public Produto atualizar(Long id, Produto dados) {
        Produto p = repo.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException(id));
        if (dados.getCodigoBarras() != null && !dados.getCodigoBarras().isBlank()) {
            repo.findByCodigoBarrasAndIdNot(dados.getCodigoBarras(), id)
                .ifPresent(ex -> { throw new CodigoDeBarrasExistente(dados.getCodigoBarras()); });
            p.setCodigoBarras(dados.getCodigoBarras());
        } else {
            p.setCodigoBarras(null);
        }
        p.setNome(dados.getNome());
        p.setPreco(dados.getPreco());
        p.setQuantidade(dados.getQuantidade());
        return repo.save(p);
    }

    @Override
    public void desativar(Long id) {
        Produto p = repo.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException(id));
        p.setAtivo(false); repo.save(p);
    }

    @Override
    public void ativar(Long id) {
        Produto p = repo.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException(id));
        p.setAtivo(true); repo.save(p);
    }
}
