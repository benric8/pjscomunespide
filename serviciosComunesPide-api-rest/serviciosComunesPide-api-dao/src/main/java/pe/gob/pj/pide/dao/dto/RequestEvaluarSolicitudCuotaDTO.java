package pe.gob.pj.pide.dao.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RequestEvaluarSolicitudCuotaDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Min(value = 1, message = "El idSolicitud no puede ser menor a 1.")
	@NotNull(message = "El idSolicitud no puede ser null")
	@JsonProperty("idSolicitud")
	private Integer idSolicitud;
	
	@NotNull(message = "El usuarioAprobo no puede ser null")
	@NotBlank(message = "El usuarioAprobo tiene un valor incorrecto")
	@JsonProperty("usuarioAprobo")
	private String usuarioAprobo;
	
	@Min(value = 1, message = "El idEstadoSolicitud no puede ser menor a 1.")
	@NotNull(message = "El idEstadoSolicitud no puede ser null")
	@JsonProperty("idEstadoSolicitud")
	private int idEstadoSolicitud;

}
