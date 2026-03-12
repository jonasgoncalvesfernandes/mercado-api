package com.jonas.mercado.mercado_api.dto;
import java.math.BigDecimal;
public class FecharCaixaRequest {
    private BigDecimal saldoFechamento;
    private String observacao;
    public BigDecimal getSaldoFechamento() { return saldoFechamento; }
    public void setSaldoFechamento(BigDecimal v) { this.saldoFechamento = v; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String v) { this.observacao = v; }
}
