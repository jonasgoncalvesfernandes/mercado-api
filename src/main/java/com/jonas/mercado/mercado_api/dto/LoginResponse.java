package com.jonas.mercado.mercado_api.dto;
public class LoginResponse {
    private String token;
    private String nomeExibicao;
    private String perfil;
    private Long   usuarioId;
    public LoginResponse(String token, String nomeExibicao, String perfil, Long usuarioId) {
        this.token = token; this.nomeExibicao = nomeExibicao;
        this.perfil = perfil; this.usuarioId = usuarioId;
    }
    public String getToken() { return token; }
    public String getNomeExibicao() { return nomeExibicao; }
    public String getPerfil() { return perfil; }
    public Long getUsuarioId() { return usuarioId; }
}
