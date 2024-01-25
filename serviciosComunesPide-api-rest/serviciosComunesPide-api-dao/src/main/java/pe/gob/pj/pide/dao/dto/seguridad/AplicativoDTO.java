package pe.gob.pj.pide.dao.dto.seguridad;

import java.io.Serializable;

import lombok.Data;

@Data
public class AplicativoDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String cCodAplicativo;
	private String xNomAplicativo;
}
