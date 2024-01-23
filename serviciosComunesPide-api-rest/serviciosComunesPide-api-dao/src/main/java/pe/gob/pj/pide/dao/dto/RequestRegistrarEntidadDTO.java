package pe.gob.pj.pide.dao.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RequestRegistrarEntidadDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Length(min = 11, max = 11, message = "El nroRuc tiene una longitud no permitida [permitido=11]")
	@NotNull(message = "El nroRuc no puede ser null")
	@NotBlank(message = "El nroRuc tiene un valor incorrecto")
	@JsonProperty("nroRuc")
	private String nroRuc;

	@Length(min = 10, max = 300, message = "El razonSocial tiene una longitud no permitida [min=10,max=300]")
	@NotNull(message = "El razonSocial no puede ser null")
	@NotBlank(message = "El razonSocial tiene un valor incorrecto")
	@JsonProperty("razonSocial")
	private String razonSocial;

	@Length(min = 1, max = 1, message = "El activo tiene una longitud no permitida [permitido=1]")
	@NotNull(message = "El activo no puede ser null")
	@NotBlank(message = "El activo tiene un valor incorrecto")
	@JsonProperty("activo")
	private String activo;

}
