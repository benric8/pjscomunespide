package pe.gob.pj.pide.dao.entity.pide;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "MOV_CONSUMO_CUOTA", schema = ConstantesSCPide.ESQUEMA_PIDE)
@NamedQuery(name = MovConsumoCuota.Q_CONSUMO_CUOTA_BY_RUC_OPERACION_FECHA, query = "SELECT mcc FROM MovConsumoCuota mcc JOIN mcc.asignacionAcceso asig "
		+ "WHERE mcc.mccuota.fechaConsumo = :fechaConsumo AND asig.entidad.ruc = :nroRuc AND asig.operacion.operacion = :operacion ")
public class MovConsumoCuota extends Auditoria implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String Q_CONSUMO_CUOTA_BY_RUC_OPERACION_FECHA = "MovConsumoCuota.findByRucOperacionFecha";

	public static final String P_RUC = "nroRuc";
	public static final String P_OPERACION = "operacion";
	public static final String P_FECHA_CONSUMO = "fechaConsumo";

	@EmbeddedId
	private MovConsumoCuotaId mccuota = new MovConsumoCuotaId();

	@ManyToOne
	@JoinColumns(value = { @JoinColumn(name = "N_ENTIDAD", insertable = false, updatable = false),
			@JoinColumn(name = "N_OPERACION", insertable = false, updatable = false) })
	private MovAsignacionAcceso asignacionAcceso;

	@Column(name = "N_CONSUMO_CUOTA")
	private Integer cuotaConsumida;

}
