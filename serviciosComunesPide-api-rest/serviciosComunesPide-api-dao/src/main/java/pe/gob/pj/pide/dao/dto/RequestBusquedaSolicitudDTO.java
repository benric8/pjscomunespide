package pe.gob.pj.pide.dao.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RequestBusquedaSolicitudDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("idSolicitud")
	private int idSolicitud;

	@JsonProperty("idEntidad")
	private int idEntidad;
	
	@JsonProperty("idOperacion")
	private int idOperacion;
	
	@NotNull(message = "La fecha desde no puede ser null")
	@JsonProperty("fechaDesde")
	private Date fechaDesde;
	
	@NotNull(message = "La fecha desde no puede ser null")
	@JsonProperty("fechaHasta")
	private Date fechaHasta;
	
	@JsonProperty("idEstadoSolicitud")
	private int idEstadoSolicitud;

}
