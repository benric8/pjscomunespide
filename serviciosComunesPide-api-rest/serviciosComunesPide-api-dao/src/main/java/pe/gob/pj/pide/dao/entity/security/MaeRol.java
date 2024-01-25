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
 * The persistent class for the mae_rol database table.
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="mae_rol", schema = SecurityConstants.ESQUEMA_SEGURIDAD)
@NamedQuery(name="MaeRol.findAll", query="SELECT m FROM MaeRol m")
@NamedQuery(name=MaeRol.FIND_ROLES_BY_ID_USUARIO, query="SELECT m FROM MaeRol m JOIN m.maeRolUsuarios ur WHERE m.lActivo = '1' AND ur.maeUsuario.nUsuario = :idUsuario")
public class MaeRol implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String FIND_ROLES_BY_ID_USUARIO = "MaeRol.rolesPorUsuario";

	@Id
	@Column(name="n_rol")
	
	private Integer nRol;

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

	@Column(name="c_rol")
	private String cRol;

	@Column(name="f_aud")
	private Timestamp fAud;

	@Column(name="l_activo")
	private String lActivo;

	@Column(name="x_descripcion")
	private String xDescripcion;

	@Column(name="x_rol")
	private String xRol;

	//bi-directional many-to-one association to MaeOperacion
	@OneToMany(mappedBy="maeRol")
	private List<MaeOperacion> maeOperacions;

	//bi-directional many-to-one association to MaeRolUsuario
	@OneToMany(mappedBy="maeRol")
	private List<MaeRolUsuario> maeRolUsuarios;

}