package com.jonas.mercado.mercado_api.dto;
import java.math.BigDecimal;
import java.time.LocalDate;
public class RelatorioVendasResponse {
    private LocalDate  inicio;
    private LocalDate  fim;
    private Long       totalVendas;
    private BigDecimal valorTotal;
    private BigDecimal ticketMedio;
    private BigDecimal totalDinheiro;
    private BigDecimal totalDebito;
    private BigDecimal totalCredito;
    public LocalDate getInicio() { return inicio; }
    public void setInicio(LocalDate inicio) { this.inicio = inicio; }
    public LocalDate getFim() { return fim; }
    public void setFim(LocalDate fim) { this.fim = fim; }
    public Long getTotalVendas() { return totalVendas; }
    public void setTotalVendas(Long totalVendas) { this.totalVendas = totalVendas; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
    public BigDecimal getTicketMedio() { return ticketMedio; }
    public void setTicketMedio(BigDecimal ticketMedio) { this.ticketMedio = ticketMedio; }
    public BigDecimal getTotalDinheiro() { return totalDinheiro; }
    public void setTotalDinheiro(BigDecimal v) { this.totalDinheiro = v; }
    public BigDecimal getTotalDebito() { return totalDebito; }
    public void setTotalDebito(BigDecimal v) { this.totalDebito = v; }
    public BigDecimal getTotalCredito() { return totalCredito; }
    public void setTotalCredito(BigDecimal v) { this.totalCredito = v; }
}
