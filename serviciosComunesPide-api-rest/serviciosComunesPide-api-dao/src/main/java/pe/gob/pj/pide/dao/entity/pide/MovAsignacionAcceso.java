package pe.gob.pj.pide.dao.entity.pide;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQuery;
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
@Table(name="MOV_ASIGNACION_ACCESO", schema = ConstantesSCPide.ESQUEMA_PIDE)
@FilterDefs(value = {
		@FilterDef(name = MovAsignacionAcceso.F_ID_ENTIDAD, parameters = {@ParamDef(name = "idEntidad", type = "int")}),
		@FilterDef(name = MovAsignacionAcceso.F_ID_OPERACION, parameters = {@ParamDef(name = "idOperacion", type = "int")}),
		@FilterDef(name = MovAsignacionAcceso.F_ESTADO, parameters = {@ParamDef(name = "estado", type = "string")}),
		@FilterDef(name = MovAsignacionAcceso.F_RANGO_FECHA, parameters = {@ParamDef(name="fechaDesde", type = "date"), @ParamDef(name="fechaHasta", type = "date")})})
@Filters(value = {
		@Filter(name = MovAsignacionAcceso.F_ID_ENTIDAD, condition = "n_entidad = :idEntidad"),
		@Filter(name = MovAsignacionAcceso.F_ID_OPERACION, condition = "n_operacion = :idOperacion"),
		@Filter(name = MovAsignacionAcceso.F_ESTADO, condition = "l_activo = :estado"),
		@Filter(name = MovAsignacionAcceso.F_RANGO_FECHA, condition = "f_registro BETWEEN :fechaDesde AND :fechaHasta")})
@NamedQuery(name = MovAsignacionAcceso.Q_ASIGNACION_ACCESO_BY_FILTERS, query = "SELECT maa FROM MovAsignacionAcceso maa JOIN maa.entidad me JOIN maa.operacion mo ORDER BY maa.fechaRegistro DESC ")
@NamedQuery(name = MovAsignacionAcceso.Q_ASIGNACION_COUNT, query = "SELECT count(maa.cuotaAsignada) FROM MovAsignacionAcceso maa JOIN maa.entidad me JOIN maa.operacion mo")
@NamedQuery(name = MovAsignacionAcceso.Q_ASIGNACION_ACCESO_BY_RUC_OPERACION, query = "SELECT maa FROM MovAsignacionAcceso maa "
		+ "JOIN maa.entidad me "
		+ "JOIN maa.operacion mo "
		+ "WHERE me.activo = '1' AND mo.activo = '1' AND maa.activo = '1' "
		+ "AND me.ruc = :nroRuc "
		+ "AND mo.operacion = :operacion ")
public class MovAsignacionAcceso extends Auditoria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String Q_ASIGNACION_ACCESO_BY_RUC_OPERACION = "MovAsignacionAcceso.findByRucOperacion";
	public static final String Q_ASIGNACION_ACCESO_BY_FILTERS = "MovAsignacionAcceso.findByFilters";
	public static final String Q_ASIGNACION_COUNT = "MovAsignacionAcceso.count";
	
	public static final String F_ID_ENTIDAD = "MovAsignacionAcceso.filterIdEntidad";
	public static final String F_ID_OPERACION = "MovAsignacionAcceso.filterIdOperacion";
	public static final String F_ESTADO = "MovAsignacionAcceso.filterEstado";
	public static final String F_RANGO_FECHA = "MovAsignacionAcceso.filterFechas";
	
	public static final String P_RUC = "nroRuc";
	public static final String P_OPERACION = "operacion";
	public static final String P_ID_ENTIDAD = "idEntidad";
	public static final String P_ID_OPERACION = "idOperacion";
	public static final String P_ESTADO = "estado";
	public static final String P_FECHA_DESDE = "fechaDesde";
	public static final String P_FECHA_HASTA = "fechaHasta";
	
	@EmbeddedId
	private MovAsignacionAccesoId maacceso = new MovAsignacionAccesoId();
	
	@MapsId("N_ENTIDAD")
	@ManyToOne
	@JoinColumn(name="N_ENTIDAD")
	private MaeEntidad entidad;
	
	@MapsId("N_OPERACION")
	@ManyToOne
	@JoinColumn(name="N_OPERACION")
	private MaeOperacion operacion;
	
	@Column(name="N_CUOTA")
	private Integer cuotaAsignada;

	@Column(name="L_ACTIVO")
	private String activo;

}
