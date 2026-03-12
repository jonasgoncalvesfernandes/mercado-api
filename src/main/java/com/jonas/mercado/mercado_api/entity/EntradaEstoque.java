package com.jonas.mercado.mercado_api.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "entradas_estoque")
public class EntradaEstoque {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;
    @ManyToOne @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario responsavel;
    @Column(nullable = false)
    private Integer quantidadeEntrada;
    @Column(nullable = false)
    private Integer estoqueAnterior;
    @Column(nullable = false)
    private Integer estoqueNovo;
    private BigDecimal precoCompra;
    private String fornecedor;
    private String observacao;
    @Column(nullable = false)
    private LocalDateTime dataHora;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Produto getProduto() { return produto; }
    public void setProduto(Produto p) { this.produto = p; }
    public Usuario getResponsavel() { return responsavel; }
    public void setResponsavel(Usuario u) { this.responsavel = u; }
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
    public void setDataHora(LocalDateTime d) { this.dataHora = d; }
}
