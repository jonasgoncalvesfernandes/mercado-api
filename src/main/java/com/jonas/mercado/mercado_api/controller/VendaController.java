package com.jonas.mercado.mercado_api.controller;

import com.jonas.mercado.mercado_api.dto.RelatorioVendasResponse;
import com.jonas.mercado.mercado_api.dto.VendaRequest;
import com.jonas.mercado.mercado_api.dto.VendaResponse;
import com.jonas.mercado.mercado_api.service.VendaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/vendas")
@CrossOrigin(origins = {"http://localhost:5173","http://localhost:3000","http://127.0.0.1:5173"})
public class VendaController {
    private final VendaService vendaService;
    public VendaController(VendaService vendaService) { this.vendaService = vendaService; }

    @PostMapping
    public ResponseEntity<VendaResponse> criar(@RequestBody VendaRequest req) {
        return ResponseEntity.ok(vendaService.criarVenda(req));
    }

    @GetMapping
    public List<VendaResponse> listar() { return vendaService.listarVendas(); }

    @GetMapping("/{id}")
    public ResponseEntity<VendaResponse> buscar(@PathVariable Long id) {
        return vendaService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/periodo")
    public List<VendaResponse> porPeriodo(
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate fim) {
        return vendaService.listarPorPeriodo(inicio, fim);
    }

    @GetMapping("/relatorio/periodo")
    public RelatorioVendasResponse relatorio(
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate fim) {
        return vendaService.gerarRelatorio(inicio, fim);
    }
}
