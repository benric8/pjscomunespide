package pe.gob.pj.pide.dao.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;

@Data
public class RequestValidarDocumetoIdentidadDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Length(min = 8, max = 8, message = "El número de documento de identidad tiene una longitud no permitida. [tam=8]")
	@Pattern(regexp = ConstantesSCPide.PATTERN_NUMBER, message = "El número de documento de identidad solo permite caracteres numericos.")
	@NotNull(message = "El número de documento de identidad no puede ser null")
	@NotBlank(message = "El número de documento de identidad tiene un valor incorrecto")
	@JsonProperty("nroDocumento")
	private String nroDocumento;

}
