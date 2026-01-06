package com.jonas.mercado.mercado_api.dto;

public class ItemVendaRequest {
    private long produtoId;

// Usa o wrapper Integer para permitir valores nulos.
// O tipo primitivo int não aceita null e assume 0 por padrão,
// o que pode invalidar a venda quando a quantidade não é definida.
    private Integer quantidade;

    
    //Getter e set
    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}