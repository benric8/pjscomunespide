package pe.gob.pj.pide.ws.bean;

import java.io.Serializable;

public class ParamConfigReniecBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String endpoint;
	private String timeout;
	private String dniConsultante;
	
	public ParamConfigReniecBean() {
		super();
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

	public String getDniConsultante() {
		return dniConsultante;
	}

	public void setDniConsultante(String dniConsultante) {
		this.dniConsultante = dniConsultante;
	}
	
}
