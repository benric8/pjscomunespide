package pe.gob.pj.pide.dao.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ResponseHistorialCuotaDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String etiquetaMes;
	private List<Integer> consumoCuota;
	private List<Integer> consumoDia;
	private List<Integer> cuotaAsignada;
	private Integer diasConsumidos;

}
