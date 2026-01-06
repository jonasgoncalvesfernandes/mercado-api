package com.jonas.mercado.mercado_api.service;

import com.jonas.mercado.mercado_api.dto.VendaRequest;
import com.jonas.mercado.mercado_api.entity.Venda;

public interface VendaService {
    Venda criarVenda(VendaRequest request);
}
