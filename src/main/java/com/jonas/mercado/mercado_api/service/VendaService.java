package com.jonas.mercado.mercado_api.service;

import java.util.List;

import com.jonas.mercado.mercado_api.dto.VendaRequest;
import com.jonas.mercado.mercado_api.dto.VendaResponse;
import java.time.LocalDate;

public interface VendaService {

    VendaResponse criarVenda(VendaRequest request);

    List<VendaResponse> listarVendas();

    List<VendaResponse> listarPorPeriodo(
        LocalDate inicio,
        LocalDate fim
    );
}

