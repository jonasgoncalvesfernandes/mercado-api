package com.jonas.mercado.mercado_api.repository;
import com.jonas.mercado.mercado_api.entity.EntradaEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EntradaEstoqueRepository extends JpaRepository<EntradaEstoque, Long> {
    List<EntradaEstoque> findByProdutoIdOrderByDataHoraDesc(Long produtoId);
    List<EntradaEstoque> findAllByOrderByDataHoraDesc();
}
