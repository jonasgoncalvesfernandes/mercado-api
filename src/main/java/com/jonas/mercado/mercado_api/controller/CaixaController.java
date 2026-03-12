package com.jonas.mercado.mercado_api.controller;
import com.jonas.mercado.mercado_api.dto.AbrirCaixaRequest;
import com.jonas.mercado.mercado_api.dto.CaixaResponse;
import com.jonas.mercado.mercado_api.dto.FecharCaixaRequest;
import com.jonas.mercado.mercado_api.service.CaixaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/caixa")
@CrossOrigin(origins = {"http://localhost:5173","http://localhost:3000","http://127.0.0.1:5173"})
public class CaixaController {
    private final CaixaService caixaService;
    public CaixaController(CaixaService caixaService) { this.caixaService = caixaService; }

    @GetMapping("/status")
    public ResponseEntity<CaixaResponse> status() {
        return caixaService.caixaAberto().map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }

    @PostMapping("/abrir")
    public ResponseEntity<CaixaResponse> abrir(@RequestBody AbrirCaixaRequest req,
                                                @RequestHeader("X-Usuario-Id") Long usuarioId) {
        return ResponseEntity.ok(caixaService.abrirCaixa(req, usuarioId));
    }

    @PostMapping("/{id}/fechar")
    public ResponseEntity<CaixaResponse> fechar(@PathVariable Long id, @RequestBody FecharCaixaRequest req) {
        return ResponseEntity.ok(caixaService.fecharCaixa(id, req));
    }

    @GetMapping("/historico")
    public ResponseEntity<List<CaixaResponse>> historico() {
        return ResponseEntity.ok(caixaService.listarHistorico());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaixaResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(caixaService.buscarPorId(id));
    }
}
