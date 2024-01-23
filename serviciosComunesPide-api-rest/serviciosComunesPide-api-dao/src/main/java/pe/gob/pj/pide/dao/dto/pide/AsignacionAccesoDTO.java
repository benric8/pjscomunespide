package pe.gob.pj.pide.dao.dto.pide;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AsignacionAccesoDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idEntidad;
	private String rucEntidad;
	private String nombreEntidad;
	private String activoEntidad;
	
	private Integer idOperacion;
	private String operacion;
	private String endpoint;
	private int cuotaDefecto;
	private String activoOpercion;
	
	private int cuotaAsignada;
	private String activo;
	private String fechaRegistro;
	
	public AsignacionAccesoDTO(Integer idEntidad, Integer idOperacion, String operacion, int cuotaAsignada) {
		super();
		this.idEntidad = idEntidad;
		this.idOperacion = idOperacion;
		this.operacion = operacion;
		this.cuotaAsignada = cuotaAsignada;
	}

	public AsignacionAccesoDTO(Integer idEntidad, String rucEntidad, String nombreEntidad, String activoEntidad,
			Integer idOperacion, String operacion, String endpoint, int cuotaDefecto, String activoOpercion,
			int cuotaAsignada, String activo, String fechaRegistro) {
		super();
		this.idEntidad = idEntidad;
		this.rucEntidad = rucEntidad;
		this.nombreEntidad = nombreEntidad;
		this.activoEntidad = activoEntidad;
		this.idOperacion = idOperacion;
		this.operacion = operacion;
		this.endpoint = endpoint;
		this.cuotaDefecto = cuotaDefecto;
		this.activoOpercion = activoOpercion;
		this.cuotaAsignada = cuotaAsignada;
		this.activo = activo;
		this.fechaRegistro = fechaRegistro;
	}

}
