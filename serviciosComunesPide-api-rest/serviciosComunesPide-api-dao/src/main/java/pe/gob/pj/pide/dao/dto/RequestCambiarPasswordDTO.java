package pe.gob.pj.pide.dao.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RequestCambiarPasswordDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull(message = "El usuario no puede ser null")
	@NotBlank(message = "El usuario tiene un valor incorrecto")
	@JsonProperty("usuario")
	private String usuario;
	@NotNull(message = "La contraseña actual de usuario no puede ser null")
	@NotBlank(message = "La contraseña actual de usuario tiene un valor incorrecto")
	@JsonProperty("claveActual")
	private String claveActual;
	@Length(min = 6, max = 12, message = "La contraseña actual tiene una longitud no permitida. [min=6,max=12]")
	@NotNull(message = "La contraseña nueva de usuario no puede ser null")
	@NotBlank(message = "La contraseña nueva de usuario tiene un valor incorrecto")
	@JsonProperty("claveNueva")
	private String claveNueva;

}
