package com.jonas.mercado.mercado_api.exception;

public class ProdutoNaoEncontradoException extends RuntimeException {

    public ProdutoNaoEncontradoException(Long id) {
        super("Produto não encontrado. ID: " + id);
    }

    public ProdutoNaoEncontradoException(String codigo) {
        super("Produto não encontrado. Código: " + codigo);
    }
}
