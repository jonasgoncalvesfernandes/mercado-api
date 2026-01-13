package com.jonas.mercado.mercado_api.controller;

import com.jonas.mercado.mercado_api.dto.VendaRequest;
import com.jonas.mercado.mercado_api.entity.Venda;
import com.jonas.mercado.mercado_api.service.VendaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping
    public ResponseEntity<Venda> criarVenda(@RequestBody VendaRequest request) {
        return ResponseEntity.ok(vendaService.criarVenda(request));
    }

    @GetMapping
    public ResponseEntity<java.util.List<Venda>> listarVendas() {
        return ResponseEntity.ok(vendaService.listarVendas());
    }
}