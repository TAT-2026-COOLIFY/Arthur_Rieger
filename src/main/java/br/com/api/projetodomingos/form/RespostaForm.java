package br.com.api.projetodomingos.form;

public class RespostaForm {

    private String mensagem;
    private Long topicoId;
    private Long autorId;

    public RespostaForm(String mensagem, Long topicoId, Long autorId) {
        this.mensagem = mensagem;
        this.topicoId = topicoId;
        this.autorId = autorId;
    }

    public RespostaForm() {
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Long getTopicoId() {
        return topicoId;
    }

    public void setTopicoId(Long topicoId) {
        this.topicoId = topicoId;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }
}
