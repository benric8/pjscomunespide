package pe.gob.pj.pide.dao.dto.pide;

import java.io.Serializable;

import lombok.Data;

@Data
public class EntidadDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idEntidad;
	private String ruc;
	private String razonSocial;
	private String activo;
	
	public EntidadDTO(Integer idEntidad, String ruc, String razonSocial, String activo) {
		super();
		this.idEntidad = idEntidad;
		this.ruc = ruc;
		this.razonSocial = razonSocial;
		this.activo = activo;
	}
	
}
