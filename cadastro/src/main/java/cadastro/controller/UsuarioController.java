package cadastro.controller;

import cadastro.dto.UsuarioDTO;
import cadastro.dto.UsuarioRespostaDTO;
import cadastro.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public UsuarioRespostaDTO salvar(@RequestBody @Valid UsuarioDTO dto) {
        return service.salvar(dto);
    }

    @PutMapping("/{id}")
    public UsuarioRespostaDTO atualizar(
            @PathVariable Long id,
            @RequestBody @Valid UsuarioDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Long id) {
        return service.deletar(id);
    }

    @GetMapping("/email")
    public UsuarioRespostaDTO buscar(@RequestParam String email) {
        return service.buscarPorEmail(email);
    }

    @GetMapping("/paginado")
    public Page<UsuarioRespostaDTO> listarPaginado(
            @RequestParam int page,
            @RequestParam int size) {
        return service.listarPaginado(page, size);
    }

    @GetMapping
    public List<UsuarioRespostaDTO> listar(HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        System.out.println("Usuário autenticado: " + email);
        return service.listar();
    }
}
