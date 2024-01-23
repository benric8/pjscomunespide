package pe.gob.pj.pide.ws.bean;

import java.io.Serializable;

public class EntidadPideBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nroRuc; //nro de ruc de entidad
	private String razonSocial; // razon social de entidad
		
	public EntidadPideBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getNroRuc() {
		return nroRuc;
	}
	public void setNroRuc(String nroRuc) {
		this.nroRuc = nroRuc;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
}
