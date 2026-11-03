package br.com.api.projetodomingos.repository;

import br.com.api.projetodomingos.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicosRepository extends JpaRepository<Topico, Long> {
    List<Topico> findByCursoNome(String nome);

    @Query("select t from Topico t where t.curso.nome = :nomeCurso")
    List<Topico> carregarPorNomeCurso(@Param("nomeCurso") String nomeCurso);
}
