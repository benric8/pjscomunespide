package pe.gob.pj.pide.dao.dto.seguridad;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Integer id;		
	private String cUsuario;
	private String cClave;	
	private String lActivo;
	
}
