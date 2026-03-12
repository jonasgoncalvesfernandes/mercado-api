package com.jonas.mercado.mercado_api.repository;
import com.jonas.mercado.mercado_api.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByCodigoBarras(String codigoBarras);
    Optional<Produto> findByCodigoBarrasAndIdNot(String codigoBarras, Long id);
    List<Produto> findByAtivoTrue();
}
