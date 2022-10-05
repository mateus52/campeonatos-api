package br.com.santos365.campeonatosapi.services;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.santos365.campeonatosapi.DTO.EquipeDTO;
import br.com.santos365.campeonatosapi.DTO.EquipeResponseDTO;
import br.com.santos365.campeonatosapi.entity.Equipe;
import br.com.santos365.campeonatosapi.exception.BadRequestException;
import br.com.santos365.campeonatosapi.exception.NotFoundException;
import br.com.santos365.campeonatosapi.repository.EquipeRepository;

@Service
public class EquipeService {

	@Autowired
	private EquipeRepository equipeRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Equipe buscarEquipeId(Long id) {
		return equipeRepository.findById(id).
				orElseThrow(() -> new NotFoundException("Nenhuma equipe encontrada com o id informado: " + id));
	}
	
	public Equipe buscarEquipePorNome(String nome) {
		return equipeRepository.findByNomeEquipe(nome).
				orElseThrow(() -> new NotFoundException("Nenhuma equipe encontrada com o nome informado: " + nome));
	}

	public EquipeResponseDTO buscarEquipes() {
		EquipeResponseDTO dto = new EquipeResponseDTO();
		dto.setEquipes(equipeRepository.findAll());
		
		return dto;
	}

	public Equipe inserirEquipe(@Valid EquipeDTO dto) {
		boolean exists = equipeRepository.existsByNomeEquipe(dto.getNomeEquipe());
		if(exists) {
			throw new BadRequestException("Já existe uma equipe cadastrada com esse nome!");
		}
		
		Equipe equipe = modelMapper.map(dto, Equipe.class);
		
		return equipeRepository.save(equipe);
	}

	public void alterarEquipe(Long id, @Valid EquipeDTO dto) {
		boolean exists = equipeRepository.existsById(id);
		if(!exists) {
			throw new BadRequestException("Não foi possível alterar a equipe: ID inexistente");
		}
		
		Equipe equipe = modelMapper.map(dto, Equipe.class);
		equipe.setId(id);
		equipeRepository.save(equipe);
	}
	
	

}
