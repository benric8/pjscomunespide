package pe.gob.pj.pide.dao.dto.pide;

import java.io.Serializable;

import javax.validation.constraints.NotNull;


import lombok.Data;

@NotNull(message = "Se debe ingresar por lo menos un tipo solicitud.")
@Data
public class TipoSolicitudDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idTipoSolicitud;
	private String nombre;
	private String descripcion;
	private String activo;
	private String requiereAcceso;
	private String impacto;
	
	public TipoSolicitudDTO(int idTipoSolicitud, String nombre, String descripcion, String activo,String requiereAcceso,String impacto) {
		super();
		this.idTipoSolicitud = idTipoSolicitud;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.activo = activo;
		this.requiereAcceso=requiereAcceso;
		this.impacto=impacto;
	}
	
}
