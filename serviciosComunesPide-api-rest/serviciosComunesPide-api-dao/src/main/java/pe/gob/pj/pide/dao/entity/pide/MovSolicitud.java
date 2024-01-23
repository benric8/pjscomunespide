package pe.gob.pj.pide.dao.entity.pide;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import lombok.Setter;
import lombok.Getter;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;

@Entity
@Table(name="MOV_SOLICITUD", schema = ConstantesSCPide.ESQUEMA_PIDE)
@FilterDefs(value = {
		@FilterDef(name = MovSolicitud.F_ID_SOLICITUD, parameters = {@ParamDef(name = MovSolicitud.P_ID_SOLICITUD, type = "int")}),
		@FilterDef(name = MovSolicitud.F_ID_ENTIDAD, parameters = {@ParamDef(name = "idEntidad", type = "int")}),
		@FilterDef(name = MovSolicitud.F_ID_OPERACION, parameters = {@ParamDef(name = "idOperacion", type = "int")}),
		@FilterDef(name = MovSolicitud.F_ID_ESTADO_SOLICITUD, parameters = {@ParamDef(name = "idEstadoSolicitud", type = "int")}),
		@FilterDef(name = MovSolicitud.F_IDS_TIPO_SOLICITUD, parameters = {@ParamDef(name = MovSolicitud.P_IDS_TIPO_SOLICITUD, type = "int")}),
		@FilterDef(name = MovSolicitud.F_RANGO_FECHA, parameters = {@ParamDef(name="fechaDesde", type = "date"), @ParamDef(name="fechaHasta", type = "date")}),
		@FilterDef(name = MovSolicitud.F_TIPO_IMPACTO, parameters = {@ParamDef(name=MovSolicitud.P_TIPO_IMPACTO, type="string")}),
		@FilterDef(name = MovSolicitud.F_ESTADO_NOMBRE, parameters = {@ParamDef(name=MovSolicitud.P_ESTADO_NOMBRE, type="string")})
		})
@Filters(value = {
		@Filter(name = MovSolicitud.F_ID_SOLICITUD, condition = "n_solicitud = :idSolicitud"),
		@Filter(name = MovSolicitud.F_ID_ENTIDAD, condition = "n_entidad = :idEntidad"),
		@Filter(name = MovSolicitud.F_ID_OPERACION, condition = "n_operacion = :idOperacion"),
		@Filter(name = MovSolicitud.F_ID_ESTADO_SOLICITUD, condition = "n_estado_solicitud = :idEstadoSolicitud"),
		@Filter(name = MovSolicitud.F_IDS_TIPO_SOLICITUD, condition = "n_tipo_solicitud in (:idTipoSolicitud)"),
		@Filter(name = MovSolicitud.F_RANGO_FECHA, condition = "f_registro BETWEEN :fechaDesde AND :fechaHasta"),
		@Filter(name = MovSolicitud.F_TIPO_IMPACTO, condition = "upper(mts.c_impacto) = :tipoImpacto"),
		@Filter(name = MovSolicitud.F_ESTADO_NOMBRE, condition = "upper(x_estado_solicitud) = :estadoNombre")
		})
@NamedQuery(name = MovSolicitud.Q_SOLICITUD_BY_FILTER, query = "SELECT mbc FROM MovSolicitud mbc JOIN mbc.entidad me LEFT JOIN mbc.operacion mo JOIN mbc.tipoSolicitud mts JOIN mbc.estadoSolicitud mes ORDER BY mbc.idSolicitud DESC ")
@NamedQuery(name = MovSolicitud.Q_SOLICITUD_ACTIVO_COUNT_FILTER, query = "SELECT COUNT(mbc.idSolicitud) FROM MovSolicitud mbc LEFT JOIN mbc.entidad me JOIN mbc.operacion mo ")
@NamedQuery(name = MovSolicitud.Q_DETALLE_SOLICITUD_BY_FILTER, query = "SELECT "
		+ "new map(mbc AS solicitud, usrr AS usuarioRegistro, maeu AS usuarioAprobo, mova AS permiso) "
		+ "FROM MovSolicitud mbc "
		+ "JOIN mbc.entidad me "
		+ "JOIN mbc.usuarioRegistro usrr "
		+ "LEFT JOIN mbc.operacion mo "
		+ "LEFT JOIN MaeUsuario maeu ON maeu.idUsuario=mbc.usuarioAprobo "
		+ "LEFT JOIN MovAsignacionAcceso mova ON mova.entidad.idEntidad=me.idEntidad AND mova.operacion.idOperacion=mo.idOperacion "
		+ "ORDER BY mbc.idSolicitud DESC ")
