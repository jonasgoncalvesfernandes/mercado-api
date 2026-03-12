package com.jonas.mercado.mercado_api.dto;
import java.util.List;
public class VendaRequest {
    private String formaPagamento;
    private List<ItemVendaRequest> itens;
    private Long   caixaOperacaoId;
    private String operadorNome;
    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }
    public List<ItemVendaRequest> getItens() { return itens; }
    public void setItens(List<ItemVendaRequest> itens) { this.itens = itens; }
    public Long getCaixaOperacaoId() { return caixaOperacaoId; }
    public void setCaixaOperacaoId(Long v) { this.caixaOperacaoId = v; }
    public String getOperadorNome() { return operadorNome; }
    public void setOperadorNome(String v) { this.operadorNome = v; }
}
