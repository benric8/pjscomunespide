package pe.gob.pj.pide.dao.dto;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class ResponseLoginDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Setter @Getter
	private String codigo;
	
	@Setter @Getter
	private String token;
	
	@Setter @Getter
	private String tokenAdmin;
	
	@Setter @Getter
	private String exededReload;
	
	@Setter @Getter
	private String exededReloadAdmin;

}
