package br.com.santos365.campeonatosapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.santos365.campeonatosapi.DTO.PartidaGoogleDTO;
import br.com.santos365.campeonatosapi.entity.Partida;
import br.com.santos365.campeonatosapi.util.DataUtil;
import br.com.santos365.campeonatosapi.util.ScrapingUtil;
import br.com.santos365.campeonatosapi.util.StatusPartida;

@Service
public class ScrapingService {

	@Autowired
	private ScrapingUtil scrapingUtil;
	
	@Autowired
	private PartidaService partidaService;
	
	
	public void verificarPartidaPeriodo() {
		Integer quantidadePartidas = partidaService.buscarQuantidadesPartidas();
		
		if(quantidadePartidas > 0) {
			List<Partida> partidas = partidaService.buscarPartidasPeriodos();
			
			for(Partida p : partidas) {
				String urlPartida = scrapingUtil.montaUrlGoogle(
						p.getEquipeCasa().getNomeEquipe(),
						p.getEquipeVisitante().getNomeEquipe(),
						DataUtil.formataDateEmString(p.getDataHoraPartida(), "dd/MM/yyyy"));
					
				PartidaGoogleDTO partidaGoogleDTO = scrapingUtil.obtemInformacoesPartida(urlPartida);
				
				if(partidaGoogleDTO.getStatusPartdida() != StatusPartida.PARTIDA_NAO_INICIADA) {
				partidaService.atualizaPartida(p, partidaGoogleDTO);
				}
			};
		}
	}
	
}
