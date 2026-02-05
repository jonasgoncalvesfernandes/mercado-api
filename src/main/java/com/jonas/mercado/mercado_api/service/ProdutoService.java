package com.jonas.mercado.mercado_api.service;

import com.jonas.mercado.mercado_api.entity.Produto;

import java.util.List;
import java.util.Optional;


//Metodos declarados para serem implementados na classe ProdutoServiceImpl
public interface ProdutoService {

    Produto salvar(Produto produto);

    Optional<Produto> buscarPorCodigoBarras(String codigoBarras);

    Optional<Produto> buscarPorId(Long id);

    List<Produto> listarTodos();

    List<Produto> listarAtivos();

    void desativar(Long id);

    void ativar(Long id);

    Produto atualizar(Long id, Produto produtoAtualizado);
    
}
