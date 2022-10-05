package br.com.santos365.campeonatosapi.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.santos365.campeonatosapi.DTO.PartidaGoogleDTO;

@Service
public class ScrapingUtil {

	private static final  Logger LOGGER = LoggerFactory.getLogger(ScrapingUtil.class);
	
	private static final String BASE_URL_GOOGLE = "https://www.google.com.br/search?q=";	
	private static final String COMPLEMENTO_URL_GOOGLE = "&hl=pt-BR";
	private static final String CASA = "casa";
	private static final String VISITANTE = "visitante";
	private static final String DIV_PENALIDADES = "div[class=imso_mh_s__psn-sc]";
	private static final String ITEN_GOL = "div[class=imso_gs__gs-r]";
	private static final String DIV_GOLS_EQUIPE_CASA = "div[class=imso_gs__tgs imso_gs__left-team]";
	private static final String DIV_GOLS_EQUIPE_VISITANTE = "div[class=imso_gs__tgs imso_gs__right-team]";
	private static final String DIV_PLACAR_EQUIPE_CASA = "div[class=imso_mh__l-tm-sc imso_mh__scr-it imso-light-font]";
	private static final String DIV_PLACAR_EQUIPE_VISITANTE = "div[class=imso_mh__r-tm-sc imso_mh__scr-it imso-light-font]";
	private static final String DIV_LOGO_EQUIPE_CASA = "div[class=imso_mh__first-tn-ed imso_mh__tnal-cont imso-tnol]";
	private static final String DIV_LOGO_EQUIPE_VISITANTE = "div[class=imso_mh__second-tn-ed imso_mh__tnal-cont imso-tnol]";
	private static final String IMG_EQUIPE = "img[class=imso_btl__mh-logo]";
	private static final String DIV_NOME_EQUIPE_CASA = "div[class=imso_mh__first-tn-ed imso_mh__tnal-cont imso-tnol]";
	private static final String DIV_NOME_EQUIPE_VISITANTE = "div[class=imso_mh__second-tn-ed imso_mh__tnal-cont imso-tnol]";
	private static final String DIV_TEMPO_PARTIDA = "div[class=imso_mh__lv-m-stts-cont]";
	private static final String SPAN_TEMPO_PARTIDA = "span[class=imso_mh__ft-mtch imso-medium-font imso_mh__ft-mtchc]";
	private static final String SPAN = "span";
	private static final String SRC = "src";
	private static final String PENALTIS = "Penâltis";
	private static final String MINUTOS = " Min";
	
	
	public PartidaGoogleDTO obtemInformacoesPartida(String url) {
		PartidaGoogleDTO partidaGoogleDTO = new PartidaGoogleDTO();

		Document document = null;
		
		try {
			document = Jsoup.connect(url).get();
			String title = document.title();
			
			LOGGER.info("Titulo da pagina: {}", title);
			StatusPartida statusPartida = obtemStatusPartida(document);
			partidaGoogleDTO.setStatusPartdida(statusPartida);
			LOGGER.info("Status partida: {}", statusPartida);
			
			if(statusPartida != StatusPartida.PARTIDA_NAO_INICIADA) {
			String tempoPartida = obtemTempoPartida(document);
			partidaGoogleDTO.setTempoPartdida(tempoPartida);
			LOGGER.info("Tempo partida: {}",tempoPartida);
			
			Integer placarEquipeCasa = recuperaPlacarEquipe(document, DIV_PLACAR_EQUIPE_CASA);
			partidaGoogleDTO.setPlacarEquipeCasa(placarEquipeCasa);
			LOGGER.info("Placar casa: {}",placarEquipeCasa);
			
			Integer placarEquipeVisitante = recuperaPlacarEquipe(document, DIV_PLACAR_EQUIPE_VISITANTE);
			partidaGoogleDTO.setPlacarEquipeVisitante(placarEquipeVisitante);
			LOGGER.info("Placar visitante: {}",placarEquipeVisitante);
			
			String golsPartidaEquipeCasa = recuperaGolsEquipe(document,DIV_GOLS_EQUIPE_CASA);
			partidaGoogleDTO.setGolsEquipeCasa(golsPartidaEquipeCasa);
			LOGGER.info("Autores de gols casa: {}",golsPartidaEquipeCasa);
			
			String golsPartidaEquipeVisitante = recuperaGolsEquipe(document, DIV_GOLS_EQUIPE_VISITANTE);
			partidaGoogleDTO.setGolsEquipeVisitante(golsPartidaEquipeVisitante);
			LOGGER.info("Autores de gols casa: {}",golsPartidaEquipeVisitante);
			
			Integer placarestendidoEquipeCasa = buscaPenalidades(document, CASA);
			partidaGoogleDTO.setPlacarEstendidoEquipeCasa(placarestendidoEquipeCasa);
			LOGGER.info("Placar estendido equipe casa: {}",placarestendidoEquipeCasa);
			
			Integer placarestendidoEquipeVisitante = buscaPenalidades(document, VISITANTE);
			partidaGoogleDTO.setPlacarEstendidoEquipeVisitante(placarestendidoEquipeVisitante);
			LOGGER.info("Placar estendido equipe visitante: {}",placarestendidoEquipeVisitante);
			
			}
			
			String nomeEquipeCasa = recuperaNomeEquipe(document, DIV_NOME_EQUIPE_CASA);
			partidaGoogleDTO.setNomeEquipeCasa(nomeEquipeCasa);
			LOGGER.info("Nome equipe casa: {}",nomeEquipeCasa);
			
			String nomeEquipeVisitante = recuperaNomeEquipe(document, DIV_NOME_EQUIPE_VISITANTE);
			partidaGoogleDTO.setNomeEquipeVisitante(nomeEquipeVisitante);
			LOGGER.info("Nome equipe visitante: {}",nomeEquipeVisitante);
			
			String urlLogoEquipeCasa = recuperaLogoEquipe(document, DIV_LOGO_EQUIPE_CASA);
			partidaGoogleDTO.setUrLogoEquipeCasa(urlLogoEquipeCasa);
			LOGGER.info("URL logo equipe casa: {}",urlLogoEquipeCasa);
			
			String urlLogoEquipeVisitante = recuperaLogoEquipe(document, DIV_LOGO_EQUIPE_VISITANTE);
			partidaGoogleDTO.setUrLogoEquipeVisitantea(urlLogoEquipeVisitante);
			LOGGER.info("URL logo equipe casa: {}",urlLogoEquipeVisitante);
			
			return partidaGoogleDTO;
			
		} catch (IOException e) {
			LOGGER.error("ERRO AO CONECTAR COM O GOOGLE -> {}", e.getMessage());
		}
		
		return null;
	}
	
