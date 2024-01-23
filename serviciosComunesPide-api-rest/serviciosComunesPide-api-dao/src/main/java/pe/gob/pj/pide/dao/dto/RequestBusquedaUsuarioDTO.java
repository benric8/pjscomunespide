package pe.gob.pj.pide.dao.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RequestBusquedaUsuarioDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("idPerfil")
	private int idPerfil;
	@JsonProperty("usuario")
	private String usuario;
	@JsonProperty("estado")
	private String estado;
	
}
