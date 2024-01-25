package pe.gob.pj.pide.dao.entity.security;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.pj.pide.dao.utils.SecurityConstants;

import java.sql.Timestamp;


/**
 * The persistent class for the mae_operacion database table.
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="mae_operacion", schema = SecurityConstants.ESQUEMA_SEGURIDAD)
@NamedQuery(name="MaeOperacion.findAll", query="SELECT m FROM MaeOperacion m")
public class MaeOperacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="n_operacion")
	private Integer nOperacion;

	@Column(name="b_aud")
	private String bAud;

	@Column(name="c_aud_ip")
	private String cAudIp;

	@Column(name="c_aud_mcaddr")
	private String cAudMcaddr;

	@Column(name="c_aud_pc")
	private String cAudPc;

	@Column(name="c_aud_uid")
	private String cAudUid;

	@Column(name="c_aud_uidred")
	private String cAudUidred;

	@Column(name="f_aud")
	private Timestamp fAud;

	@Column(name="l_activo")
	private String lActivo;

	@Column(name="x_descripcion")
	private String xDescripcion;

	@Column(name="x_endpoint")
	private String xEndpoint;

	@Column(name="x_operacion")
	private String xOperacion;

	//bi-directional many-to-one association to MaeAplicativo
	@ManyToOne
	@JoinColumn(name="n_aplicativo")
	private MaeAplicativo maeAplicativo;

	//bi-directional many-to-one association to MaeRol
	@ManyToOne
	@JoinColumn(name="n_rol")
	private MaeRol maeRol;

}