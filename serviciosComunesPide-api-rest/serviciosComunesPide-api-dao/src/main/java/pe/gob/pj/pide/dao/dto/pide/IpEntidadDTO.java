package pe.gob.pj.pide.dao.dto.pide;

import java.io.Serializable;

import lombok.Data;

@Data
public class IpEntidadDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int idEntidad;
	private int idIpAcceso;
	private String ipPublica;
	private String activo;
	
	public IpEntidadDTO(int idEntidad, int idIpAcceso, String ipPublica, String activo) {
		super();
		this.idIpAcceso = idIpAcceso;
		this.idEntidad = idEntidad;
		this.ipPublica = ipPublica;
		this.activo = activo;
	}

}
