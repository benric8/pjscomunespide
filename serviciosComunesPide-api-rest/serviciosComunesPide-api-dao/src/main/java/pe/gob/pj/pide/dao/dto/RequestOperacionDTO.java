package pe.gob.pj.pide.dao.dto;

import java.io.Serializable;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;

@Data
public class RequestOperacionDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	@NotNull(message = "El nombre no puede ser null")
	@Length(min=0, max=300, message = "El nombre tiene una longitud no permitida [min=0,max=300]")
	@NotBlank(message = "El nombre tiene un valor incorrecto")
	@JsonProperty("nombre")
	private String nombre;
	
	@NotNull(message = "la operacion no puede ser null")
	@Length(min=0, max=300, message = "la operacion  tiene una longitud no permitida [min=0,max=300]")
	@NotBlank(message = "La oprecion tiene un valor incorrecto")
	@JsonProperty("operacion")
	private String operacion;
	
	@NotNull(message = "la descripcion no puede ser null")
	@Length(min=0, max=300, message = "la descripcion  tiene una longitud no permitida [min=0,max=300]")
	@NotBlank(message = "La descripcion tiene un valor incorrecto")
	@JsonProperty("descripcion")
	private String descripcion;
	
	@Pattern(regexp = ConstantesSCPide.PATTERN_ENDPOINT, message="El endpoint no tiene el formato adecuado")
	@NotNull(message = "El endpoint no puede ser null")
	@Length(min=0, max=300, message = "El endpoint tiene una longitud no permitida [min=0,max=300]")
	@NotBlank(message = "El endpoint tiene un valor incorrecto")
	@JsonProperty("endpoint")
	private String endpoint;
	
	@Min(value = 20, message = "La cuotaDefecto no puede ser menor a 20.")
	@NotNull(message = "La cuotaDefecto no puede ser null")
	@JsonProperty("cuotaDefecto")
	private Integer cuotaDefecto;

	@Length(min = 1, max = 1, message = "El requiereAprobacionAcceso tiene una longitud no permitida [permitido=1]")
	@NotNull(message = "El requiereAprobacionAcceso no puede ser null")
	@NotBlank(message = "El requiereAprobacionAcceso tiene un valor incorrecto")
	@JsonProperty("requiereAprobacionAcceso")
	private String requiereAprobacionAcceso;
	
	@Length(min = 1, max = 1, message = "El requiereAprobacionCuota tiene una longitud no permitida [permitido=1]")
	@NotNull(message = "El requiereAprobacionCuota no puede ser null")
	@NotBlank(message = "El requiereAprobacionCuota tiene un valor incorrecto")
	@JsonProperty("requiereAprobacionCuota")
	private String requiereAprobacionCuota;
	
	@Length(min = 1, max = 1, message = "El requiereAprobacionIps tiene una longitud no permitida [permitido=1]")
	@NotNull(message = "El requiereAprobacionIps no puede ser null")
	@NotBlank(message = "El requiereAprobacionIps tiene un valor incorrecto")
	@JsonProperty("requiereAprobacionIps")
	private String requiereAprobacionIps;
	
	@Length(min = 1, max = 1, message = "El requiereAprobacionEstado tiene una longitud no permitida [permitido=1]")
	@NotNull(message = "El requiereAprobacionEstado no puede ser null")
	@NotBlank(message = "El requiereAprobacionEstado tiene un valor incorrecto")
	@JsonProperty("requiereAprobacionEstado")
	private String requiereAprobacionEstado;
	
	@Length(min = 1, max = 1, message = "El activo tiene una longitud no permitida [permitido=1]")
	@NotNull(message = "El activo no puede ser null")
	@NotBlank(message = "El activo tiene un valor incorrecto")
	@JsonProperty("activo")
	private String activo;
	
	

}
