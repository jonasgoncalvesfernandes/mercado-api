package com.jonas.mercado.mercado_api.dto;
import java.math.BigDecimal;
public class AbrirCaixaRequest {
    private BigDecimal saldoAbertura;
    public BigDecimal getSaldoAbertura() { return saldoAbertura; }
    public void setSaldoAbertura(BigDecimal saldoAbertura) { this.saldoAbertura = saldoAbertura; }
}
