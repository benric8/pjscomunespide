package pe.gob.pj.pide.ws.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The persistent class for the PRE_SOLICITUD database table.
 * 
 */
public class PersonaBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String cNumeTramite;
	private String cDocuIdentidad;
	private String cNumeIdentidad;
	private String cNumeTelefono;
	private String codgCerti;
	private Date fFechEmision;
	private Date fFechNacimiento;
	private Date fFechOperacion;
	private Date fFechRegistro;
	private String lEstaIdentidad;
	private String lEstado;
	private String xApllMaterSolic;
	private String xApllPaterSolic;
	private String xCorreo;
	private String xIpOperacion;
	private String xIpWanOperacion;
	private String xMacOperacion;
	private String xNom1Solic;
	private String xNom2Solic;
	private String xNom3Solic;
	private String xNombMadre;
	private String xNombPadre;
	private String xUsuario;
	private String cUbigeo;
	private String lNaciOtroPais;
	private String codgLugarRecojo;
	private String xLugarNacimiento;
	private String cVerificacion;
	
	public PersonaBean() {
	}

	/**
	 * @return the cNumeTramite
	 */
	public String getcNumeTramite() {
		return cNumeTramite;
	}

	/**
	 * @param cNumeTramite the cNumeTramite to set
	 */
	public void setcNumeTramite(String cNumeTramite) {
		this.cNumeTramite = cNumeTramite;
	}

	/**
	 * @return the cDocuIdentidad
	 */
	public String getcDocuIdentidad() {
		return cDocuIdentidad;
	}

	/**
	 * @param cDocuIdentidad the cDocuIdentidad to set
	 */
	public void setcDocuIdentidad(String cDocuIdentidad) {
		this.cDocuIdentidad = cDocuIdentidad;
	}

	/**
	 * @return the cNumeIdentidad
	 */
	public String getcNumeIdentidad() {
		return cNumeIdentidad;
	}

	/**
	 * @param cNumeIdentidad the cNumeIdentidad to set
	 */
	public void setcNumeIdentidad(String cNumeIdentidad) {
		this.cNumeIdentidad = cNumeIdentidad;
	}

	/**
	 * @return the cNumeTelefono
	 */
	public String getcNumeTelefono() {
		return cNumeTelefono;
	}

	/**
	 * @param cNumeTelefono the cNumeTelefono to set
	 */
	public void setcNumeTelefono(String cNumeTelefono) {
		this.cNumeTelefono = cNumeTelefono;
	}

	/**
	 * @return the codgCerti
	 */
	public String getCodgCerti() {
		return codgCerti;
	}

	/**
	 * @param codgCerti the codgCerti to set
	 */
	public void setCodgCerti(String codgCerti) {
		this.codgCerti = codgCerti;
	}



	public Date getfFechEmision() {
		return fFechEmision;
	}

	public void setfFechEmision(Date fFechEmision) {
		this.fFechEmision = fFechEmision;
	}

	public Date getfFechNacimiento() {
		return fFechNacimiento;
	}

	public void setfFechNacimiento(Date fFechNacimiento) {
		this.fFechNacimiento = fFechNacimiento;
	}

	public Date getfFechOperacion() {
		return fFechOperacion;
	}

	public void setfFechOperacion(Date fFechOperacion) {
		this.fFechOperacion = fFechOperacion;
	}

	public Date getfFechRegistro() {
		return fFechRegistro;
	}

	public void setfFechRegistro(Date fFechRegistro) {
		this.fFechRegistro = fFechRegistro;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @param fFechRegistro the fFechRegistro to set
	 */
	public void setfFechRegistro(Timestamp fFechRegistro) {
		this.fFechRegistro = fFechRegistro;
	}

	/**
	 * @return the lEstaIdentidad
	 */
	public String getlEstaIdentidad() {
		return lEstaIdentidad;
	}

	/**
	 * @param lEstaIdentidad the lEstaIdentidad to set
	 */
	public void setlEstaIdentidad(String lEstaIdentidad) {
		this.lEstaIdentidad = lEstaIdentidad;
	}

	/**
	 * @return the lEstado
	 */
	public String getlEstado() {
		return lEstado;
	}

	/**
	 * @param lEstado the lEstado to set
	 */
	public void setlEstado(String lEstado) {
		this.lEstado = lEstado;
	}

	/**
	 * @return the xApllMaterSolic
	 */
	public String getxApllMaterSolic() {
		return xApllMaterSolic;
	}

	/**
	 * @param xApllMaterSolic the xApllMaterSolic to set
	 */
	public void setxApllMaterSolic(String xApllMaterSolic) {
		this.xApllMaterSolic = xApllMaterSolic;
	}

	/**
	 * @return the xApllPaterSolic
	 */
	public String getxApllPaterSolic() {
		return xApllPaterSolic;
	}

	/**
	 * @param xApllPaterSolic the xApllPaterSolic to set
	 */
	public void setxApllPaterSolic(String xApllPaterSolic) {
		this.xApllPaterSolic = xApllPaterSolic;
	}

	/**
	 * @return the xCorreo
	 */
	public String getxCorreo() {
		return xCorreo;
	}

	/**
	 * @param xCorreo the xCorreo to set
	 */
	public void setxCorreo(String xCorreo) {
		this.xCorreo = xCorreo;
	}

	/**
	 * @return the xIpOperacion
	 */
	public String getxIpOperacion() {
		return xIpOperacion;
	}

	/**
	 * @param xIpOperacion the xIpOperacion to set
	 */
	public void setxIpOperacion(String xIpOperacion) {
		this.xIpOperacion = xIpOperacion;
	}

	/**
	 * @return the xIpWanOperacion
	 */
	public String getxIpWanOperacion() {
		return xIpWanOperacion;
	}

	/**
	 * @param xIpWanOperacion the xIpWanOperacion to set
	 */
	public void setxIpWanOperacion(String xIpWanOperacion) {
		this.xIpWanOperacion = xIpWanOperacion;
	}

	/**
	 * @return the xMacOperacion
	 */
	public String getxMacOperacion() {
		return xMacOperacion;
	}

	/**
	 * @param xMacOperacion the xMacOperacion to set
	 */
	public void setxMacOperacion(String xMacOperacion) {
		this.xMacOperacion = xMacOperacion;
	}

	/**
	 * @return the xNom1Solic
	 */
	public String getxNom1Solic() {
		return xNom1Solic;
	}

	/**
	 * @param xNom1Solic the xNom1Solic to set
	 */
	public void setxNom1Solic(String xNom1Solic) {
		this.xNom1Solic = xNom1Solic;
	}

	/**
	 * @return the xNom2Solic
	 */
	public String getxNom2Solic() {
		return xNom2Solic;
	}

	/**
	 * @param xNom2Solic the xNom2Solic to set
	 */
	public void setxNom2Solic(String xNom2Solic) {
		this.xNom2Solic = xNom2Solic;
	}

	/**
	 * @return the xNom3Solic
	 */
	public String getxNom3Solic() {
		return xNom3Solic;
	}

	/**
	 * @param xNom3Solic the xNom3Solic to set
	 */
	public void setxNom3Solic(String xNom3Solic) {
		this.xNom3Solic = xNom3Solic;
	}

	/**
	 * @return the xNombMadre
	 */
	public String getxNombMadre() {
		return xNombMadre;
	}

	/**
	 * @param xNombMadre the xNombMadre to set
	 */
	public void setxNombMadre(String xNombMadre) {
		this.xNombMadre = xNombMadre;
	}

	/**
	 * @return the xNombPadre
	 */
	public String getxNombPadre() {
		return xNombPadre;
	}

	/**
	 * @param xNombPadre the xNombPadre to set
	 */
	public void setxNombPadre(String xNombPadre) {
		this.xNombPadre = xNombPadre;
	}

	/**
	 * @return the xUsuario
	 */
	public String getxUsuario() {
		return xUsuario;
	}

	/**
	 * @param xUsuario the xUsuario to set
	 */
	public void setxUsuario(String xUsuario) {
		this.xUsuario = xUsuario;
	}

	/**
	 * @return the cUbigeo
	 */
	public String getcUbigeo() {
		return cUbigeo;
	}

	public void setcUbigeo(String cUbigeo) {
		this.cUbigeo = cUbigeo;
	}
	
	public String getCodgLugarRecojo() {
		return codgLugarRecojo;
	}

	public void setCodgLugarRecojo(String codgLugarRecojo) {
		this.codgLugarRecojo = codgLugarRecojo;
	}
	
	public String getlNaciOtroPais() {
		return lNaciOtroPais;
	}

	public void setlNaciOtroPais(String lNaciOtroPais) {
		this.lNaciOtroPais = lNaciOtroPais;
	}

	public String getxLugarNacimiento() {
		return xLugarNacimiento;
	}

	public void setxLugarNacimiento(String xLugarNacimiento) {
		this.xLugarNacimiento = xLugarNacimiento;
	}
	
	public String getcVerificacion() {
		return cVerificacion;
	}

	public void setcVerificacion(String cVerificacion) {
		this.cVerificacion = cVerificacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cNumeIdentidad == null) ? 0 : cNumeIdentidad.hashCode());
		result = prime * result
				+ ((xApllMaterSolic == null) ? 0 : xApllMaterSolic.hashCode());
		result = prime * result
				+ ((xApllPaterSolic == null) ? 0 : xApllPaterSolic.hashCode());
		result = prime * result
				+ ((xNom1Solic == null) ? 0 : xNom1Solic.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonaBean other = (PersonaBean) obj;
		if (cNumeIdentidad == null) {
			if (other.cNumeIdentidad != null)
				return false;
		} else if (!cNumeIdentidad.equals(other.cNumeIdentidad))
			return false;
		if (xApllMaterSolic == null) {
			if (other.xApllMaterSolic != null)
				return false;
		} else if (!xApllMaterSolic.equals(other.xApllMaterSolic))
			return false;
		if (xApllPaterSolic == null) {
			if (other.xApllPaterSolic != null)
				return false;
		} else if (!xApllPaterSolic.equals(other.xApllPaterSolic))
			return false;
		if (xNom1Solic == null) {
			if (other.xNom1Solic != null)
				return false;
		} else if (!xNom1Solic.equals(other.xNom1Solic))
			return false;
		return true;
	}

	

}