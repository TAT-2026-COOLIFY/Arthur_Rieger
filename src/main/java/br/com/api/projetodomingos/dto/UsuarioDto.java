package br.com.api.projetodomingos.dto;

import br.com.api.projetodomingos.model.Usuario;
import br.com.api.projetodomingos.repository.UsuarioRepository;

import java.util.List;

public class UsuarioDto {

    private Long id;
    private String nome;
    private String email;

    public static List<UsuarioDto> converter(List<Usuario> usuarios) {
        return usuarios.stream().map(UsuarioDto::new).toList();
    }

    public UsuarioDto(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
    }

    public UsuarioDto() {
    }

    public UsuarioDto(String nome, String email) {
        this.nome = nome;
        this.email = email;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
