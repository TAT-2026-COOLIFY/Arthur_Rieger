package br.com.api.projetodomingos.controller;

import br.com.api.projetodomingos.dto.ContatoDto;
import br.com.api.projetodomingos.form.ContatoForm;
import br.com.api.projetodomingos.model.Contato;
import br.com.api.projetodomingos.repository.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contatos")
@CrossOrigin(origins = "*")
public class ContatoController {

    @Autowired
    private ContatoRepository contatoRepository;

    @GetMapping
    public List<ContatoDto> listarContatos(@RequestParam(required = false) String nome) {
        if (nome == null || nome.isEmpty()) {
            return ContatoDto.converter(contatoRepository.findAll());
        }
        return ContatoDto.converter(contatoRepository.findByNomeContainingIgnoreCase(nome));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContatoDto> buscarContato(@PathVariable Long id) {
        Optional<Contato> contato = contatoRepository.findById(id);
        if (contato.isPresent()) {
            return ResponseEntity.ok(new ContatoDto(contato.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ContatoDto> salvarContato(@RequestBody ContatoForm form) {
        Contato contato = new Contato(
                form.getNome(),
                form.getTelefone(),
                form.getEmail(),
                form.getAniversario(),
                form.getRedes(),
                form.getObservacoes(),
                form.getAvatar()
        );
        Contato salvo = contatoRepository.save(contato);
        URI uri = URI.create("/contatos/" + salvo.getId());
        return ResponseEntity.created(uri).body(new ContatoDto(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContatoDto> editarContato(@PathVariable Long id, @RequestBody ContatoForm form) {
        Optional<Contato> optional = contatoRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Contato contato = optional.get();
        contato.setNome(form.getNome());
        contato.setTelefone(form.getTelefone());
        contato.setEmail(form.getEmail());
        contato.setAniversario(form.getAniversario());
        contato.setRedes(form.getRedes());
        contato.setObservacoes(form.getObservacoes());
        contato.setAvatar(form.getAvatar());
        contatoRepository.save(contato);
        return ResponseEntity.ok(new ContatoDto(contato));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirContato(@PathVariable Long id) {
        Optional<Contato> contato = contatoRepository.findById(id);
        if (contato.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        contatoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
