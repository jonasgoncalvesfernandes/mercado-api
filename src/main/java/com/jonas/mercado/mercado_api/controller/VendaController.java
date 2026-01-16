package com.jonas.mercado.mercado_api.controller;

import com.jonas.mercado.mercado_api.dto.VendaRequest;
import com.jonas.mercado.mercado_api.dto.VendaResponse;
import com.jonas.mercado.mercado_api.service.VendaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping
    public ResponseEntity<VendaResponse> criarVenda(@RequestBody VendaRequest request) {
        return ResponseEntity.ok(vendaService.criarVenda(request));
    }

    @GetMapping
    public ResponseEntity<List<VendaResponse>> listar() {
        return ResponseEntity.ok(vendaService.listarVendas());
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<VendaResponse>> listarPorPeriodo(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim
    ) {
        return ResponseEntity.ok(
                vendaService.listarPorPeriodo(inicio, fim)
        );
    }

}