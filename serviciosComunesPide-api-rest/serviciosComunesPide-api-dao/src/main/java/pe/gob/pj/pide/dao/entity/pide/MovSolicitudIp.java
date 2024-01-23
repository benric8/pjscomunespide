package pe.gob.pj.pide.dao.entity.pide;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;

@Entity
@Table(name = "MOV_SOLICITUD_IP", schema = ConstantesSCPide.ESQUEMA_PIDE)
public class MovSolicitudIp extends Auditoria implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SequenceGenerator(name = "GENERATOR_SOLICITUD_IP", schema = ConstantesSCPide.ESQUEMA_PIDE, sequenceName = "USEQ_MOV_SOLICITUD_IP", allocationSize = 1)
	@GeneratedValue(generator = "GENERATOR_SOLICITUD_IP", strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name = "N_SOLICITUD_IP")
	@Getter @Setter
	private int idSolicitudIp;
	
	@ToString.Exclude
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "N_SOLICITUD")
	@Getter @Setter
	private MovSolicitud solicitud;

	@Column(name = "N_IP_ACCESO")
	@Getter @Setter
	private int idIp;
	
	@Column(name = "X_IP_PUBLICA")
	@Getter @Setter
	private String ipPublica;
	
	@Column(name = "L_ACTIVO")
	@Getter @Setter
	private String activo;

}
