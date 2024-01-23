package pe.gob.pj.pide.dao.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


public class ResponseClaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Getter @Setter
	private String claveGenerada;

}
