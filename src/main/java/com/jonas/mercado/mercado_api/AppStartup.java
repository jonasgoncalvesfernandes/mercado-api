package com.jonas.mercado.mercado_api;
import com.jonas.mercado.mercado_api.service.AuthService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartup implements ApplicationRunner {
    private final AuthService authService;
    public AppStartup(AuthService authService) { this.authService = authService; }

    @Override
    public void run(ApplicationArguments args) {
        authService.criarAdminPadraoSeNecessario();
    }
}
