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
 * The persistent class for the mae_tipo_aplicativo database table.
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="mae_tipo_aplicativo", schema = SecurityConstants.ESQUEMA_SEGURIDAD)
@NamedQuery(name="MaeTipoAplicativo.findAll", query="SELECT m FROM MaeTipoAplicativo m")
public class MaeTipoAplicativo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="n_tipo_aplicativo")
	private Integer nTipoAplicativo;

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

	@Column(name="x_tipo_aplicativo")
	private String xTipoAplicativo;

	//bi-directional many-to-one association to MaeAplicativo
	@OneToMany(mappedBy="maeTipoAplicativo")
	private List<MaeAplicativo> maeAplicativos;

}