public class MovSolicitud extends Auditoria implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String F_ID_SOLICITUD = "MovSolicitud.filterIdSolicitud";
	public static final String F_ID_ENTIDAD = "MovSolicitud.filterIdEntidad";
	public static final String F_ID_OPERACION = "MovSolicitud.filterIdOperacion";
	public static final String F_ID_ESTADO_SOLICITUD = "MovSolicitud.filteridEstadoSolicitud";
	public static final String F_IDS_TIPO_SOLICITUD = "MovSolicitud.filteridTipoSolicitud";
	public static final String F_RANGO_FECHA = "MovSolicitud.filterFechas";
	public static final String F_TIPO_IMPACTO = "MovSolicitud.filterTipoImpacto";
	public static final String F_ESTADO_NOMBRE = "MovSolicitud.filterEstadoNombre";
	
	public static final String Q_SOLICITUD_BY_FILTER = "MovSolicitud.findActivoByFilter";
	public static final String Q_SOLICITUD_ACTIVO_COUNT_FILTER = "MovSolicitud.countActivoByFilter";
	public static final String Q_DETALLE_SOLICITUD_BY_FILTER = "MovSolicitud.findDetalleByFilter";

	public static final String P_ID_SOLICITUD = "idSolicitud";
	public static final String P_ID_ENTIDAD = "idEntidad";
	public static final String P_ID_OPERACION = "idOperacion";
	public static final String P_ID_ESTADO_SOLICITUD = "idEstadoSolicitud";
	public static final String P_IDS_TIPO_SOLICITUD = "idTipoSolicitud";
	public static final String P_FECHA_DESDE = "fechaDesde";
	public static final String P_FECHA_HASTA = "fechaHasta";
	public static final String P_TIPO_IMPACTO = "tipoImpacto";
	public static final String P_ESTADO_NOMBRE = "estadoNombre";
	
	@SequenceGenerator(name = "GENERATOR_MOV_SOLICITUD", schema = ConstantesSCPide.ESQUEMA_PIDE, sequenceName = "USEQ_MOV_SOLICITUD", allocationSize = 1)
	@GeneratedValue(generator = "GENERATOR_MOV_SOLICITUD", strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name = "N_SOLICITUD")
	@Getter @Setter
	private int idSolicitud;

	@Column(name = "N_USUARIO_APROBO")
	@Getter @Setter
	private Integer usuarioAprobo;

	@Column(name = "X_JUSTIFICACION")
	@Getter @Setter
	private String justificacion;

	@Column(name = "C_DNI_SOLICITANTE")
	@Getter @Setter
	private String nroDocumentoSolicitante;

	@Column(name = "X_NOMBRE_SOLICITANTE")
	@Getter @Setter
	private String nombreSolicitante;

	@Column(name = "F_SOLICITO", nullable = false)
	@Getter @Setter
	private Date fechaSolicito;

	@Column(name = "F_EVALUACION")
	@Getter @Setter
	private Date fechaEvaluacion;

	@Column(name = "N_CUOTA_CAMBIO")
	@Getter @Setter
	private Integer cuotaCambio;

	@Column(name = "L_ESTADO_CAMBIO")
	@Getter @Setter
	private String estadoCambio;

	@Column(name = "L_ACTIVO")
	@Getter @Setter
	private String activo;
	
	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "N_USUARIO_REGISTRO")
	@Getter @Setter
	private MaeUsuario usuarioRegistro;
	
	@ManyToOne
	@JoinColumn(name="N_ENTIDAD")
	@Getter @Setter
	private MaeEntidad entidad;
	
	@ManyToOne
	@JoinColumn(name="N_OPERACION")
	@Getter @Setter
	private MaeOperacion operacion;

	@ManyToOne
	@JoinColumn(name = "N_ESTADO_SOLICITUD")
	@Getter @Setter
	private MaeEstadoSolicitud estadoSolicitud;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "N_TIPO_SOLICITUD")
	@Getter @Setter
	private MaeTipoSolicitud tipoSolicitud;

	//Bi-direccional
	
	@OneToMany(mappedBy = "solicitud",fetch = FetchType.LAZY)
	@Getter @Setter
	private Set<MovSolicitudIp> listaIps;

	
}
