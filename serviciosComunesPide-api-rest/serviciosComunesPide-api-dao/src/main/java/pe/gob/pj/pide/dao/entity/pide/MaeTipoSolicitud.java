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
@Table(name = "MAE_TIPO_SOLICITUD", schema = ConstantesSCPide.ESQUEMA_PIDE)
@FilterDefs(value = {
		@FilterDef(name=MaeTipoSolicitud.F_TSOLICITUD_ID, parameters = {@ParamDef(name="idTipoSolicitud", type="int")})
})
@Filters(value = {
		@Filter(name = MaeTipoSolicitud.F_TSOLICITUD_ID, condition = "N_TIPO_SOLICITUD = :idTipoSolicitud"),
})
@NamedQueries(value = {
		@NamedQuery(name = MaeTipoSolicitud.Q_TSOLICITUD_FILTERS, query = "SELECT mts FROM MaeTipoSolicitud mts WHERE mts.activo = '1'")
})
public class MaeTipoSolicitud extends Auditoria implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String F_TSOLICITUD_ID = "MaeTipoSolicitud.filterById";

	public static final String Q_TSOLICITUD_FILTERS = "MaeTipoSolicitud.queryByFilters";

	public static final String P_TSOLICITUD_ID = "idTipoSolicitud";
	
	@SequenceGenerator(name = "GENERATOR_TIPO_SOLICITUD", schema = ConstantesSCPide.ESQUEMA_PIDE, sequenceName = "USEQ_MAE_TIPO_SOLICITUD", allocationSize = 1)
	@GeneratedValue(generator = "GENERATOR_TIPO_SOLICITUD",strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name="N_TIPO_SOLICITUD")
	private int idTipoSolicitud;
	
	@Column(name="X_TIPO_SOLICITUD")
	private String nombreTipoSolicitud;
	
	@Column(name="X_DESCRIPCION")
	private String descripcionTipoSolicitud;

	@Column(name="C_IMPACTO")
	private String impacto;

	@Column(name="L_REQUIERE_APROBACION")
	private String aprobacionObligatoria;
	
	@Column(name = "L_REQUIERE_EXISTENCIA_ACCESO")
	private String requiereAcceso;
	
	@Column(name="L_ACTIVO")
	private String activo;

}
