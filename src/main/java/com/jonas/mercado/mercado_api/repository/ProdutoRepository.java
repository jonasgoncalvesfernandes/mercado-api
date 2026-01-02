package com.jonas.mercado.mercado_api.repository;

import com.jonas.mercado.mercado_api.entity.Produto; // Importa a entidade que criamos antes
import org.springframework.data.jpa.repository.JpaRepository; // Importa o "motor" do Spring Data
import java.util.Optional; // Importa um container que ajuda a evitar erros de "valor nulo"

// A interface estende JpaRepository. 
// <Produto, Long> significa: Vou gerenciar a entidade Produto e a chave primária dela é do tipo Long.
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Método customizado: O Spring "lê" o nome do método e cria o SQL automaticamente!
    Optional<Produto> findByCodigoBarras(String codigoBarras);
}