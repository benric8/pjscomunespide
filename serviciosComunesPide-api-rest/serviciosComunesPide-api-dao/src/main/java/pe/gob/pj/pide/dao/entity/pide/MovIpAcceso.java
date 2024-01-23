package pe.gob.pj.pide.dao.entity.pide;

import java.io.Serializable;

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
@Table(name = "MOV_IP_ACCESO", schema = ConstantesSCPide.ESQUEMA_PIDE)
@FilterDefs(value= {
		@FilterDef(name=MovIpAcceso.F_BY_ID_ENTIDAD, parameters = {@ParamDef(name="idEntidad", type = "int")})})
@Filters(value = {
		@Filter(name=MovIpAcceso.F_BY_ID_ENTIDAD, condition = "N_ENTIDAD = :idEntidad")})
@NamedQuery(name = MovIpAcceso.Q_IP_BY_FILTERS, query = "SELECT ia FROM MovIpAcceso ia JOIN ia.entidad me")
@NamedQuery(name = MovIpAcceso.Q_IP_ACTIVO_BY_RUC, query = "SELECT ia FROM MovIpAcceso ia JOIN ia.entidad me " + "WHERE me.activo = '1' AND ia.activo = '1' AND me.ruc = :nroRuc AND ia.ipPublica = :ip ")
@NamedQuery(name = MovIpAcceso.Q_IP_BY_ID_ENTIDAD, query = "SELECT ia FROM MovIpAcceso ia JOIN ia.entidad me WHERE me.idEntidad = :idEntidad AND TRIM(ia.ipPublica) = :ip ")
@NamedQuery(name = MovIpAcceso.Q_IP_BY_ID_ENTIDAD_ONLY, query = "SELECT ia FROM MovIpAcceso ia JOIN ia.entidad me WHERE me.idEntidad = :idEntidad ")
public class MovIpAcceso extends Auditoria implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String F_BY_ID_ENTIDAD = "MovIpAcceso.filterIdEntidad";
	
	public static final String Q_IP_BY_FILTERS = "MovIpAcceso.findByFilters";
	public static final String Q_IP_ACTIVO_BY_RUC = "MovIpAcceso.findActivoByRuc";
	public static final String Q_IP_BY_ID_ENTIDAD = "MovIpAcceso.findByRuc";
	public static final String Q_IP_BY_ID_ENTIDAD_ONLY = "MovIpAcceso.findByIdEntidad";
	
	public static final String P_RUC = "nroRuc";
	public static final String P_IP = "ip";
	public static final String P_ID_ENTIDAD = "idEntidad";
	
	@SequenceGenerator(name = "GENERATOR_MOV_IP_ACCESO",sequenceName = "USEQ_MOV_IP_ACCESO",schema = ConstantesSCPide.ESQUEMA_PIDE,allocationSize = 1)
	@GeneratedValue(generator = "GENERATOR_MOV_IP_ACCESO",strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name="N_IP_ACCESO")
	private Integer idIpacceso;
	
	@Column(name="X_IP_PUBLICA")
	private String ipPublica;
	
	@ManyToOne(optional = false, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "N_ENTIDAD")
	private MaeEntidad entidad = new MaeEntidad();
	
	@Column(name="L_ACTIVO")
	private String activo;
	
}
