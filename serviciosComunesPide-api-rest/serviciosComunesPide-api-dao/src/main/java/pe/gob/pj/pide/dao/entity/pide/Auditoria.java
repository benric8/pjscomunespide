package pe.gob.pj.pide.dao.entity.pide;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class Auditoria implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="F_AUD")
	private Date fAud;
	@Column(name="B_AUD")
	private String cAud;
	@Column(name="C_AUD_UID")
	private String cAudId;
	@Column(name="C_AUD_UIDRED")
	private String cAudIdRed;
	@Column(name="C_AUD_PC")
	private String cAudPc;
	@Column(name="C_AUD_IP")
	private String cAudIp;
	@Column(name="C_AUD_MCADDR")
	private String cAudMcAddr;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="F_REGISTRO", nullable = false)
	private Date fechaRegistro;
}
