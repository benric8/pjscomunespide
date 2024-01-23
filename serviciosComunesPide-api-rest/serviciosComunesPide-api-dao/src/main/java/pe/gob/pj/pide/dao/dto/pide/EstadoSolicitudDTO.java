package pe.gob.pj.pide.dao.dto.pide;

import java.io.Serializable;

import lombok.Data;

@Data
public class EstadoSolicitudDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idEstadoSolicitud;
	private String nombre;
	private String descripcion;
	private String paraEvaluacion;
	private String activo;
	
	public EstadoSolicitudDTO(int idEstadoSolicitud, String nombre, String descripcion, String paraEvaluacion,
			String activo) {
		super();
		this.idEstadoSolicitud = idEstadoSolicitud;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.paraEvaluacion = paraEvaluacion;
		this.activo = activo;
	}
	
}
