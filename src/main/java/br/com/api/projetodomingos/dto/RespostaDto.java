package br.com.api.projetodomingos.dto;

import br.com.api.projetodomingos.model.Resposta;

import java.time.LocalDateTime;
import java.util.List;

public class RespostaDto {
    private Long id;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String nomeAutor;

    public RespostaDto(Long id, String mensagem, LocalDateTime dataCriacao, String nomeAutor) {
        this.id = id;
        this.mensagem = mensagem;
        this.dataCriacao = dataCriacao;
        this.nomeAutor = nomeAutor;
    }

    public RespostaDto(Resposta resposta) {
        this.id = resposta.getId();
        this.mensagem = resposta.getMensagem();
        this.dataCriacao = resposta.getDataCriacao();
        this.nomeAutor = resposta.getAutor().getNome();
    }

    public static List<RespostaDto> convert(List<Resposta> respostas) {
        return respostas.stream().map(RespostaDto::new).toList();
    }

    public RespostaDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }
}
