package com.jonas.mercado.mercado_api.repository;
import com.jonas.mercado.mercado_api.entity.CaixaOperacao;
import com.jonas.mercado.mercado_api.entity.CaixaOperacao.StatusCaixa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CaixaOperacaoRepository extends JpaRepository<CaixaOperacao, Long> {
    Optional<CaixaOperacao> findFirstByStatusOrderByAberturaDesc(StatusCaixa status);
    List<CaixaOperacao> findAllByOrderByAberturaDesc();
}
