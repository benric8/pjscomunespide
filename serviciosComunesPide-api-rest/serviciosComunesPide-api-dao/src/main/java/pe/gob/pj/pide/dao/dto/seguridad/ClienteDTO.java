package pe.gob.pj.pide.dao.dto.seguridad;

import java.io.Serializable;

import lombok.Data;

@Data
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String cCodCliente;
	private String xNomCliente;

}
