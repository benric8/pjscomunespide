package pe.gob.pj.pide.dao.entity.pide;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
@Table(name="MAE_OPERACION", schema=ConstantesSCPide.ESQUEMA_PIDE)
@FilterDefs(value = {@FilterDef(name=MaeOperacion.F_ID_OPERACION, parameters = { @ParamDef(name="idOperacion", type="int") })})
@Filters(value = {@Filter(name = MaeOperacion.F_ID_OPERACION, condition = "N_OPERACION=:idOperacion")})
@NamedQuery(name = MaeOperacion.Q_OPERACION, query = "SELECT mo FROM MaeOperacion mo")
@NamedQuery(name = MaeOperacion.Q_OPERACION_BY_ACTIVO, query = "SELECT mo FROM MaeOperacion mo WHERE mo.activo = '1' ")
@NamedQuery(name = MaeOperacion.Q_OPERACION_ACTIVO_BY_OPERACION, query = "SELECT mo FROM MaeOperacion mo WHERE mo.activo = '1' AND upper(operacion) LIKE :operacion ORDER BY operacion ")
@NamedQuery(name = MaeOperacion.Q_OPERACION_BY_ENDPOINT, query = "SELECT mo FROM MaeOperacion mo WHERE mo.endpoint = :endpoint")
@NamedQuery(name = MaeOperacion.Q_FIND_BY_FILTERS, query = "SELECT mo FROM MaeOperacion mo")
public class MaeOperacion extends Auditoria implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String F_ID_OPERACION = "MaeOperacion.filterIdOperacion";
	
	public static final String Q_OPERACION = "MaeOperacion.findOperacion";
	public static final String Q_OPERACION_BY_ACTIVO = "MaeOperacion.findByActivo";
	public static final String Q_OPERACION_ACTIVO_BY_OPERACION = "MaeOperacion.findByActivoOperacion";
	public static final String Q_OPERACION_BY_ENDPOINT = "MaeOperacion.findByEndpointOperacion";
	public static final String Q_FIND_BY_FILTERS = "MaeOperacion.findByFilters";
	
	public static final String P_OPERACION = "operacion";
	public static final String P_ID_OPERACION = "idOperacion";
	public static final String P_ENDPOINT_OPERACION = "endpoint";
	
	@SequenceGenerator(name = "GENERATOR_MAE_OPERACION", sequenceName = "USEQ_MAE_OPERACION", schema = ConstantesSCPide.ESQUEMA_PIDE, allocationSize = 1)
	@GeneratedValue(generator = "GENERATOR_MAE_OPERACION",strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name="N_OPERACION")
	private Integer idOperacion;
	
	@Column(name="X_ETIQUETA")
	private String nombre;
	
	@Column(name="X_OPERACION")
	private String operacion;
	
	@Column(name="X_DESCRIPCION")
	private String descripcion;
	
	@Column(name="X_ENDPOINT")
	private String endpoint;
	
	@Column(name="N_CUOTA_DEFECTO")
	private Integer cuotaDefecto;
	
	@Column(name="L_APROBACION_ACCESO")
	private String requiereAprobacionAcceso;
	
	@Column(name="L_APROBACION_CUOTA")
	private String requiereAprobacionCuota;
	
	@Column(name="L_APROBACION_IP")
	private String requiereAprobacionIps;
	
	@Column(name="L_APROBACION_ESTADO")
	private String requiereAprobacionEstado;
	
	@Column(name="L_ACTIVO")
	private String activo;
	
	@Column(name="x_aud_usuario")
	private String xAudUsuario;
	
}
