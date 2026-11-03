package br.com.api.projetodomingos.form;

public class ContatoForm {

    private String nome;
    private String telefone;
    private String email;
    private String aniversario;
    private String redes;
    private String observacoes;
    private String avatar;

    public ContatoForm() {}

    public ContatoForm(String nome, String telefone, String email, String aniversario, String redes, String observacoes, String avatar) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.aniversario = aniversario;
        this.redes = redes;
        this.observacoes = observacoes;
        this.avatar = avatar;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAniversario() { return aniversario; }
    public void setAniversario(String aniversario) { this.aniversario = aniversario; }

    public String getRedes() { return redes; }
    public void setRedes(String redes) { this.redes = redes; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
}
