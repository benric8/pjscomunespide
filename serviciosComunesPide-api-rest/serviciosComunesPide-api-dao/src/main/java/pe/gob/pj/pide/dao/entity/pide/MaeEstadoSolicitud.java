package pe.gob.pj.pide.dao.entity.pide;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
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
@Table(name="MAE_ESTADO_SOLICITUD", schema = ConstantesSCPide.ESQUEMA_PIDE)
@FilterDefs(value= {
		@FilterDef(name=MaeEstadoSolicitud.F_ESOLICITUD_ID, parameters = {@ParamDef(name=MaeEstadoSolicitud.P_ESOLICITUD_ID, type="int")}),
		@FilterDef(name=MaeEstadoSolicitud.F_ESOLICITUD_PARA_EVALUACION, parameters = {@ParamDef(name=MaeEstadoSolicitud.P_ESOLICITUD_PARA_EVALUACION, type="string")})
})
@Filters(value = {
		@Filter(name = MaeEstadoSolicitud.F_ESOLICITUD_ID, condition = "n_estado_solicitud=:idEstadoSolicitud"),
		@Filter(name = MaeEstadoSolicitud.F_ESOLICITUD_PARA_EVALUACION, condition = "l_respuesta_evaluacion=:paraEvaluacion")
})
@NamedQueries(value= {
		@NamedQuery(name = MaeEstadoSolicitud.Q_ESOLICITUD_FILTERS, query = "SELECT mes FROM MaeEstadoSolicitud mes")
})
public class MaeEstadoSolicitud extends Auditoria implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String F_ESOLICITUD_ID = "MaeEstadoSolicitud.filterById";
	public static final String F_ESOLICITUD_PARA_EVALUACION = "MaeEstadoSolicitud.filterByEvaluacion";
	
	public static final String Q_ESOLICITUD_FILTERS = "MaeEstadoSolicitud.queryByFilters";
	
	public static final String P_ESOLICITUD_ID = "idEstadoSolicitud";
	public static final String P_ESOLICITUD_PARA_EVALUACION = "paraEvaluacion";
	

	@SequenceGenerator(name = "GENERATOR_ESTADO_SOLICITUD", schema = ConstantesSCPide.ESQUEMA_PIDE, sequenceName = "USEQ_MAE_ESTADO_SOLICITUD", allocationSize = 1)
	@GeneratedValue(generator = "GENERATOR_ESTADO_SOLICITUD",strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name="N_ESTADO_SOLICITUD")
	private int idEstadoSolicitud;
	
	@Column(name="X_ESTADO_SOLICITUD")
	private String nombreEstadoSolicitud;
	
	@Column(name="X_DESCRIPCION")
	private String descripcionEstadoSolicitud;
	
	@Column(name="L_RESPUESTA_EVALUACION")
	private String paraEvaluacion;
	
	@Column(name="L_ACTIVO")
	private String activo;
	
}
