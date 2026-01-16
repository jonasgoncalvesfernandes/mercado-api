package com.jonas.mercado.mercado_api.repository;

import com.jonas.mercado.mercado_api.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDateTime;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    
    List<Venda> findByDataHoraBetween(
        LocalDateTime inicio,
        LocalDateTime fim
    );
}

