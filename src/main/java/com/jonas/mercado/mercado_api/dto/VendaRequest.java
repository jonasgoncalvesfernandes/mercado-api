package com.jonas.mercado.mercado_api.dto;

import java.util.List;

public class VendaRequest {

    private String formaPagamento;
    private List<ItemVendaRequest> itens;

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public List<ItemVendaRequest> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendaRequest> itens) {
        this.itens = itens;
    }
}
