package pe.gob.pj.pide.dao.dto.pide;

import java.io.Serializable;
import java.sql.Date;

import lombok.Data;

@Data
public class ConsumoCuotaDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idEntidad;
	private Integer idOperacion;
	private Date fechaConsumo;
	private int cuotaConsumida;
	
	public ConsumoCuotaDTO(Integer idEntidad, Integer idOperacion, Date fechaConsumo, int cuotaConsumida) {
		super();
		this.idEntidad = idEntidad;
		this.idOperacion = idOperacion;
		this.fechaConsumo = fechaConsumo;
		this.cuotaConsumida = cuotaConsumida;
	}

}
