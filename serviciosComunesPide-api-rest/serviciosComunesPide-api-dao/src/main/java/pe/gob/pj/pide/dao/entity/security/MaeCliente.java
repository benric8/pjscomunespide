package pe.gob.pj.pide.dao.entity.security;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.pj.pide.dao.utils.SecurityConstants;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the mae_cliente database table.
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="mae_cliente", schema = SecurityConstants.ESQUEMA_SEGURIDAD)
@NamedQuery(name="MaeCliente.findAll", query="SELECT m FROM MaeCliente m")
public class MaeCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="n_cliente")
	private Integer nCliente;

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

	@Column(name="c_cliente")
	private String cCliente;

	@Column(name="f_aud")
	private Timestamp fAud;

	@Column(name="l_activo")
	private String lActivo;

	@Column(name="l_tipo_cliente")
	private String lTipoCliente;

	@Column(name="x_cliente")
	private String xCliente;

	@Column(name="x_descripcion")
	private String xDescripcion;

	//bi-directional many-to-one association to MaeUsuario
	@OneToMany(mappedBy="maeCliente")
	private List<MaeUsuario> maeUsuarios;

}