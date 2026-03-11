package com.jonas.mercado.mercado_api.exception;

public class CodigoDeBarrasExistente extends RuntimeException {

    public CodigoDeBarrasExistente(String codigoBarras) {
        super("Já existe produto com esse código de barras: " + codigoBarras);
    }
    
}
