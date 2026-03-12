package com.jonas.mercado.mercado_api.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
public class EntradaEstoqueResponse {
    private Long id;
    private Long produtoId;
    private String produtoNome;
    private String responsavelNome;
    private Integer quantidadeEntrada;
    private Integer estoqueAnterior;
    private Integer estoqueNovo;
    private BigDecimal precoCompra;
    private String fornecedor;
    private String observacao;
    private LocalDateTime dataHora;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long v) { this.produtoId = v; }
    public String getProdutoNome() { return produtoNome; }
    public void setProdutoNome(String v) { this.produtoNome = v; }
    public String getResponsavelNome() { return responsavelNome; }
    public void setResponsavelNome(String v) { this.responsavelNome = v; }
    public Integer getQuantidadeEntrada() { return quantidadeEntrada; }
    public void setQuantidadeEntrada(Integer v) { this.quantidadeEntrada = v; }
    public Integer getEstoqueAnterior() { return estoqueAnterior; }
    public void setEstoqueAnterior(Integer v) { this.estoqueAnterior = v; }
    public Integer getEstoqueNovo() { return estoqueNovo; }
    public void setEstoqueNovo(Integer v) { this.estoqueNovo = v; }
    public BigDecimal getPrecoCompra() { return precoCompra; }
    public void setPrecoCompra(BigDecimal v) { this.precoCompra = v; }
    public String getFornecedor() { return fornecedor; }
    public void setFornecedor(String v) { this.fornecedor = v; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String v) { this.observacao = v; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime v) { this.dataHora = v; }
}
