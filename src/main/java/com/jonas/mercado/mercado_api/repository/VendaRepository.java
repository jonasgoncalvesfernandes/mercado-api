package com.jonas.mercado.mercado_api.repository;
import com.jonas.mercado.mercado_api.entity.Venda;
import com.jonas.mercado.mercado_api.entity.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Venda> findByCaixaOperacaoId(Long caixaId);

    @Query("SELECT COALESCE(SUM(v.valorTotal), 0) FROM Venda v WHERE v.caixaOperacao.id = :caixaId AND v.formaPagamento = :forma")
    BigDecimal sumByCaixaAndForma(@Param("caixaId") Long caixaId, @Param("forma") FormaPagamento forma);

    @Query("SELECT COUNT(v) FROM Venda v WHERE v.caixaOperacao.id = :caixaId")
    Long countByCaixa(@Param("caixaId") Long caixaId);
}
