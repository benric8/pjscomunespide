package pe.gob.pj.pide.dao.entity.pide;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class MovAsignacionAccesoId implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="N_ENTIDAD")
	private Integer idEntidad;
	
	@Column(name = "N_OPERACION")
	private Integer idOperacion;
	
}
