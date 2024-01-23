package pe.gob.pj.pide.dao.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RequestModificarPermisoDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Min(value = 1, message = "El idEntidad no puede ser menor a 1.")
	@NotNull(message = "El idEntidad no puede ser null")
	@JsonProperty("idEntidad")
	private Integer idEntidad;

	@Min(value = 1, message = "El idOperacion no puede ser menor a 1.")
	@NotNull(message = "El idOperacion no puede ser null")
	@JsonProperty("idOperacion")
	private Integer idOperacion;
	
	@Length(min = 1, max = 1, message = "El activo tiene una longitud no permitida [permitido=1]")
	@NotNull(message = "El activo no puede ser null")
	@NotBlank(message = "El activo tiene un valor incorrecto")
	@JsonProperty("activo")
	private String activo;

}
