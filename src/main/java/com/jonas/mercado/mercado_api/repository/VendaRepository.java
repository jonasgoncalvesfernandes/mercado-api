package com.jonas.mercado.mercado_api.repository;

import com.jonas.mercado.mercado_api.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}
