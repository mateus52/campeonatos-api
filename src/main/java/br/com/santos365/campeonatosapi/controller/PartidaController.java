package br.com.santos365.campeonatosapi.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.santos365.campeonatosapi.DTO.PartidaDTO;
import br.com.santos365.campeonatosapi.DTO.PartidaResponseDTO;
import br.com.santos365.campeonatosapi.entity.Partida;
import br.com.santos365.campeonatosapi.exception.StandardError;
import br.com.santos365.campeonatosapi.services.PartidaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("API - Partidas")
@RestController
@RequestMapping("/api/v1/partidas")
public class PartidaController {
	
	@Autowired
	private PartidaService partidaService;
	
	@ApiOperation(value = "Buscar partida por id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = Partida.class),
			@ApiResponse(code = 400, message = "BAD REQUEST", response = StandardError.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZED", response = StandardError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = StandardError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = StandardError.class),
			@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = StandardError.class),
	})
	@GetMapping("/{id}")
	public ResponseEntity<Partida> buscarPartidaId(@PathVariable("id") Long id){
		
		return ResponseEntity.ok().body(partidaService.buscarPartidaId(id));
		
	}
	
	@ApiOperation(value = "Listar partidas")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = PartidaResponseDTO.class),
			@ApiResponse(code = 400, message = "BAD REQUEST", response = StandardError.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZED", response = StandardError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = StandardError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = StandardError.class),
			@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = StandardError.class),
	})
	@GetMapping
	public ResponseEntity<PartidaResponseDTO> listarPartidas(){
		
		return ResponseEntity.ok().body(partidaService.buscarPartidas());
	}
	
	@ApiOperation(value = "Inserir partidas")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "CREATED", response = Partida.class),
			@ApiResponse(code = 400, message = "BAD REQUEST", response = StandardError.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZED", response = StandardError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = StandardError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = StandardError.class),
			@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = StandardError.class),
	})
	@PostMapping
	public ResponseEntity<Partida> inserirEquipe(@Valid @RequestBody PartidaDTO dto) {
		Partida partida = partidaService.inserirPartida(dto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(partida.getId()).toUri();
		
		return ResponseEntity.created(location).body(partida);
		
	}
	
	@ApiOperation(value = "Alterar partidas")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "NO CONTENT", response = Void.class),
			@ApiResponse(code = 400, message = "BAD REQUEST", response = StandardError.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZED", response = StandardError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = StandardError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = StandardError.class),
			@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = StandardError.class),
	})
	@PutMapping("/{id}")
	public ResponseEntity<Void> alterarEquipe(@PathVariable("id") Long id, @Valid @RequestBody PartidaDTO dto){
		partidaService.alterarPartidas(id, dto);
		return ResponseEntity.noContent().build();
	}

}
