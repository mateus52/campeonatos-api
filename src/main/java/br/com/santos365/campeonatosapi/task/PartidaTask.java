package br.com.santos365.campeonatosapi.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import br.com.santos365.campeonatosapi.services.ScrapingService;
import br.com.santos365.campeonatosapi.util.DataUtil;

@Configuration
@EnableScheduling
public class PartidaTask {

	private static final  Logger LOGGER = LoggerFactory.getLogger(PartidaTask.class);
	private static final String TIME_ZONE = "America/Sao_Paulo";
	private static final String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
	
	@Autowired
	private ScrapingService scrapingService;
	
	@Scheduled(cron = "0/30 * 19-23 * * WED", zone = TIME_ZONE)
	public void taskPartidaQuartaFeira() {
		inicializaAgendamento("taskPartidaQuartaFeira()");
	}
	
	@Scheduled(cron = "0/60 * 19-23 * * TUE", zone = TIME_ZONE)
	public void taskPartidaTercaFeira() {
		inicializaAgendamento("taskPartidaTercaFeira()");
	}
	
	@Scheduled(cron = "0/30 * 19-23 * * THU", zone = TIME_ZONE)
	public void taskPartidaQuintaFeira() {
		inicializaAgendamento("taskPartidaQuintaFeira()");
	}
	
	@Scheduled(cron = "0/30 * 16-23 * * SAT", zone = TIME_ZONE)
	public void taskPartidaSabado() {
		inicializaAgendamento("taskPartidaSabado()");
	}
	
	@Scheduled(cron = "0/30 * 16-23 * * SUN", zone = TIME_ZONE)
	public void taskPartidaDomingoTarde() {
		inicializaAgendamento("taskPartidaDomingoTarde()");
	}
	
	@Scheduled(cron = "0/30 * 11-13 * * SUN", zone = TIME_ZONE)
	public void taskPartidaDomingoManha() {
		inicializaAgendamento("taskPartidaDomingoManha()");
	}
	
	@Scheduled(cron = "0/30 * 19-23 * * MON", zone = TIME_ZONE)
	public void taskPartidaSegundaFeira() {
		inicializaAgendamento("taskPartidaSegundaFeira()");
	}
	
	@Scheduled(cron = "0/30 * 19-23 * * FRI", zone = TIME_ZONE)
	public void taskPartidaSextaFeira() {
		inicializaAgendamento("taskPartidaSextaFeira()");
	}
	
	private void inicializaAgendamento(String diaSemana) {
		this.graLogInfo(String.format("%s: %s", diaSemana, DataUtil.formataDateEmString(new Date(), DD_MM_YYYY_HH_MM_SS)));
		
		scrapingService.verificarPartidaPeriodo();
	}
	
	private void graLogInfo(String mensagem) {
		LOGGER.info(mensagem);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
