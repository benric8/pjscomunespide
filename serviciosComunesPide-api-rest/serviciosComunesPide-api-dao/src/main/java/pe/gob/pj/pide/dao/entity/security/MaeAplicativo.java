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
 * The persistent class for the mae_aplicativo database table.
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="mae_aplicativo", schema = SecurityConstants.ESQUEMA_SEGURIDAD)
@NamedQuery(name="MaeAplicativo.findAll", query="SELECT m FROM MaeAplicativo m")
public class MaeAplicativo implements Serializable {
	private static final long serialVersionUID = 1L;
	
    @Id
	@Column(name="n_aplicativo")
	private Integer nAplicativo;

	@Column(name="b_aud")
	private String bAud;

	@Column(name="c_aplicativo")
	private String cAplicativo;

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

	@Column(name="x_aplicativo")
	private String xAplicativo;

	@Column(name="x_descripcion")
	private String xDescripcion;

	//bi-directional many-to-one association to MaeTipoAplicativo
	@ManyToOne
	@JoinColumn(name="n_tipo_aplicativo")
	private MaeTipoAplicativo maeTipoAplicativo;

	//bi-directional many-to-one association to MaeOperacion
	@OneToMany(mappedBy="maeAplicativo")
	private List<MaeOperacion> maeOperacions;
	
}