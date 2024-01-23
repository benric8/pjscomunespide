package pe.gob.pj.pide.dao.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RequestRegistrarIpEntidadDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Min(value = 1, message = "El idEntidad no puede ser menor a 1.")
	@NotNull(message = "El idEntidad no puede ser null")
	@JsonProperty("idEntidad")
	private Integer idEntidad;

	@Length(min = 9, max =15, message = "El ipPublica tiene una longitud no permitida [min=9,max=15].")
	@NotNull(message = "El ipPublica no puede ser null")
	@NotBlank(message = "El ipPublica tiene un valor incorrecto")
	@JsonProperty("ipPublica")
	private String ipPublica;

	@Length(min = 1, max =1, message = "El operador tiene una longitud no permitida [min=1,max=1].")
	@NotNull(message = "El operador no puede ser null")
	@NotBlank(message = "El operador tiene un valor incorrecto")
	@JsonProperty("operador")
	private String operador;

	@Length(min = 1, max = 1, message = "El activo tiene una longitud no permitida [permitido=1]")
	@NotNull(message = "El activo no puede ser null")
	@NotBlank(message = "El activo tiene un valor incorrecto")
	@JsonProperty("activo")
	private String activo;

}
