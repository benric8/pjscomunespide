package pe.gob.pj.pide.dao.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ResponseValidarRucDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nroRuc;
	private String razonSocial;
	private String respuestaSunat;
	
}
