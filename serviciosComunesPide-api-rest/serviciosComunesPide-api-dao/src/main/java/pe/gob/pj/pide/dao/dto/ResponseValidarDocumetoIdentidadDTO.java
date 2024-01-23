package pe.gob.pj.pide.dao.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ResponseValidarDocumetoIdentidadDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nroDocumento;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombres;
	
	private String respuestaReniec;

}
