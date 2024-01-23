package pe.gob.pj.pide.dao.entity.pide;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name="MAE_USUARIO", schema = ConstantesSCPide.ESQUEMA_PIDE)
@FilterDefs(value = {
		@FilterDef(name = MaeUsuario.F_ID_USUARIO, parameters = {@ParamDef(name = MaeUsuario.P_ID_USUARIO, type = "int")}),
		@FilterDef(name = MaeUsuario.F_USUARIO, parameters = {@ParamDef(name=MaeUsuario.P_USUARIO, type="string")}),
		@FilterDef(name = MaeUsuario.F_ACTIVO, parameters = {@ParamDef(name=MaeUsuario.P_ACTIVO, type="string")}),
		@FilterDef(name = MaeUsuario.F_ID_PERFIL, parameters = {@ParamDef(name=MaeUsuario.P_ID_PERFIL, type="int")})
})
@Filters(value = {
		@Filter(name = MaeUsuario.F_ID_USUARIO, condition = "N_USUARIO=:idUsuario"),
		@Filter(name = MaeUsuario.F_USUARIO, condition = "X_USUARIO=:usuario"),
		@Filter(name = MaeUsuario.F_ACTIVO, condition = "L_ACTIVO=:activo"),
		@Filter(name = MaeUsuario.F_ID_PERFIL, condition = "N_PERFIL=:idPerfil")
})
@NamedQuery(name = MaeUsuario.Q_FIND_BY_FILTERS, query = "SELECT mu FROM MaeUsuario mu JOIN mu.perfil mp ")
@NamedQuery(name = MaeUsuario.Q_FIND_FY_USUARIO, query = "SELECT mu FROM MaeUsuario mu JOIN mu.perfil mp WHERE mu.activo = '1' AND mp.activo = '1' AND mu.usuario = :usuario ")
@NamedQuery(name = MaeUsuario.Q_USUARIO_UPDATE_CLAVE, query = "UPDATE MaeUsuario SET clave = :claveNueva, fechaCambioClave = :fechaCambio WHERE idUsuario = :idUsuario ")
public class MaeUsuario extends Auditoria implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String F_ID_USUARIO = "MaeUsuario.filterIdUsuario";
	public static final String F_USUARIO = "MaeUsuario.filterUsuario";
	public static final String F_ACTIVO = "MaeUsuario.filterActivo";
	public static final String F_ID_PERFIL = "MaeUsuario.filterPerfil";

	public static final String Q_FIND_BY_FILTERS = "MaeUsuario.findByFilters";
	public static final String Q_FIND_FY_USUARIO = "MaeUsuario.findByUsuario";
	public static final String Q_USUARIO_UPDATE_CLAVE = "MaeUsuario.updateClave";
	
	public static final String P_ID_USUARIO = "idUsuario";
	public static final String P_USUARIO = "usuario";
	public static final String P_CLAVE_NUEVA = "claveNueva";
	public static final String P_FECHA_CAMBIO = "fechaCambio";
	public static final String P_ACTIVO = "activo";
	public static final String P_ID_PERFIL = "idPerfil";
	
	@SequenceGenerator(name = "GENERATOR_MAE_USUARIO", schema = ConstantesSCPide.ESQUEMA_PIDE, sequenceName = "USEQ_MAE_USUARIO", allocationSize = 1)
	@GeneratedValue(generator = "GENERATOR_MAE_USUARIO", strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name = "N_USUARIO", nullable = false)
	private Integer idUsuario;

	@Column(name = "X_USUARIO")
	private String usuario;

	@Column(name = "X_PASSWORD")
	private String clave;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "F_CAMBIO_CLAVE")
	private Date fechaCambioClave;

	@Column(name = "X_APELLIDOS_NOMBRES")
	private String apellidosNombres;

	@Column(name = "L_ACTIVO")
	private String activo;
	
	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "N_PERFIL")
	private MaePerfil perfil;
	
}
