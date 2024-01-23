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
public class RequestValidarRucDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Pattern(regexp = ConstantesSCPide.PATTERN_NUMBER, message = "El ruc solo permite caracteres numericos.")
	@Length(min=11, max=11, message = "El ruc tiene una longitud no permitida [min=11,max=11].")
	@NotNull(message = "El ruc no puede ser null.")
	@NotBlank(message = "El ruc no puede estar vacio.")
	@JsonProperty("ruc")
	private String ruc;

}
