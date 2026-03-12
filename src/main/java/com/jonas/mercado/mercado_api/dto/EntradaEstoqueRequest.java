package com.jonas.mercado.mercado_api.dto;
import java.math.BigDecimal;
public class EntradaEstoqueRequest {
    private Long produtoId;
    private Integer quantidade;
    private BigDecimal precoCompra;
    private String fornecedor;
    private String observacao;
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long v) { this.produtoId = v; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer v) { this.quantidade = v; }
    public BigDecimal getPrecoCompra() { return precoCompra; }
    public void setPrecoCompra(BigDecimal v) { this.precoCompra = v; }
    public String getFornecedor() { return fornecedor; }
    public void setFornecedor(String v) { this.fornecedor = v; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String v) { this.observacao = v; }
}
