// Define o pacote onde a classe está localizada (organização de pastas)
package com.jonas.mercado.mercado_api.entity;

// Importa as anotações da JPA para mapeamento do Banco de Dados
import jakarta.persistence.*;
// Importa o tipo ideal para trabalhar com valores monetários/precisos
import java.math.BigDecimal;

@Entity // Indica que esta classe é uma entidade do banco de dados (mapeia a tabela)
@Table(name = "produtos") // Define explicitamente o nome da tabela no banco de dados
public class Produto {

    @Id // Define que este campo é a Chave Primária (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O banco de dados gera o ID automaticamente (1, 2, 3...)
    private Long id;

    @Column(nullable = false) // Define que a coluna 'nome' não pode ser nula (obrigatória)
    private String nome;

    @Column(unique = true) // Garante que não haverá dois produtos com o mesmo código de barras
    private String codigoBarras;

    @Column(nullable = false) // O preço é obrigatório
    private BigDecimal preco;

    @Column(nullable = false) // A quantidade em estoque é obrigatória
    private Integer quantidade;

    @Column(nullable = false) // Define se o produto está ativo no sistema
    private Boolean ativo = true; // Por padrão, todo produto novo nasce como 'true' (ativo)

    // --- MÉTODOS GETTERS E SETTERS (Encapsulamento) ---
    // Servem para ler e alterar os dados de forma segura

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }
    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public BigDecimal getPreco() {
        return preco;
    }
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Boolean getAtivo() {
        return ativo;
    }
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}