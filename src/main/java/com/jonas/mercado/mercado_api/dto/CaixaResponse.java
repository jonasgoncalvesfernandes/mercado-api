package com.jonas.mercado.mercado_api.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
public class CaixaResponse {
    private Long id;
    private String operadorNome;
    private Long   operadorId;
    private LocalDateTime abertura;
    private LocalDateTime fechamento;
    private BigDecimal saldoAbertura;
    private BigDecimal saldoFechamento;
    private BigDecimal totalVendasDinheiro;
    private BigDecimal totalVendasDebito;
    private BigDecimal totalVendasCredito;
    private BigDecimal totalGeral;
    private Long       quantidadeVendas;
    private BigDecimal diferenca;
    private String status;
    private String observacaoFechamento;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOperadorNome() { return operadorNome; }
    public void setOperadorNome(String v) { this.operadorNome = v; }
    public Long getOperadorId() { return operadorId; }
    public void setOperadorId(Long v) { this.operadorId = v; }
    public LocalDateTime getAbertura() { return abertura; }
    public void setAbertura(LocalDateTime v) { this.abertura = v; }
    public LocalDateTime getFechamento() { return fechamento; }
    public void setFechamento(LocalDateTime v) { this.fechamento = v; }
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
    public BigDecimal getTotalGeral() { return totalGeral; }
    public void setTotalGeral(BigDecimal v) { this.totalGeral = v; }
    public Long getQuantidadeVendas() { return quantidadeVendas; }
    public void setQuantidadeVendas(Long v) { this.quantidadeVendas = v; }
    public BigDecimal getDiferenca() { return diferenca; }
    public void setDiferenca(BigDecimal v) { this.diferenca = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
    public String getObservacaoFechamento() { return observacaoFechamento; }
    public void setObservacaoFechamento(String v) { this.observacaoFechamento = v; }
}
