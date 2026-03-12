package com.jonas.mercado.mercado_api.service;

import com.jonas.mercado.mercado_api.dto.LoginRequest;
import com.jonas.mercado.mercado_api.dto.LoginResponse;
import com.jonas.mercado.mercado_api.entity.Usuario;
import com.jonas.mercado.mercado_api.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class AuthService {
    private static final String SECRET = "mercadopdv-secret-key-2025-jonas-super-segura-256bits!!";
    private static final long   EXP_MS = 12 * 60 * 60 * 1000L;
    private final UsuarioRepository usuarioRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UsuarioRepository usuarioRepo) { this.usuarioRepo = usuarioRepo; }

    public LoginResponse login(LoginRequest req) {
        Usuario u = usuarioRepo.findByLoginAndAtivoTrue(req.getLogin())
                .orElseThrow(() -> new RuntimeException("Usuário ou senha inválidos"));
        if (!encoder.matches(req.getSenha(), u.getSenha()))
            throw new RuntimeException("Usuário ou senha inválidos");
        return new LoginResponse(gerarToken(u), u.getNomeExibicao(), u.getPerfil().name(), u.getId());
    }

    public String gerarToken(Usuario u) {
        Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setSubject(u.getLogin())
                .claim("nome", u.getNomeExibicao())
                .claim("perfil", u.getPerfil().name())
                .claim("id", u.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXP_MS))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public void criarAdminPadraoSeNecessario() {
        if (usuarioRepo.count() == 0) {
            Usuario admin = new Usuario();
            admin.setLogin("admin");
            admin.setSenha(encoder.encode("admin123"));
            admin.setNomeExibicao("Administrador");
            admin.setPerfil(Usuario.PerfilUsuario.ADMIN);
            admin.setAtivo(true);
            usuarioRepo.save(admin);
            System.out.println("=== Usuário padrão criado: admin / admin123 ===");
        }
    }

    public String hashSenha(String senha) { return encoder.encode(senha); }
}
