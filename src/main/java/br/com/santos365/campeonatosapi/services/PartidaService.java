package br.com.santos365.campeonatosapi.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.santos365.campeonatosapi.DTO.PartidaDTO;
import br.com.santos365.campeonatosapi.DTO.PartidaGoogleDTO;
import br.com.santos365.campeonatosapi.DTO.PartidaResponseDTO;
import br.com.santos365.campeonatosapi.entity.Partida;
import br.com.santos365.campeonatosapi.exception.BadRequestException;
import br.com.santos365.campeonatosapi.exception.NotFoundException;
import br.com.santos365.campeonatosapi.repository.PartidaRepository;

@Service
public class PartidaService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PartidaRepository partidaRepository;
	
	@Autowired
	private EquipeService equipeService;

	public Partida buscarPartidaId(Long id) {
		return partidaRepository.findById(id).
				orElseThrow(() -> new NotFoundException("Nenhuma pardida encontrada com o id informado: " + id));
	}

	public PartidaResponseDTO buscarPartidas() {
		PartidaResponseDTO dto = new PartidaResponseDTO();
		dto.setPartidas(partidaRepository.findAll());
		
		return dto;
	}

	public Partida inserirPartida( PartidaDTO dto) {
		Partida partida = modelMapper.map(dto, Partida.class);
		partida.setEquipeCasa(equipeService.buscarEquipePorNome(dto.getNomeEquipeCasa()));
		partida.setEquipeVisitante(equipeService.buscarEquipePorNome(dto.getNomeEquipeVisitante()));
		
		return salvarPartida(partida);
	}
	
	private Partida salvarPartida(Partida partida) {
		return partidaRepository.save(partida);
	}

	public void alterarPartidas(Long id, PartidaDTO dto) {
		boolean exists = partidaRepository.existsById(id);
		if(!exists) {
			throw new BadRequestException("Não foi possível alterar a partida: ID inexistente");
		}
		
		Partida partida = buscarPartidaId(id);
		partida.setEquipeCasa(equipeService.buscarEquipePorNome(dto.getNomeEquipeCasa()));
		partida.setEquipeVisitante(equipeService.buscarEquipePorNome(dto.getNomeEquipeVisitante()));
		partida.setDataHoraPartida(dto.getDataHoraPartida());
		partida.setLocalPartida(dto.getLocalPartida());
		
		salvarPartida(partida);
	}

	public void atualizaPartida(Partida partida, PartidaGoogleDTO partidaGoogleDTO) {
		partida.setStatusPartida(partidaGoogleDTO.getStatusPartdida().toString());
		partida.setPlacarEquipeCasa(partidaGoogleDTO.getPlacarEquipeCasa());
		partida.setPlacarEquipeVisitante(partidaGoogleDTO.getPlacarEquipeVisitante());
		partida.setGolsEquipeCasa(partidaGoogleDTO.getGolsEquipeCasa());
		partida.setGolsEquipeVisitante(partidaGoogleDTO.getGolsEquipeVisitante());
		partida.setPlacarEstendidoEquipeCasa(partidaGoogleDTO.getPlacarEstendidoEquipeCasa());
		partida.setPlacarEstendidoEquipeVisitante(partidaGoogleDTO.getPlacarEstendidoEquipeVisitante());
		partida.setTempoPartida(partidaGoogleDTO.getTempoPartdida());
		
		salvarPartida(partida);
	}

	public List<Partida> buscarPartidasPeriodos() {
		return partidaRepository.buscarPartidasPeriodos();
	}

	public Integer buscarQuantidadesPartidas() {
		return partidaRepository.buscarQuantidadesPartidas();
	}



}
