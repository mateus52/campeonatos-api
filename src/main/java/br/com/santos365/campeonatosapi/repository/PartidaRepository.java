package br.com.santos365.campeonatosapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.santos365.campeonatosapi.entity.Partida;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long>{

	@Query(name = "buscar_partidas_periodo",
			value = "select * from partida as p "
					+ "where p.data_hora_partida between dateadd(hour, -3, current_timestamp) and current_timestamp "
					+ "and ifnull(p.tempo_partida, 'Vazio') != 'Encerrado' ",
			nativeQuery = true)
	public List<Partida> buscarPartidasPeriodos();
	
	@Query(name = "buscar_quantidade_partida_periodo",
			value = "select count(*) from partida as p "
					+ "where p.data_hora_partida between dateadd(hour, -3, current_timestamp) and current_timestamp "
					+ "and ifnull(p.tempo_partida, 'Vazio') != 'Encerrado' ",
			nativeQuery = true)
	public Integer buscarQuantidadesPartidas();
	
	


}
