package pe.gob.pj.pide.dao.entity.pide;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.ParamDef;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "MAE_TIPO_DOCUMENTO", schema = ConstantesSCPide.ESQUEMA_PIDE)
@FilterDefs(value = {
		@FilterDef(name = MaeTipoDocumento.F_ID, parameters = {@ParamDef(name = MaeTipoDocumento.P_ID,type = "int")}),
		@FilterDef(name = MaeTipoDocumento.F_CODIGO, parameters = {@ParamDef(name=MaeTipoDocumento.P_CODIGO,type = "string")})
})
@Filters(value = {
		@Filter(name = MaeTipoDocumento.F_ID, condition = "N_TIPO_DOCUMENTO = :idTipoDocumento"),
		@Filter(name = MaeTipoDocumento.F_CODIGO, condition = "C_TIPO_DOCUMENTO = :codigoTipoDocumento")
})
@NamedQueries(value = {
		@NamedQuery(name = MaeTipoDocumento.Q_N_FILTERS, query = "FROM MaeTipoDocumento mt WHERE mt.activo = '1'", readOnly = true)
})
public class MaeTipoDocumento extends Auditoria implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String F_ID = "MaeTipoDocumento.filterId";
	public static final String F_CODIGO = "MaeTipoDocumento.filterCodigo";
	
	public static final String Q_N_FILTERS = "MaeTipoDocumento.queryNamedFilters";
	
	public static final String P_ID = "idTipoDocumento";
	public static final String P_CODIGO = "codigoTipoDocumento";
	
	@Id
	@SequenceGenerator(name = "GENERATOR_MAE_TIPO_DOCUMENTO", sequenceName = "USEQ_MAE_TIPO_DOCUMENTO" ,schema = ConstantesSCPide.ESQUEMA_PIDE, allocationSize = 1)
	@GeneratedValue(generator = "GENERATOR_MAE_TIPO_DOCUMENTO", strategy = GenerationType.SEQUENCE)
	@Column(name = "N_TIPO_DOCUMENTO", nullable = false)
	private int idTipoDocumento;

	@Column(name = "X_NOMBRE", nullable = false, length = 35)
	private String nombre;

	@Column(name = "C_TIPO_DOCUMENTO", nullable = false, length = 2)
	private String codigoTipoDocumento;

	@Column(name = "C_REDAM", length = 2)
	private String codigoRedam;

	@Column(name = "C_CONDENAS", length = 2)
	private String codigoCondenas;

	@Column(name = "L_ACTIVO", nullable = false, length = 1)
	private String activo;

}
