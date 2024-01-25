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
 * The persistent class for the mae_usuario database table.
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="mae_usuario", schema = SecurityConstants.ESQUEMA_SEGURIDAD)
@NamedQuery(name="MaeUsuario.findAll", query="SELECT m FROM MaeUsuario m")
@NamedQuery(name=MaeUsuario.FIND_BY_ID, query="SELECT m FROM MaeUsuario m WHERE m.lActivo = '1' AND m.nUsuario = :idUsuario ")
@NamedQuery(name=MaeUsuario.FIND_BY_CODIGO, query="SELECT m FROM MaeUsuario m WHERE m.lActivo = '1' AND m.cUsuario = :codigo ")
public class MaeUsuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String FIND_BY_ID = "MaeUsuario.buscarPorId";
	public static final String P_N_USUARIO = "idUsuario";
	public static final String FIND_BY_CODIGO = "MaeUsuario.buscarPorcodigo";
	public static final String P_N_CODIGO = "codigo";

	@Id
	@Column(name="n_usuario")
	private Integer nUsuario;

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

	@Column(name="c_clave")
	private String cClave;

	@Column(name="c_usuario")
	private String cUsuario;

	@Column(name="f_aud")
	private Timestamp fAud;

	@Column(name="f_registro")
	private Timestamp fRegistro;

	@Column(name="l_activo")
	private String lActivo;

	//bi-directional many-to-one association to MaeRolUsuario
	@OneToMany(mappedBy="maeUsuario")
	private List<MaeRolUsuario> maeRolUsuarios;

	//bi-directional many-to-one association to MaeCliente
	@ManyToOne
	@JoinColumn(name="n_cliente")
	private MaeCliente maeCliente;

}