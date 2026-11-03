package br.com.api.projetodomingos.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensagem;
    private LocalDateTime dataCriacao;
    @ManyToOne
    private Usuario autor;
    @ManyToOne
    private Topico topico;
    private boolean solucao;

    public Resposta() {}

    public Resposta(Long id, String mensagem, Usuario autor, Topico topico) {
        this.id = id;
        this.mensagem = mensagem;
        this.autor = autor;
        this.topico = topico;
        this.dataCriacao = LocalDateTime.now();
        this.solucao = false;
    }

    public Resposta(String mensagem, LocalDateTime dataCriacao, Usuario autor, Topico topico) {
        this.mensagem = mensagem;
        this.dataCriacao = dataCriacao;
        this.autor = autor;
        this.topico = topico;
        this.solucao = false;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public Usuario getAutor() { return autor; }
    public void setAutor(Usuario autor) { this.autor = autor; }

    public Topico getTopico() { return topico; }
    public void setTopico(Topico topico) { this.topico = topico; }

    public boolean isSolucao() { return solucao; }
    public void setSolucao(boolean solucao) { this.solucao = solucao; }
}