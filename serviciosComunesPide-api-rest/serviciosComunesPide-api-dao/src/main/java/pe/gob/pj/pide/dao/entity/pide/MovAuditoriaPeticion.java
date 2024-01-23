package pe.gob.pj.pide.dao.entity.pide;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "MOV_AUDITORIA_PETICION", schema = ConstantesSCPide.ESQUEMA_PIDE)
public class MovAuditoriaPeticion extends Auditoria implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SequenceGenerator(name = "GENERATOR_AUD_PETICION", schema = ConstantesSCPide.ESQUEMA_PIDE, sequenceName = "USEQ_MOV_AUDITORIA_PETICION", allocationSize = 1)
	@GeneratedValue(generator = "GENERATOR_AUD_PETICION", strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name="N_AUDITORIA_PETICION", nullable = false)
	private Integer idAuditoriaPeticion;

	@Column(name = "X_OPERACION")
	private String operacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "F_OPERACION")
	private Date fechaOperacion; 
	
	@Column(name = "X_TIPO_DOCUMENTO")
	private String tipoDocumento;
	
	@Column(name = "X_NRO_DOCUMENTO_IDENTIDAD")
	private String nroDocumentoIdentidad;
	
	@Column(name = "X_APELLIDO_PATERNO")
	private String apellidoPaterno;
	
	@Column(name = "X_APELLIDO_MATERNO")
	private String apellidoMaterno;
	
	@Column(name = "X_NOMBRE1")
	private String nombre1;
	
	@Column(name = "X_NOMBRE2")
	private String nombre2;
	
	@Column(name = "X_NOMBRE3")
	private String nombre3;
	
	@Column(name = "X_MOTIVO_CONSULTA")
	private String motivoConsulta;
	
	@Column(name = "X_NRO_PROCESO_ENTIDAD_CONSULTANTE")
	private String nroProcesoEntidadConsultante;
	
	@Column(name = "X_NRO_RUC_ENTIDAD_CONSULTANTE")
	private String nroRucEntidadConsultante;
	
	@Column(name = "X_NRO_IP_ENTIDAD_CONSULTANTE")
	private String nroIpEntidadConsultante;
	
	@Column(name = "X_NRO_DOCUMENTO_CONSULTANTE")
	private String nroDocumentoConsultante;
	
	@Column(name = "X_AUD_IP")
	private String audIp;
	
	@Column(name = "X_AUD_MAC")
	private String audMac;
	
	@Column(name = "X_AUD_NOMBRE_PC")
	private String audNombrePc;
	
	@Column(name = "X_AUD_NOMBRE_USUARIO")
	private String audNombreUsuario;
	
	@Column(name = "X_RPTA_CODIGO")
	private String rptaCodigo;
	
	@Column(name = "X_RPTA_DESCRIPCION")
	private String rptaDescripcion;
	

}
