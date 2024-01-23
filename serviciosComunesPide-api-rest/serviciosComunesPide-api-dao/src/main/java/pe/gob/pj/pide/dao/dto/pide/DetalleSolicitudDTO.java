package pe.gob.pj.pide.dao.dto.pide;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class DetalleSolicitudDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idTipoSolicitud;
	private String nombreTipoSolicitud;
	
	private int idEstadoSolicitud;
	private String nombreEstadoSolicitud;
	
	private int idSolicitud;
	private String usuarioRegistro;
	private String usuarioEvaluo;
	private String estadoCambio;
	private Integer cuotaCambio;
	private String estadoActual;
	private Integer cuotaActual;
	private String fechaRegistro;
	private String fechaEvaluacion;
	private String justificacion;
	private String solicitante;
	private List<IpEntidadDTO> listaIpSolicitud;

}
