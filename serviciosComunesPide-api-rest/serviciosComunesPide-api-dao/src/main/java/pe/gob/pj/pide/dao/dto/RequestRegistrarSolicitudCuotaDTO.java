package pe.gob.pj.pide.dao.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import pe.gob.pj.pide.dao.dto.pide.IpEntidadDTO;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;

@Data
public class RequestRegistrarSolicitudCuotaDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Min(value = 1, message = "El idEntidad no puede ser menor a 1.")
	@NotNull(message = "El idEntidad no puede ser null")
	@JsonProperty("idEntidad")
	private Integer idEntidad;

	@Min(value = 0, message = "El idOperacion no puede ser menor a 1.")
	@NotNull(message = "El idOperacion no puede ser null")
	@JsonProperty("idOperacion")
	private Integer idOperacion;
	
	@NotNull(message = "El usuarioRegistro no puede ser null")
	@NotBlank(message = "El usuarioRegistro tiene un valor incorrecto")
	@JsonProperty("usuarioRegistro")
	private String usuarioRegistro;
	
	@NotNull(message = "El justificacion no puede ser null")
	@Length(min=0, max=300, message = "El justificacion tiene una longitud no permitida [min=0,max=300]")
	@NotBlank(message = "El justificacion tiene un valor incorrecto")
	@JsonProperty("justificacion")
	private String justificacion;
	
	@Pattern(regexp = ConstantesSCPide.PATTERN_NUMBER, message = "El nroDocumentoSolicitante solo permite caracteres numericos.")
	@Length(min = 8, max = 8, message = "El nroDocumentoSolicitante tiene una longitud no permitida [min=8,max=8].")
	@NotNull(message = "El nroDocumentoSolicitante no puede ser null")
	@NotBlank(message = "El nroDocumentoSolicitante tiene un valor incorrecto")
	@JsonProperty("nroDocumentoSolicitante")
	private String nroDocumentoSolicitante;
	
	@NotNull(message = "El nombreSolicitante no puede ser null")
	@NotBlank(message = "El nombreSolicitante tiene un valor incorrecto")
	@JsonProperty("nombreSolicitante")
	private String nombreSolicitante;

	@Min(value = 1, message = "El idTiposSolicitud no puede ser menor a 1.")
	@NotNull(message = "El idTiposSolicitud no puede ser null")
	@JsonProperty("idTiposSolicitud")
	private int idTiposSolicitud;

	@JsonProperty("cuotaCambio")
	private Integer cuotaCambio;

	@JsonProperty("estadoCambio")
	private String estadoCambio;

	@JsonProperty("listaIps")
	private List<IpEntidadDTO> listaIps;

}
