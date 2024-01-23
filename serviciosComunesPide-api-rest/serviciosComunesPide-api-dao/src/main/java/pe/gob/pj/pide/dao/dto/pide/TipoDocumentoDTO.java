package pe.gob.pj.pide.dao.dto.pide;

import java.io.Serializable;

import lombok.Data;

@Data
public class TipoDocumentoDTO  implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nombre;
	private String codigoRedam;
	private String codigoCondenas;
	private String activo;

}
