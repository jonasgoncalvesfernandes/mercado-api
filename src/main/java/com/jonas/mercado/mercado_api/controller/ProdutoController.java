package com.jonas.mercado.mercado_api.controller;

import com.jonas.mercado.mercado_api.entity.Produto;
import com.jonas.mercado.mercado_api.exception.ProdutoNaoEncontradoException;
import com.jonas.mercado.mercado_api.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = {"http://localhost:5173","http://localhost:3000","http://127.0.0.1:5173"})
public class ProdutoController {
    private final ProdutoService produtoService;
    public ProdutoController(ProdutoService produtoService) { this.produtoService = produtoService; }

    @GetMapping
    public List<Produto> listarTodos() { return produtoService.listarTodos(); }

    @GetMapping("/ativos")
    public List<Produto> listarAtivos() { return produtoService.listarAtivos(); }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id).map(ResponseEntity::ok)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Produto> buscarPorCodigo(@PathVariable String codigo) {
        return produtoService.buscarPorCodigoBarras(codigo).map(ResponseEntity::ok)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(codigo));
    }

    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody Produto produto) {
        return ResponseEntity.ok(produtoService.salvar(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        return ResponseEntity.ok(produtoService.atualizar(id, produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        produtoService.desativar(id); return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        produtoService.ativar(id); return ResponseEntity.noContent().build();
    }
}
