package pe.gob.pj.pide.dao.entity;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

public class MovUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Getter	@Setter
	public Integer id;		
	@Getter	@Setter
	private String cUsuario;
	@Getter	@Setter
	private String cClave;	
	@Getter	@Setter
	private String lActivo;
	
	
	public MovUsuario() {
		super();
	}


	public MovUsuario(Integer id, String cUsuario, String cClave, String lActivo) {
		super();
		this.id = id;
		this.cUsuario = cUsuario;
		this.cClave = cClave;
		this.lActivo = lActivo;
	}	
	
	
}
