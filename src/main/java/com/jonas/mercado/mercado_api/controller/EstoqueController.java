package com.jonas.mercado.mercado_api.controller;
import com.jonas.mercado.mercado_api.dto.EntradaEstoqueRequest;
import com.jonas.mercado.mercado_api.dto.EntradaEstoqueResponse;
import com.jonas.mercado.mercado_api.service.EntradaEstoqueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/estoque")
@CrossOrigin(origins = {"http://localhost:5173","http://localhost:3000","http://127.0.0.1:5173"})
public class EstoqueController {
    private final EntradaEstoqueService service;
    public EstoqueController(EntradaEstoqueService service) { this.service = service; }

    @PostMapping("/entrada")
    public ResponseEntity<EntradaEstoqueResponse> entrada(@RequestBody EntradaEstoqueRequest req,
                                                           @RequestHeader("X-Usuario-Id") Long usuarioId) {
        return ResponseEntity.ok(service.registrar(req, usuarioId));
    }

    @GetMapping("/entradas")
    public ResponseEntity<List<EntradaEstoqueResponse>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/entradas/produto/{id}")
    public ResponseEntity<List<EntradaEstoqueResponse>> listarPorProduto(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorProduto(id));
    }
}
