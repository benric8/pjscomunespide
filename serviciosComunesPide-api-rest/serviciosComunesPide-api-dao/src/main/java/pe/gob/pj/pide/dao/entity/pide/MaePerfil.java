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
@FilterDefs(value= {
		@FilterDef(name=MaePerfil.F_ID_PERFIL, parameters = {@ParamDef(name=MaePerfil.P_ID_PERFIL, type="int")}),
		@FilterDef(name=MaePerfil.F_ACTIVO, parameters = {@ParamDef(name=MaePerfil.P_ACTIVO, type="string")})
})
@Filters(value= {
		@Filter(name=MaePerfil.F_ID_PERFIL, condition = "N_PERFIL=:idPerfil"),
		@Filter(name=MaePerfil.F_ACTIVO, condition = "L_ACTIVO=:activo")
})
@NamedQueries(value= {
		@NamedQuery(name=MaePerfil.Q_FIND_FILTERS, query="SELECT mp FROM MaePerfil mp")
})
@Table(name = "MAE_PERFIL", schema = ConstantesSCPide.ESQUEMA_PIDE)
public class MaePerfil extends Auditoria implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String F_ID_PERFIL = "MaePerfil.filterIdPerfil";
	public static final String F_ACTIVO = "MaePerfil.filterActivo";
	
	public static final String Q_FIND_FILTERS = "MaePerfil.findByFilters";
	
	public static final String P_ID_PERFIL = "idPerfil";
	public static final String P_ACTIVO = "activo";
	
	@SequenceGenerator(name = "GENERATOR_MAE_PERFIL", schema = ConstantesSCPide.ESQUEMA_PIDE, sequenceName = "USEQ_MAE_PERFIL", allocationSize = 1 )
	@GeneratedValue(generator = "GENERATOR_MAE_PERFIL", strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name = "N_PERFIL", nullable = false)
	private int idPerfil;

	@Column(name="X_PERFIL")
	private String nombre;
	
	@Column(name="X_DESCRIPCION")
	private String descripcion;
	
	@Column(name="L_ACTIVO")
	private String activo;

}
