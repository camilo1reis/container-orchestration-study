package cadastro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.aspectj.bridge.IMessage;

public class UsuarioDTO {

    @NotBlank(message = "Nome é Obrigatório")
    private String nome;

    @Email(message = "Email inválido")
    @NotBlank(message = "Email é Obrigatório")
    private String email;

    @NotBlank(message ="senha é obrigatória")
    private String senha;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
