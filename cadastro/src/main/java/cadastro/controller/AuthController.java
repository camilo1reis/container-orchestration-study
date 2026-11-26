package cadastro.controller;

import cadastro.model.Usuario;
import cadastro.repository.UsuarioRepository;
import cadastro.service.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder encoder;

    public AuthController(JwtService jwtService,
                          UsuarioRepository repository,
                          BCryptPasswordEncoder encoder) {
        this.jwtService = jwtService;
        this.repository = repository;
        this.encoder = encoder;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String email,
                                     @RequestParam String senha) {

        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!encoder.matches(senha, usuario.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = jwtService.gerarToken(email);

        Map<String, String> resposta = new HashMap<>();
        resposta.put("token", token);

        return resposta;
    }
}
