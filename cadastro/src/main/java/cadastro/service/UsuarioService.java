package cadastro.service;

import cadastro.dto.UsuarioDTO;
import cadastro.dto.UsuarioRespostaDTO;
import cadastro.model.Usuario;
import cadastro.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder encoder;

    public UsuarioService(UsuarioRepository repository,
                          BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public UsuarioRespostaDTO buscarPorEmail(String email) {
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return new UsuarioRespostaDTO(usuario);
    }

    public List<UsuarioRespostaDTO> listar() {
        return repository.findAll()
                .stream()
                .map(UsuarioRespostaDTO::new)
                .toList();
    }

    public UsuarioRespostaDTO salvar(UsuarioDTO dto) {
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(encoder.encode(dto.getSenha()));

        return new UsuarioRespostaDTO(repository.save(usuario));
    }

    public UsuarioRespostaDTO atualizar(Long id, UsuarioDTO dto) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado com id: " + id));

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(encoder.encode(dto.getSenha()));
        }

        return new UsuarioRespostaDTO(repository.save(usuario));
    }

    public String deletar(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado com id: " + id));

        repository.delete(usuario);
        return "Usuário deletado com sucesso!";
    }

    public Page<UsuarioRespostaDTO> listarPaginado(int page, int size) {
        return repository.findAll(PageRequest.of(page, size))
                .map(UsuarioRespostaDTO::new);
    }
}
