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
	private String nombreOperacion;
	private String descripcionOperacion;
	private String endPoint;
	private int cuotaDefecto;
	private String activo;
	
}
