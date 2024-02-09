package pe.gob.pj.pide.ws.bean;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class ParamConfigReniecBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String endpoint;
	private String timeout;
	private String dniConsultante;
	private String usuario;
	
	public ParamConfigReniecBean() {
		super();
	}

	
	
}
