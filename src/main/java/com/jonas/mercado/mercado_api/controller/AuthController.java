package com.jonas.mercado.mercado_api.controller;
import com.jonas.mercado.mercado_api.dto.LoginRequest;
import com.jonas.mercado.mercado_api.dto.LoginResponse;
import com.jonas.mercado.mercado_api.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:5173","http://localhost:3000","http://127.0.0.1:5173"})
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }
}
