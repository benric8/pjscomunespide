package pe.gob.pj.pide.dao.dto.pide;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OperacionDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idOperacion;
	private String nombre;
	private String operacion;
	private String descripcion;
	private String endPoint;
	private int cuotaDefecto;
	private String requiereAprobacionAcceso;
	private String requiereAprobacionCuota;
	private String requiereAprobacionIps;
	private String requiereAprobacionEstado;
	private String activo;
	
	public OperacionDTO(Integer idOperacion, String nombre) {
		super();
		this.idOperacion = idOperacion;
		this.nombre = nombre;
	}
}
