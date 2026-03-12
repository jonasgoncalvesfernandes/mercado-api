package com.jonas.mercado.mercado_api.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
public class VendaResponse {
    private Long id;
    private LocalDateTime dataHora;
    private BigDecimal valorTotal;
    private String formaPagamento;
    private String operadorNome;
    private Long   caixaOperacaoId;
    private List<ItemVendaResponse> itens;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }
    public String getOperadorNome() { return operadorNome; }
    public void setOperadorNome(String v) { this.operadorNome = v; }
    public Long getCaixaOperacaoId() { return caixaOperacaoId; }
    public void setCaixaOperacaoId(Long v) { this.caixaOperacaoId = v; }
    public List<ItemVendaResponse> getItens() { return itens; }
    public void setItens(List<ItemVendaResponse> itens) { this.itens = itens; }
}
