package pe.gob.pj.pide.dao.dto.pide;

import java.io.Serializable;

import lombok.Data;

@Data
public class PerfilDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idPerfil;
	private String nombrePerfil;
	private String descripcionPerfil;
	private String activo;
	
	public PerfilDTO(int idPerfil, String nombrePerfil, String descripcionPerfil, String activo) {
		super();
		this.idPerfil = idPerfil;
		this.nombrePerfil = nombrePerfil;
		this.descripcionPerfil = descripcionPerfil;
		this.activo = activo;
	}
	
}
