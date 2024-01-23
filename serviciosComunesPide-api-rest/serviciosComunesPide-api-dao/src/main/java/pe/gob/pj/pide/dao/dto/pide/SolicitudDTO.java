package pe.gob.pj.pide.dao.dto.pide;

import java.io.Serializable;

import lombok.Data;

@Data
public class SolicitudDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idSolicitud;
	
	private Integer idEntidad;
	private String nombreEntidad;
	
	private Integer idOperacion;
	private String nombreOperacion;
	
	private String documentoSolicitante;
	private String solicitante;
	private String justificacion;
	private String fechaSolicito;
	private String fechaAprobo;
	
	private String estadoSolicito;
	private Integer cuotaSolicito;
	private String estadoSolicitud;
	private String tipoSolicitud;
	
	public SolicitudDTO(Integer idSolicitud, Integer idEntidad, String nombreEntidad, Integer idOperacion, String nombreOperacion,
			String documentoSolicitante, String solicitante, String justificacion, String fechaSolicito, String fechaAprobo, Integer cuotaSolicito,
			String estadoSolicito, String estadoSolicitud, String tipoSolicitud) {
		super();
		this.idSolicitud = idSolicitud;
		this.idEntidad = idEntidad;
		this.nombreEntidad = nombreEntidad;
		this.idOperacion = idOperacion;
		this.nombreOperacion = nombreOperacion;
		this.documentoSolicitante = documentoSolicitante;
		this.solicitante = solicitante;
		this.justificacion = justificacion;
		this.fechaSolicito = fechaSolicito;
		this.fechaAprobo = fechaAprobo;
		this.cuotaSolicito = cuotaSolicito;
		this.estadoSolicito = estadoSolicito;
		this.estadoSolicitud = estadoSolicitud;
		this.tipoSolicitud = tipoSolicitud;
	}

	public SolicitudDTO() {
		super();
	}
	
}
