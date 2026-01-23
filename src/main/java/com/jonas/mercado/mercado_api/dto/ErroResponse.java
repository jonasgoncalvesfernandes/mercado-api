package com.jonas.mercado.mercado_api.dto;

import java.time.LocalDateTime;

public class ErroResponse {

    private int status;
    private String mensagem;
    private LocalDateTime timestamp;
    
    public ErroResponse(int status, String mensagem, LocalDateTime timestamp) {
        this.status = status;
        this.mensagem = mensagem;
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
