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

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name="MAE_ENTIDAD", schema=ConstantesSCPide.ESQUEMA_PIDE)
@FilterDefs(value = {@FilterDef(name = MaeEntidad.F_ID_ENTIDAD, parameters = { @ParamDef(name="idEntidad", type="int") })})
@Filters(value = {@Filter(name = MaeEntidad.F_ID_ENTIDAD, condition = "N_ENTIDAD = :idEntidad")})
@NamedQuery(name = MaeEntidad.Q_ENTIDAD_BY_RUC, query = "SELECT e FROM MaeEntidad e WHERE e.ruc = :nroRuc ")
@NamedQuery(name = MaeEntidad.Q_ENTIDAD_BY_ACTIVO, query = "SELECT me FROM MaeEntidad me WHERE me.activo= '1' ")
@NamedQuery(name = MaeEntidad.Q_ENTIDAD_ACTIVO_BY_RSOCIAL, query = "SELECT me FROM MaeEntidad me WHERE me.activo= '1' AND upper(razonSocial) LIKE :razonSocial ORDER BY razonSocial ")
public class MaeEntidad extends Auditoria implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String F_ID_ENTIDAD = "MaeEntidad.filterIdEntidad";
	
	public static final String Q_ENTIDAD_BY_RUC = "MaeEntidad.findByRuc";
	public static final String Q_ENTIDAD_BY_ACTIVO = "MaeEntidad.findByActivo";
	public static final String Q_ENTIDAD_ACTIVO_BY_RSOCIAL = "MaeEntidad.findByActivoRSocial";
	
	public static final String P_RUC = "nroRuc";
	public static final String P_RAZON_SOCIAL = "razonSocial";
	public static final String P_ID_ENTIDAD = "idEntidad";
	
	
	@SequenceGenerator(name="GENERATOR_MAE_ENTIDAD", sequenceName = "USEQ_MAE_ENTIDAD", schema = ConstantesSCPide.ESQUEMA_PIDE, allocationSize = 1)
	@GeneratedValue(generator = "GENERATOR_MAE_ENTIDAD", strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name="N_ENTIDAD", nullable = false)
	private Integer idEntidad;
	
	@Column(name="X_RUC", nullable = false)
	private String ruc;
	
	@Column(name="X_RAZON_SOCIAL", nullable = false)
	private String razonSocial;

	@Column(name="L_ACTIVO", nullable = false)
	private String activo;

}