	public StatusPartida obtemStatusPartida(Document document) {
		StatusPartida partida = StatusPartida.PARTIDA_NAO_INICIADA;

		boolean isTempoPartida = document.select(DIV_TEMPO_PARTIDA).isEmpty();

		if (!isTempoPartida) {
			String tempoPartida = document.select(DIV_TEMPO_PARTIDA).first().text();
			partida = StatusPartida.PARTIDA_EM_ANDAMENTO;
			if (tempoPartida.contains(PENALTIS)) {
				partida = StatusPartida.PARTIDA_PENALTIS;
			}
		}

		isTempoPartida = document.select(SPAN_TEMPO_PARTIDA).isEmpty();
		if (!isTempoPartida) {
			partida = StatusPartida.PARTIDA_ENCERRADA;
		}
		return partida;

	}
	
	public String obtemTempoPartida(Document document) {
		String tempoPartida = null;

		boolean isTempoPartida = document.select(DIV_TEMPO_PARTIDA).isEmpty();

		if (!isTempoPartida) {
			tempoPartida = document.select(DIV_TEMPO_PARTIDA).first().text();
		}

		isTempoPartida = document.select(SPAN_TEMPO_PARTIDA).isEmpty();
		if (!isTempoPartida) {
			tempoPartida = document.select(SPAN_TEMPO_PARTIDA).first()
					.text();
		}
		return corrigeTempoPartida(tempoPartida);
	}
	
	public String corrigeTempoPartida(String tempo) {
		
		if(tempo.contains("'")) {
			return tempo.replace("'", MINUTOS);
		}else {
			return tempo;
		}
	}
	
	public String recuperaNomeEquipe(Document document, String equipe) {
		Element element = document.selectFirst(equipe);
		String nomeEquipe = element.select(SPAN).text();
		
		return nomeEquipe;
	}
	
	public String recuperaLogoEquipe(Document document, String equipe) {
		Element element = document.selectFirst(equipe);
		String urlLogo = element.select(IMG_EQUIPE).attr(SRC);
		
		return urlLogo;
	}
	
	public Integer recuperaPlacarEquipe(Document document, String equipe) {
		String placarEquipe = document.selectFirst(equipe).text();

		return formataPlacarStringInteger(placarEquipe);
	}
	
	public String recuperaGolsEquipe(Document document, String equipe) {
		List<String> golsEquipe = new ArrayList<>();

		Elements elements = document.select(equipe).select(ITEN_GOL);

		for (Element e : elements) {
			String infoGol = e.select(ITEN_GOL).text();
			golsEquipe.add(infoGol);
		}

		return String.join(",", golsEquipe);
	}
	
	public Integer buscaPenalidades(Document document, String equipe) {
		boolean isPenalidades = document.select(DIV_PENALIDADES).isEmpty();

		if (!isPenalidades) {
			String penalidades = document.select(DIV_PENALIDADES).text();
			String penalidadesCompleta = penalidades.substring(0, 5).replace(" ", "");
			String[] divisao = penalidadesCompleta.split("-");

			return equipe.equals(CASA) ? formataPlacarStringInteger(divisao[0])
					: formataPlacarStringInteger(divisao[1]);

		}

		return null;
	}
	
	public Integer formataPlacarStringInteger(String placar) {
		Integer valor;
		try {
			valor = Integer.parseInt(placar);
		} catch (Exception e) {
			valor = 0;
		}

		return valor;
	}
	
	public String montaUrlGoogle(String nomeEquipeCasa, String nomeEquipeVisitante, String data) {
		try {
			String equipeCasa = nomeEquipeCasa.replace(" ", "+").replace("-", "+");
			String equipeVisitante = nomeEquipeVisitante.replace(" ", "+").replace("-", "+");
			
			return BASE_URL_GOOGLE + equipeCasa + "+x+" + equipeVisitante + "+" + data + COMPLEMENTO_URL_GOOGLE;
			
		}catch (Exception e ) {
			LOGGER.error("ERRO: {}", e.getMessage());
		}
		
		return null;
	}
}
