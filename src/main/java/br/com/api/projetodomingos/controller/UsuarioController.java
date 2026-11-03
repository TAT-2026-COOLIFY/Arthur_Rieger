package br.com.api.projetodomingos.controller;

import br.com.api.projetodomingos.dto.UsuarioDto;
import br.com.api.projetodomingos.form.UsuarioForm;
import br.com.api.projetodomingos.model.Usuario;
import br.com.api.projetodomingos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/usuarios")
    @ResponseBody
    public List<UsuarioDto> listarUsuarios(String nome) {
        if (nome == null) {
            return UsuarioDto.converter(this.usuarioRepository.findAll());
        } else {
            return UsuarioDto.converter(this.usuarioRepository.findByNome(nome));
        }
    }

    @PostMapping("/usuarios")
    @ResponseBody
    public ResponseEntity<UsuarioDto> registrarUsuario(@RequestBody UsuarioForm form) {
        if (this.usuarioRepository.findByEmail(form.getEmail()).isEmpty()) {
            UsuarioDto dto = new UsuarioDto(this.usuarioRepository.save(new Usuario(form.getNome(), form.getEmail(), form.getSenha())));
            URI uri = URI.create("/usuarios/" + dto.getId());
            return ResponseEntity.created(uri).body(dto);
        } else throw new RuntimeException("Usuário com email: " + form.getEmail() + " já cadastrado!");
    }
}
