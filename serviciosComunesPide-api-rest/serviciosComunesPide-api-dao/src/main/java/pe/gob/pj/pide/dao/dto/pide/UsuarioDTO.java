package pe.gob.pj.pide.dao.dto.pide;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int idUsuario;
	
	@NotNull(message = "El usuario no puede ser null")
	@NotBlank(message = "El usuario tiene un valor incorrecto")
	@JsonProperty("usuario")
	private String usuario;
	
	@NotNull(message = "El activo no puede ser null")
	@NotBlank(message = "El activo tiene un valor incorrecto")
	@JsonProperty("activo")
	private String activo;
	
	@NotNull(message = "El apellidosNombres no puede ser null")
	@NotBlank(message = "El apellidosNombres tiene un valor incorrecto")
	@JsonProperty("apellidosNombres")
	private String apellidosNombres;

	@NotNull(message = "El idPerfil no puede ser null")
	@JsonProperty("idPerfil")
	private int idPerfil;

	@JsonProperty("clave")
	private String clave;
	
	private String codigoRol;
	private String fechaCambioClave;
	private String fechaRegistro;
	private String nombrePerfil;
	private String descripcionPerfil;
	
	private String token;
	
}