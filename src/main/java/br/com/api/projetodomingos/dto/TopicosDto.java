package br.com.api.projetodomingos.dto;

import br.com.api.projetodomingos.model.Resposta;
import br.com.api.projetodomingos.model.StatusTopico;
import br.com.api.projetodomingos.model.Topico;

import java.time.LocalDateTime;
import java.util.List;

public class TopicosDto {
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private StatusTopico status;
    private String autor;
    private List<RespostaDto> respostas;

    public TopicosDto(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
        this.status = topico.getStatus();
        this.autor = topico.getAutor().getNome();
        this.respostas = RespostaDto.convert(topico.getRespostas());
    }

    public static List<TopicosDto> converter(List<Topico> topicos) {
        return topicos.stream().map(TopicosDto::new).toList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public StatusTopico getStatus() {
        return status;
    }

    public void setStatus(StatusTopico status) {
        this.status = status;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public List<RespostaDto> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<RespostaDto> respostas) {
        this.respostas = respostas;
    }
}
