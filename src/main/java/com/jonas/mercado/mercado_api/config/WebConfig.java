package com.jonas.mercado.mercado_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração global de CORS.
 *
 * PROBLEMA ANTERIOR: @CrossOrigin estava apenas no ProdutoController.
 * Isso fazia o VendaController retornar erro 403/CORS bloqueado no navegador
 * para todas as chamadas de vendas (POST /vendas, GET /vendas/periodo, etc.)
 *
 * SOLUÇÃO: Configuração global que cobre TODOS os controllers de uma vez.
 * Ao adicionar novos controllers no futuro, eles automaticamente herdam o CORS.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // Frontend React rodando em desenvolvimento (Vite)
                .allowedOrigins(
                    "http://localhost:5173",
                    "http://localhost:3000",
                    "http://127.0.0.1:5173"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600); // Cache do preflight por 1 hora
    }
}
