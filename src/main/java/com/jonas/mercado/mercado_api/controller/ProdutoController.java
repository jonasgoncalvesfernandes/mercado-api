package com.jonas.mercado.mercado_api.controller;

import com.jonas.mercado.mercado_api.entity.Produto;
import com.jonas.mercado.mercado_api.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    // Injeção de dependência via construtor
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody Produto produto) {
        return ResponseEntity.ok(produtoService.salvar(produto));
    }

    // READ - listar todos
    @GetMapping
    public ResponseEntity<List<Produto>> listar() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    // READ - buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(
            @PathVariable Long id,
            @RequestBody Produto produto
    ) {
        return ResponseEntity.ok(produtoService.atualizar(id, produto));
    }

    // DELETE lógico (desativar)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        Produto produto = produtoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setAtivo(false);
        produtoService.salvar(produto);

        return ResponseEntity.noContent().build();
    }
    
    // READ - buscar por código de barras (PDV / scanner)
    @GetMapping("/codigo/{codigoBarras}")
    public ResponseEntity<Produto> buscarPorCodigoBarras(
            @PathVariable String codigoBarras
    ) {
        return produtoService.buscarPorCodigoBarras(codigoBarras)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
