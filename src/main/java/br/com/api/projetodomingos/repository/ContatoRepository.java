package br.com.api.projetodomingos.repository;

import br.com.api.projetodomingos.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContatoRepository extends JpaRepository<Contato, Long> {

    List<Contato> findByNomeContainingIgnoreCase(String nome);
}
