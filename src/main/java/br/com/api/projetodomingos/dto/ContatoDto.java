package br.com.api.projetodomingos.dto;

import br.com.api.projetodomingos.model.Contato;

import java.util.List;

public class ContatoDto {

    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private String aniversario;
    private String redes;
    private String observacoes;
    private String avatar;

    public ContatoDto(Contato contato) {
        this.id = contato.getId();
        this.nome = contato.getNome();
        this.telefone = contato.getTelefone();
        this.email = contato.getEmail();
        this.aniversario = contato.getAniversario();
        this.redes = contato.getRedes();
        this.observacoes = contato.getObservacoes();
        this.avatar = contato.getAvatar();
    }

    public static List<ContatoDto> converter(List<Contato> contatos) {
        return contatos.stream().map(ContatoDto::new).toList();
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }
    public String getAniversario() { return aniversario; }
    public String getRedes() { return redes; }
    public String getObservacoes() { return observacoes; }
    public String getAvatar() { return avatar; }
}
