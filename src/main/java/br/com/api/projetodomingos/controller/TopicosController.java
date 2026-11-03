package br.com.api.projetodomingos.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import br.com.api.projetodomingos.dto.RespostaDto;
import br.com.api.projetodomingos.dto.TopicosDto;
import br.com.api.projetodomingos.form.RespostaForm;
import br.com.api.projetodomingos.model.Resposta;
import br.com.api.projetodomingos.model.Topico;
import br.com.api.projetodomingos.model.Usuario;
import br.com.api.projetodomingos.repository.RespostaRepository;
import br.com.api.projetodomingos.repository.TopicosRepository;
import br.com.api.projetodomingos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class TopicosController {

    @Autowired
    private TopicosRepository topicosRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @RequestMapping("/topicos")
    @ResponseBody
    public java.util.List<TopicosDto> lista(String nomeCurso) {
        if (nomeCurso == null) {
            List<Topico> topicos = topicosRepository.findAll();
            return TopicosDto.converter(topicos);
        } else {
            List<Topico> topicos = topicosRepository.findByCursoNome(nomeCurso);
            return TopicosDto.converter(topicos);
        }
    }

    @RequestMapping("/topicos/{idTopico}")
    @ResponseBody
    public TopicosDto busca(@PathVariable Long idTopico) {
        Topico topico = this.topicosRepository.findById(idTopico).orElseThrow(() -> new RuntimeException("Tópico com ID: " + idTopico + " não encontrado!"));
        return new TopicosDto(topico);
    }

    @PostMapping("/resposta")
    @ResponseBody
    public ResponseEntity<RespostaDto> responderTopico(@RequestBody RespostaForm form) {
        Topico topico = this.topicosRepository.findById(form.getTopicoId()).orElseThrow(() -> new RuntimeException("Topico com ID: " + form.getTopicoId() + " não encontrado!"));
        Usuario usuario = this.usuarioRepository.findById(form.getAutorId()).orElseThrow(() -> new RuntimeException("Usuário com ID: " + form.getAutorId() + " não encontrado!"));
        Resposta resposta = new Resposta(
                form.getMensagem(),
                LocalDateTime.now(),
                usuario,
                topico
        );
        this.respostaRepository.save(resposta);
        topico.getRespostas().add(this.respostaRepository.save(resposta));
        this.topicosRepository.save(topico);
        RespostaDto dto = new RespostaDto(resposta.getId(), resposta.getMensagem(), resposta.getDataCriacao(), resposta.getAutor().getNome());
        URI uri = URI.create("/resposta/" + resposta.getId());

        return ResponseEntity.created(uri).body(dto);
    }
}