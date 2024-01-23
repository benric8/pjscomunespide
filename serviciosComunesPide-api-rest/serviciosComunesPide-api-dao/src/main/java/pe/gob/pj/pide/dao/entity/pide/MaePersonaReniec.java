package pe.gob.pj.pide.dao.entity.pide;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name="MAE_PERSONA_RENIEC", schema = ConstantesSCPide.ESQUEMA_PIDE)
@NamedQuery(name = MaePersonaReniec.Q_PERSONA_BY_DNI, query = "SELECT mp FROM MaePersonaReniec mp WHERE mp.idPersonaReniec = :nroDni ")
public class MaePersonaReniec extends Auditoria implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String Q_PERSONA_BY_DNI = "MaePersonaReniec.findByDni";
	
	public static final String P_DNI = "nroDni";
	
	@Id
	@Column(name = "C_NRO_DNI", nullable = false)
	private String idPersonaReniec;
	@Column(name="X_APELLIDO_PATERNO")
	private String apellidoPaterno;
	@Column(name="X_APELLIDO_MATERNO")
	private String apellidoMaterno;
	@Column(name="X_NOMBRES")
	private String nombres;

}
