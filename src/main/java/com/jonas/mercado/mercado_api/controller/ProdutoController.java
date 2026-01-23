package com.jonas.mercado.mercado_api.controller;

import com.jonas.mercado.mercado_api.entity.Produto;
import com.jonas.mercado.mercado_api.exception.ProdutoNaoEncontradoException;
import com.jonas.mercado.mercado_api.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// Responde as requisições HTTP relacionadas a produtos
@RestController
// Define o caminho base para todas as rotas deste controlador, que será "/produtos"
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    // Injeção de dependência via construtor
    // Spring automaticamente fornece a implementação de ProdutoService
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // CREATE
    // Cria um novo produto, POSTMAN solicitações para /produtos e transforma o corpo da requisição em um objeto Produto
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
        Produto produto = produtoService.buscarPorId(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));

        return ResponseEntity.ok(produto);
    }


    // UPDATE
    // Put para atualizar um produto existente buscando pelo ID
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(
        // pathvariable extrai o ID da URL, requestbody extrai o produto do corpo da requisição
            @PathVariable Long id,
            @RequestBody Produto produto
    ) {
        // Chama o serviço para atualizar o produto e retorna a resposta
        return ResponseEntity.ok(produtoService.atualizar(id, produto));
    }

    // DELETE lógico (desativar)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        produtoService.desativar(id);
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
