package pe.gob.pj.pide.dao.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class GlobalResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private String descripcion;
	private Object data;

}
