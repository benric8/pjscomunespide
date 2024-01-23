package pe.gob.pj.pide.ws.bean;

import java.io.Serializable;

public class ParamConfigPideBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String user;
	private String pass;
	private String endpoint;
	private String timeout;
	private String codigoAplicativo;
	private String codigoRol;
	private String codigoCliente;
	
	public ParamConfigPideBean() {
		super();
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getCodigoAplicativo() {
		return codigoAplicativo;
	}

	public void setCodigoAplicativo(String codigoAplicativo) {
		this.codigoAplicativo = codigoAplicativo;
	}

	public String getCodigoRol() {
		return codigoRol;
	}

	public void setCodigoRol(String codigoRol) {
		this.codigoRol = codigoRol;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
	
}
