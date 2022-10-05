package br.com.santos365.campeonatosapi.DTO;

import java.io.Serializable;

import br.com.santos365.campeonatosapi.util.StatusPartida;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartidaGoogleDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private StatusPartida statusPartdida;
	private String tempoPartdida;
	//equipe casa
	private String nomeEquipeCasa;
	private String urLogoEquipeCasa;
	private Integer placarEquipeCasa;
	private String golsEquipeCasa;
	private Integer placarEstendidoEquipeCasa;
	//equipe visitante
	private String nomeEquipeVisitante;
	private String urLogoEquipeVisitantea;
	private Integer placarEquipeVisitante;
	private String golsEquipeVisitante;
	private Integer placarEstendidoEquipeVisitante;
	

}
