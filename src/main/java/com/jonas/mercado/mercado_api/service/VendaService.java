package com.jonas.mercado.mercado_api.service;
import com.jonas.mercado.mercado_api.dto.RelatorioVendasResponse;
import com.jonas.mercado.mercado_api.dto.VendaRequest;
import com.jonas.mercado.mercado_api.dto.VendaResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VendaService {
    VendaResponse criarVenda(VendaRequest request);
    List<VendaResponse> listarVendas();
    Optional<VendaResponse> buscarPorId(Long id);
    List<VendaResponse> listarPorPeriodo(LocalDate inicio, LocalDate fim);
    RelatorioVendasResponse gerarRelatorio(LocalDate inicio, LocalDate fim);
}
