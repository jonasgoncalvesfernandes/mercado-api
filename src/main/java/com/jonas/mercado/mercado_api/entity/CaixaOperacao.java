package com.jonas.mercado.mercado_api.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "caixa_operacoes")
public class CaixaOperacao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario operador;
    @Column(nullable = false)
    private LocalDateTime abertura;
    private LocalDateTime fechamento;
    @Column(nullable = false)
    private BigDecimal saldoAbertura;
    private BigDecimal saldoFechamento;
    private BigDecimal totalVendasDinheiro;
    private BigDecimal totalVendasDebito;
    private BigDecimal totalVendasCredito;
    private Long quantidadeVendas;
    private String observacaoFechamento;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCaixa status = StatusCaixa.ABERTO;

    public enum StatusCaixa { ABERTO, FECHADO }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getOperador() { return operador; }
    public void setOperador(Usuario operador) { this.operador = operador; }
    public LocalDateTime getAbertura() { return abertura; }
    public void setAbertura(LocalDateTime a) { this.abertura = a; }
    public LocalDateTime getFechamento() { return fechamento; }
    public void setFechamento(LocalDateTime f) { this.fechamento = f; }
    public BigDecimal getSaldoAbertura() { return saldoAbertura; }
    public void setSaldoAbertura(BigDecimal v) { this.saldoAbertura = v; }
    public BigDecimal getSaldoFechamento() { return saldoFechamento; }
    public void setSaldoFechamento(BigDecimal v) { this.saldoFechamento = v; }
    public BigDecimal getTotalVendasDinheiro() { return totalVendasDinheiro; }
    public void setTotalVendasDinheiro(BigDecimal v) { this.totalVendasDinheiro = v; }
    public BigDecimal getTotalVendasDebito() { return totalVendasDebito; }
    public void setTotalVendasDebito(BigDecimal v) { this.totalVendasDebito = v; }
    public BigDecimal getTotalVendasCredito() { return totalVendasCredito; }
    public void setTotalVendasCredito(BigDecimal v) { this.totalVendasCredito = v; }
    public Long getQuantidadeVendas() { return quantidadeVendas; }
    public void setQuantidadeVendas(Long v) { this.quantidadeVendas = v; }
    public String getObservacaoFechamento() { return observacaoFechamento; }
    public void setObservacaoFechamento(String v) { this.observacaoFechamento = v; }
    public StatusCaixa getStatus() { return status; }
    public void setStatus(StatusCaixa s) { this.status = s; }
}
