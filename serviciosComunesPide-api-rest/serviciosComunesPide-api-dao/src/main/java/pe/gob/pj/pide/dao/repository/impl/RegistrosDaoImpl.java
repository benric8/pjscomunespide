package pe.gob.pj.pide.dao.repository.impl;

import java.io.Serializable;

import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Filter;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import pe.gob.pj.pide.dao.dto.RequestEvaluarSolicitudCuotaDTO;
import pe.gob.pj.pide.dao.dto.RequestModificarPermisoDTO;
import pe.gob.pj.pide.dao.dto.RequestRegistrarEntidadDTO;
import pe.gob.pj.pide.dao.dto.RequestRegistrarIpEntidadDTO;
import pe.gob.pj.pide.dao.dto.RequestOperacionDTO;
import pe.gob.pj.pide.dao.dto.RequestRegistrarSolicitudCuotaDTO;
import pe.gob.pj.pide.dao.dto.pide.IpEntidadDTO;
import pe.gob.pj.pide.dao.dto.pide.SolicitudDTO;
import pe.gob.pj.pide.dao.dto.pide.UsuarioDTO;
import pe.gob.pj.pide.dao.entity.pide.MaeEntidad;
import pe.gob.pj.pide.dao.entity.pide.MaeEstadoSolicitud;
import pe.gob.pj.pide.dao.entity.pide.MovIpAcceso;
import pe.gob.pj.pide.dao.entity.pide.MaeOperacion;
import pe.gob.pj.pide.dao.entity.pide.MaeTipoSolicitud;
import pe.gob.pj.pide.dao.entity.pide.MaeUsuario;
import pe.gob.pj.pide.dao.entity.pide.MovAsignacionAcceso;
import pe.gob.pj.pide.dao.entity.pide.MovSolicitud;
import pe.gob.pj.pide.dao.entity.pide.MovSolicitudIp;
import pe.gob.pj.pide.dao.repository.GeneralDao;
import pe.gob.pj.pide.dao.repository.LoginDao;
import pe.gob.pj.pide.dao.repository.RegistrosDao;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;
import pe.gob.pj.pide.dao.utils.UtilsSCPide;

@Component("registrosDao")
public class RegistrosDaoImpl implements RegistrosDao, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(RegistrosDaoImpl.class);

	@Autowired
	@Qualifier("sessionSeguridadPide")
	private SessionFactory sf;

	@Autowired
	private LoginDao loginDao;
	
	@Autowired
	private GeneralDao generalDao;

	@Override
	public SolicitudDTO registrarSolicitudCuota(String cuo, RequestRegistrarSolicitudCuotaDTO solicitud) throws Exception {
		
		SolicitudDTO solicitudRegistrado = new SolicitudDTO();
		
		MovSolicitud soli = new MovSolicitud();
		MaeUsuario usu = new MaeUsuario();
		
		MaeEntidad maeEntidad = generalDao.findEntidadById(solicitud.getIdEntidad());
		MaeOperacion maeOperacion = generalDao.findOperacionById(solicitud.getIdOperacion());
		UsuarioDTO usuario = loginDao.login(cuo, solicitud.getUsuarioRegistro());
		MaeTipoSolicitud maeTipoSolicitud = generalDao.findTipoSolicitudById(solicitud.getIdTiposSolicitud());

		if (maeEntidad == null) {
			throw new Exception("La entidad seleccionada no esta registrado o esta inactivo.");
		}

		if (maeOperacion == null && !maeTipoSolicitud.getImpacto().trim().equalsIgnoreCase(ConstantesSCPide.TIPO_SOLICITUD_IMPACTO_IP)) {
			throw new Exception("La operacion seleccionada no esta registrada o esta inactiva.");
		}

		if (usuario == null) {
			throw new Exception("El usuario no esta registrado o esta inactivo.");
		}
		
		if(maeTipoSolicitud == null) {
			throw new Exception("El tipo solicitud no esta registrado o esta inactivo.");
		}
		
		MovAsignacionAcceso permi = generalDao.findAsignacionByFilters(solicitud.getIdEntidad(), solicitud.getIdOperacion());
		
		if(!UtilsSCPide.isNullOrEmpty(maeTipoSolicitud.getRequiereAcceso())) {
			if(maeTipoSolicitud.getRequiereAcceso().equalsIgnoreCase(ConstantesSCPide.RPTA_0)&&permi!=null) {
				throw new Exception("El tipo solicitud ["+maeTipoSolicitud.getNombreTipoSolicitud()+"] no es valido, ya que ya existe la asignación de permiso para la operación y entidad seleccionadas.");
			}else if(maeTipoSolicitud.getRequiereAcceso().equalsIgnoreCase(ConstantesSCPide.RPTA_1)&&permi==null) {
				throw new Exception("El tipo solicitud ["+maeTipoSolicitud.getNombreTipoSolicitud()+"] requiere asignación de permiso previo para la operación y entidad seleccionada.");
			}
		}
		
		MaeEstadoSolicitud estadoSolicitud = new MaeEstadoSolicitud();
		estadoSolicitud.setIdEstadoSolicitud(ConstantesSCPide.ESTADO_SOLICITUD_ID_PENDIENTE);
		boolean transaccionPermiso = false;
		boolean transaccionIps = false;
		
		switch(maeTipoSolicitud.getImpacto().trim()) {
			case ConstantesSCPide.TIPO_SOLICITUD_IMPACTO_ACCESO:
				permi = new MovAsignacionAcceso();
				transaccionIps = solicitud.getListaIps()!=null && !solicitud.getListaIps().isEmpty();
				if(maeOperacion.getRequiereAprobacionAcceso().equalsIgnoreCase(ConstantesSCPide.RPTA_0)) {
					estadoSolicitud.setIdEstadoSolicitud(ConstantesSCPide.ESTADO_SOLICITUD_ID_APROBADO);
					if(transaccionIps) {
						for(IpEntidadDTO item : solicitud.getListaIps()) {
							MovIpAcceso mip = new MovIpAcceso();
							mip.setEntidad(maeEntidad);
							mip.setIpPublica(item.getIpPublica());
							mip.setActivo(item.getActivo());
							
							mip.setFAud(UtilsSCPide.getFechaActualDate());
							mip.setCAudIp(UtilsSCPide.getIp());
							mip.setCAudMcAddr(UtilsSCPide.getMac());
							mip.setCAudPc(UtilsSCPide.getPc());
							mip.setFechaRegistro(UtilsSCPide.getFechaActualDate());
							if(item.getIdIpAcceso()==0) {
								mip.setCAud(ConstantesSCPide.SQL_ACCION_INSERT);
								this.sf.getCurrentSession().save(mip);
							}else {
								mip.setCAud(ConstantesSCPide.SQL_ACCION_UPDATE);
								mip.setIdIpacceso(item.getIdIpAcceso());
								this.sf.getCurrentSession().update(mip);
							}
						}
					}
											
					permi.getMaacceso().setIdEntidad(maeEntidad.getIdEntidad());
					permi.getMaacceso().setIdOperacion(maeOperacion.getIdOperacion());
					permi.setCAud(ConstantesSCPide.SQL_ACCION_INSERT);
					permi.setCuotaAsignada(maeOperacion.getCuotaDefecto());
					permi.setActivo(ConstantesSCPide.ESTADO_ACTIVO);
					transaccionPermiso = true;
					soli.setFechaEvaluacion(UtilsSCPide.getFechaActualDate());
				}
				solicitud.setCuotaCambio(maeOperacion.getCuotaDefecto());
				solicitud.setEstadoCambio(ConstantesSCPide.ESTADO_ACTIVO);
				
				break;
			case ConstantesSCPide.TIPO_SOLICITUD_IMPACTO_CUOTA:
				if(solicitud.getCuotaCambio() == null || solicitud.getCuotaCambio()<1) {
					logger.warn("{}El tipo solicitud ["+maeTipoSolicitud.getNombreTipoSolicitud()+"] requiere número de cuota valida para cambio.",cuo);
					throw new Exception("El tipo solicitud ["+maeTipoSolicitud.getNombreTipoSolicitud()+"] requiere número de cuota valida para cambio.");
				}
				if(solicitud.getCuotaCambio()==permi.getCuotaAsignada()) {
					logger.warn("{}El tipo solicitud ["+maeTipoSolicitud.getNombreTipoSolicitud()+"] requiere número de cuota de acceso diferente al actual.",cuo);
					throw new Exception("El tipo solicitud ["+maeTipoSolicitud.getNombreTipoSolicitud()+"] requiere número de cuota de acceso diferente al actual.");
				}
				if(maeOperacion.getRequiereAprobacionCuota().equalsIgnoreCase(ConstantesSCPide.RPTA_0)) {
					estadoSolicitud.setIdEstadoSolicitud(ConstantesSCPide.ESTADO_SOLICITUD_ID_APROBADO);
					permi.setCAud(ConstantesSCPide.SQL_ACCION_UPDATE);
					permi.setCuotaAsignada(solicitud.getCuotaCambio());
					transaccionPermiso = true;
					soli.setFechaEvaluacion(UtilsSCPide.getFechaActualDate());
				}
				
				break;
			case ConstantesSCPide.TIPO_SOLICITUD_IMPACTO_IP:
				if(solicitud.getListaIps() == null || solicitud.getListaIps().size()<1) {
					logger.warn("{}El tipo solicitud ["+maeTipoSolicitud.getNombreTipoSolicitud()+"] requiere ips.",cuo);
					throw new Exception("El tipo solicitud ["+maeTipoSolicitud.getNombreTipoSolicitud()+"] requiere ips.");
				}
				if(maeOperacion==null || maeOperacion.getRequiereAprobacionIps().equalsIgnoreCase(ConstantesSCPide.RPTA_0)) {
					estadoSolicitud.setIdEstadoSolicitud(ConstantesSCPide.ESTADO_SOLICITUD_ID_APROBADO);
					for(IpEntidadDTO item : solicitud.getListaIps()) {
						MovIpAcceso mip = new MovIpAcceso();
						mip.setEntidad(maeEntidad);
						mip.setIpPublica(item.getIpPublica());
						mip.setActivo(item.getActivo());
						
						mip.setFAud(UtilsSCPide.getFechaActualDate());
						mip.setCAudIp(UtilsSCPide.getIp());
						mip.setCAudMcAddr(UtilsSCPide.getMac());
						mip.setCAudPc(UtilsSCPide.getPc());
						mip.setFechaRegistro(UtilsSCPide.getFechaActualDate());
						if(item.getIdIpAcceso()==0) {
							mip.setCAud(ConstantesSCPide.SQL_ACCION_INSERT);
							this.sf.getCurrentSession().save(mip);
						}else {
							mip.setCAud(ConstantesSCPide.SQL_ACCION_UPDATE);
							mip.setIdIpacceso(item.getIdIpAcceso());
							this.sf.getCurrentSession().update(mip);
						}
					}
				}
				transaccionIps = true;
				break;
			case ConstantesSCPide.TIPO_SOLICITUD_IMPACTO_ESTADO:
				if(UtilsSCPide.isNullOrEmpty(solicitud.getEstadoCambio())) {
					logger.warn("{}El tipo solicitud ["+maeTipoSolicitud.getNombreTipoSolicitud()+"] requiere estado para cambio.",cuo);
					throw new Exception("El tipo solicitud ["+maeTipoSolicitud.getNombreTipoSolicitud()+"] requiere estado para cambio.");
				}
				if(solicitud.getEstadoCambio().equalsIgnoreCase(permi.getActivo())) {
					logger.warn("{}El tipo solicitud ["+maeTipoSolicitud.getNombreTipoSolicitud()+"] requiere estado de acceso diferente al actual.",cuo);
					throw new Exception("El tipo solicitud ["+maeTipoSolicitud.getNombreTipoSolicitud()+"] requiere estado de acceso diferente al actual.");
				}
				if(maeOperacion.getRequiereAprobacionEstado().equalsIgnoreCase(ConstantesSCPide.RPTA_0)) {
					estadoSolicitud.setIdEstadoSolicitud(ConstantesSCPide.ESTADO_SOLICITUD_ID_APROBADO);
					permi.setCAud(ConstantesSCPide.SQL_ACCION_UPDATE);
					permi.setActivo(solicitud.getEstadoCambio());
					transaccionPermiso = true;
					soli.setFechaEvaluacion(UtilsSCPide.getFechaActualDate());
				}
				break;
		}
		
		usu.setIdUsuario(usuario.getIdUsuario());
		
		soli.setEntidad(maeEntidad);
		soli.setOperacion(maeOperacion);
		soli.setTipoSolicitud(maeTipoSolicitud);
		soli.setEstadoSolicitud(estadoSolicitud);
		soli.setCuotaCambio(solicitud.getCuotaCambio());
		soli.setEstadoCambio(solicitud.getEstadoCambio());
		soli.setJustificacion(solicitud.getJustificacion().trim().toUpperCase());
		soli.setNroDocumentoSolicitante(solicitud.getNroDocumentoSolicitante());
		soli.setNombreSolicitante(solicitud.getNombreSolicitante());
		soli.setUsuarioRegistro(usu);
		soli.setFechaSolicito(UtilsSCPide.getFechaActualDate());
		soli.setActivo(ConstantesSCPide.ESTADO_ACTIVO);

		soli.setCAud(ConstantesSCPide.SQL_ACCION_INSERT);
		soli.setFAud(UtilsSCPide.getFechaActualDate());
		soli.setCAudIp(UtilsSCPide.getIp());
		soli.setCAudMcAddr(UtilsSCPide.getMac());
		soli.setCAudPc(UtilsSCPide.getPc());
		soli.setFechaRegistro(UtilsSCPide.getFechaActualDate());
		
		try {
			logger.info("{}Inicio de : {}", cuo, "dao.registrarSolicitudCuota");	

			if(transaccionPermiso) {
				permi.setFAud(UtilsSCPide.getFechaActualDate());
				permi.setCAudIp(UtilsSCPide.getIp());
				permi.setCAudMcAddr(UtilsSCPide.getMac());
				permi.setCAudPc(UtilsSCPide.getPc());
				permi.setFechaRegistro(UtilsSCPide.getFechaActualDate());
				if(permi.getCAud().equalsIgnoreCase(ConstantesSCPide.SQL_ACCION_INSERT)) {
					this.sf.getCurrentSession().save(permi);
				}else if(permi.getCAud().equalsIgnoreCase(ConstantesSCPide.SQL_ACCION_UPDATE)) {
					this.sf.getCurrentSession().update(permi);
				}
			}
			this.sf.getCurrentSession().save(soli);
			
			if(transaccionIps) {
				for(IpEntidadDTO item : solicitud.getListaIps()) {
					MovSolicitudIp sip = new MovSolicitudIp();
					sip.setIdIp(item.getIdIpAcceso());
					sip.setIpPublica(item.getIpPublica());
					sip.setActivo(item.getActivo());
					sip.setSolicitud(soli);
					
					sip.setCAud(ConstantesSCPide.SQL_ACCION_INSERT);
					sip.setFAud(UtilsSCPide.getFechaActualDate());
					sip.setCAudIp(UtilsSCPide.getIp());
					sip.setCAudMcAddr(UtilsSCPide.getMac());
					sip.setCAudPc(UtilsSCPide.getPc());
					sip.setFechaRegistro(UtilsSCPide.getFechaActualDate());
					
					this.sf.getCurrentSession().save(sip);
				}
			}
			

		} catch (Exception e) {
			logger.error("{}Error en registrarSolicitudCuota :{}", cuo, e.getMessage());
			e.printStackTrace();
		}
		
		solicitudRegistrado.setIdSolicitud(soli.getIdSolicitud());
		solicitudRegistrado.setFechaSolicito(UtilsSCPide.convertDateToString(soli.getFechaRegistro(), ConstantesSCPide.PATTERN_FECHA_DD_MM_YYYY_HH_MM));
		
		return solicitudRegistrado;
	}

	@Override
	public boolean evaluarSolicitudCuota(String cuo, RequestEvaluarSolicitudCuotaDTO evaluacion) throws Exception {
		MovSolicitud soli = new MovSolicitud();
		MovAsignacionAcceso permi;
		MaeUsuario usu = new MaeUsuario();

		logger.info("{}Inicio de : {}", cuo, "dao.evaluarSolicitudCuota");

		UsuarioDTO usuario = loginDao.login(cuo, evaluacion.getUsuarioAprobo());
		
		MaeEstadoSolicitud maeEstado = generalDao.findEstadoSolicitudById(evaluacion.getIdEstadoSolicitud());

		if (usuario == null) {
			throw new Exception("El usuario no esta registrado o esta inactivo.");
		}
		
		if(maeEstado == null) {
			throw new Exception("El estado de solicitud no esta registrado o esta inactivo.");
		}

		// Obtener solicitud
		this.sf.getCurrentSession().enableFilter(MovSolicitud.F_ID_SOLICITUD)
			.setParameter(MovSolicitud.P_ID_SOLICITUD, evaluacion.getIdSolicitud());
		logger.info("{} Filtro " + MovSolicitud.P_ID_SOLICITUD + " : {}", cuo, evaluacion.getIdSolicitud());

		TypedQuery<MovSolicitud> query_solicitud = this.sf.getCurrentSession().createNamedQuery(MovSolicitud.Q_SOLICITUD_BY_FILTER, MovSolicitud.class);
		soli = query_solicitud.getResultStream().findFirst().orElse(null);
		
		if(soli==null) {
			throw new Exception("No se encontro coicidencia con la solicitud enviada.");
		}

		if (soli.getEstadoSolicitud().getIdEstadoSolicitud() == ConstantesSCPide.ESTADO_SOLICITUD_ID_PENDIENTE) {

			usu.setIdUsuario(usuario.getIdUsuario());
			
			soli.setUsuarioAprobo(usu.getIdUsuario());
			soli.setEstadoSolicitud(maeEstado);
			soli.setFechaEvaluacion(UtilsSCPide.getFechaActualDate());
			
			if(maeEstado.getIdEstadoSolicitud() == ConstantesSCPide.ESTADO_SOLICITUD_ID_APROBADO) {
				// Obtener permiso
				permi = generalDao.findAsignacionByFilters(soli.getEntidad().getIdEntidad(), soli.getOperacion().getIdOperacion());
				
				if(!UtilsSCPide.isNullOrEmpty(soli.getTipoSolicitud().getRequiereAcceso())) {
					if(soli.getTipoSolicitud().getRequiereAcceso().equalsIgnoreCase(ConstantesSCPide.RPTA_0)&&permi!=null) {
						throw new Exception("El tipo solicitud ["+soli.getTipoSolicitud().getNombreTipoSolicitud()+"] no es valido, ya que ya existe la asignación de permiso para la operación y entidad seleccionadas.");
					}else if(soli.getTipoSolicitud().getRequiereAcceso().equalsIgnoreCase(ConstantesSCPide.RPTA_1)&&permi==null) {
						throw new Exception("El tipo solicitud ["+soli.getTipoSolicitud().getNombreTipoSolicitud()+"] requiere asignación de permiso previo para la operación y entidad seleccionada.");
					}
				}
				
				permi = permi != null ? permi : new MovAsignacionAcceso();
				
				boolean transaccionPermiso = false;
				if(soli.getTipoSolicitud().getImpacto().trim().equalsIgnoreCase(ConstantesSCPide.TIPO_SOLICITUD_IMPACTO_ACCESO) && soli.getOperacion().getRequiereAprobacionAcceso().equalsIgnoreCase(ConstantesSCPide.RPTA_1)) {
					
					permi.setCAud(ConstantesSCPide.SQL_ACCION_INSERT);
					permi.getMaacceso().setIdEntidad(soli.getEntidad().getIdEntidad());
					permi.getMaacceso().setIdOperacion(soli.getOperacion().getIdOperacion());
					permi.setCuotaAsignada(soli.getCuotaCambio());
					permi.setActivo(ConstantesSCPide.ESTADO_ACTIVO);
					
					if(soli.getListaIps()!=null && !soli.getListaIps().isEmpty()) {
						for(MovSolicitudIp item : soli.getListaIps()) {
							MovIpAcceso mip = new MovIpAcceso();
							mip.setEntidad(soli.getEntidad());
							mip.setIpPublica(item.getIpPublica());
							mip.setActivo(item.getActivo());
							
							mip.setFAud(UtilsSCPide.getFechaActualDate());
							mip.setCAudIp(UtilsSCPide.getIp());
							mip.setCAudMcAddr(UtilsSCPide.getMac());
							mip.setCAudPc(UtilsSCPide.getPc());
							mip.setFechaRegistro(UtilsSCPide.getFechaActualDate());
							if(item.getIdIp()==0) {
								mip.setCAud(ConstantesSCPide.SQL_ACCION_INSERT);
								this.sf.getCurrentSession().save(mip);
							}else {
								mip.setCAud(ConstantesSCPide.SQL_ACCION_UPDATE);
								mip.setIdIpacceso(item.getIdIp());
								this.sf.getCurrentSession().update(mip);
							}
						}
					}
					transaccionPermiso = true;
				}else if(soli.getTipoSolicitud().getImpacto().trim().equalsIgnoreCase(ConstantesSCPide.TIPO_SOLICITUD_IMPACTO_CUOTA) && soli.getOperacion().getRequiereAprobacionCuota().equalsIgnoreCase(ConstantesSCPide.RPTA_1)) {
					permi.setCAud(ConstantesSCPide.SQL_ACCION_UPDATE);
					permi.setCuotaAsignada(soli.getCuotaCambio());
					transaccionPermiso = true;
				}else if(soli.getTipoSolicitud().getImpacto().trim().equalsIgnoreCase(ConstantesSCPide.TIPO_SOLICITUD_IMPACTO_IP) && soli.getOperacion().getRequiereAprobacionIps().equalsIgnoreCase(ConstantesSCPide.RPTA_1)) {
					
					for(MovSolicitudIp item : soli.getListaIps()) {
						MovIpAcceso mip = new MovIpAcceso();
						mip.setEntidad(soli.getEntidad());
						mip.setIpPublica(item.getIpPublica());
						mip.setActivo(item.getActivo());
						
						mip.setFAud(UtilsSCPide.getFechaActualDate());
						mip.setCAudIp(UtilsSCPide.getIp());
						mip.setCAudMcAddr(UtilsSCPide.getMac());
						mip.setCAudPc(UtilsSCPide.getPc());
						mip.setFechaRegistro(UtilsSCPide.getFechaActualDate());
						if(item.getIdIp()==0) {
							mip.setCAud(ConstantesSCPide.SQL_ACCION_INSERT);
							this.sf.getCurrentSession().save(mip);
						}else {
							mip.setCAud(ConstantesSCPide.SQL_ACCION_UPDATE);
							mip.setIdIpacceso(item.getIdIp());
							this.sf.getCurrentSession().update(mip);
						}
					}
					
				}else if(soli.getTipoSolicitud().getImpacto().trim().equalsIgnoreCase(ConstantesSCPide.TIPO_SOLICITUD_IMPACTO_ESTADO) && soli.getOperacion().getRequiereAprobacionEstado().equalsIgnoreCase(ConstantesSCPide.RPTA_1)) {
					permi.setCAud(ConstantesSCPide.SQL_ACCION_UPDATE);
					permi.setActivo(soli.getEstadoCambio());
				}

				if(transaccionPermiso) {
					permi.setFAud(UtilsSCPide.getFechaActualDate());
					permi.setCAudIp(UtilsSCPide.getIp());
					permi.setCAudMcAddr(UtilsSCPide.getMac());
					permi.setCAudPc(UtilsSCPide.getPc());
					permi.setFechaRegistro(UtilsSCPide.getFechaActualDate());
					
					if(permi.getCAud().equalsIgnoreCase(ConstantesSCPide.SQL_ACCION_UPDATE)) {
						this.sf.getCurrentSession().update(permi);
					}else {
						this.sf.getCurrentSession().save(permi);
					}
				}
			}

			soli.setCAud(ConstantesSCPide.SQL_ACCION_UPDATE);
			soli.setFAud(UtilsSCPide.getFechaActualDate());
			soli.setCAudIp(UtilsSCPide.getIp());
			soli.setCAudMcAddr(UtilsSCPide.getMac());
			soli.setCAudPc(UtilsSCPide.getPc());

			this.sf.getCurrentSession().update(soli);

		} else {
			throw new Exception("No se puede evaluar una solicitud que ya fue evaluada previamente.");
		}

		return soli.getEstadoSolicitud().getIdEstadoSolicitud() == evaluacion.getIdEstadoSolicitud();
	}

	@Override
	public boolean registrarEntidad(String cuo, RequestRegistrarEntidadDTO entidad) throws Exception {
		MaeEntidad enti;
		TypedQuery<MaeEntidad> query = this.sf.getCurrentSession().createNamedQuery(MaeEntidad.Q_ENTIDAD_BY_RUC,
				MaeEntidad.class);
		query.setParameter(MaeEntidad.P_RUC, entidad.getNroRuc());
		enti = query.getResultStream().findFirst().orElse(new MaeEntidad());
		if (enti.getIdEntidad() != null && enti.getIdEntidad() > 0) {
			logger.warn("{} La entidad con el ruc ingresado ya se encuentra registrado.", cuo);
			throw new Exception("La entidad con el ruc ingresado ya se encuentra registrado.");
		} else {
			enti.setRuc(entidad.getNroRuc());
			enti.setRazonSocial(entidad.getRazonSocial().trim().toUpperCase());
			enti.setActivo(entidad.getActivo());

			enti.setCAud(ConstantesSCPide.SQL_ACCION_INSERT);
			enti.setFAud(UtilsSCPide.getFechaActualDate());
			enti.setCAudIp(UtilsSCPide.getIp());
			enti.setCAudMcAddr(UtilsSCPide.getMac());
			enti.setCAudPc(UtilsSCPide.getPc());
			enti.setFechaRegistro(UtilsSCPide.getFechaActualDate());

			this.sf.getCurrentSession().save(enti);
		}
		return enti.getIdEntidad() > 0;
	}

	@Override
	public boolean registrarIpEntidad(String cuo, RequestRegistrarIpEntidadDTO ip) throws Exception {
		MovIpAcceso mip;

		TypedQuery<MovIpAcceso> query = this.sf.getCurrentSession().createNamedQuery(MovIpAcceso.Q_IP_BY_ID_ENTIDAD,MovIpAcceso.class);
		query.setParameter(MovIpAcceso.P_ID_ENTIDAD, ip.getIdEntidad());
		query.setParameter(MovIpAcceso.P_IP, ip.getIpPublica().trim());
		mip = query.getResultStream().findFirst().orElse(new MovIpAcceso());

		if (ip.getOperador().equalsIgnoreCase(ConstantesSCPide.SQL_ACCION_INSERT)) {
			if (mip.getIdIpacceso() != null && mip.getIdIpacceso() > 0) {
				logger.warn("{} La ip publica de la entidad ya se encuentra registrada.", cuo);
				throw new Exception("La ip publica de la entidad ya se encuentra registrada.");
			} else {
				mip.getEntidad().setIdEntidad(ip.getIdEntidad());
				mip.setIpPublica(ip.getIpPublica());
				mip.setActivo(ip.getActivo());

				mip.setCAud(ConstantesSCPide.SQL_ACCION_INSERT);
				mip.setFAud(UtilsSCPide.getFechaActualDate());
				mip.setCAudIp(UtilsSCPide.getIp());
				mip.setCAudMcAddr(UtilsSCPide.getMac());
				mip.setCAudPc(UtilsSCPide.getPc());
				mip.setFechaRegistro(UtilsSCPide.getFechaActualDate());

				this.sf.getCurrentSession().save(mip);
			}

		} else if (ip.getOperador().equalsIgnoreCase(ConstantesSCPide.SQL_ACCION_UPDATE)) {
			if (mip.getIdIpacceso() != null && mip.getIdIpacceso() > 0) {
				mip.setActivo(ip.getActivo());

				mip.setCAud(ConstantesSCPide.SQL_ACCION_INSERT);
				mip.setFAud(UtilsSCPide.getFechaActualDate());
				mip.setCAudIp(UtilsSCPide.getIp());
				mip.setCAudMcAddr(UtilsSCPide.getMac());
				mip.setCAudPc(UtilsSCPide.getPc());
				mip.setFechaRegistro(UtilsSCPide.getFechaActualDate());

				this.sf.getCurrentSession().update(mip);
			} else {
				logger.warn("{} La ip de entidad a modificar aún no se encuentra registrado.", cuo);
				throw new Exception("La ip de entidad a modificar aún no se encuentra registrado.");
			}
		} else {
			logger.warn("{} Operador no permitido.", cuo);
			throw new Exception("Operador no permitido.");
		}

		return true;
	}

	@Override
	public boolean modificarPermiso(String cuo, RequestModificarPermisoDTO permiso) throws Exception {

		boolean rpta = false;
		MovAsignacionAcceso permi = new MovAsignacionAcceso();
		
		Filter f_identidad = this.sf.getCurrentSession().enableFilter(MaeEntidad.F_ID_ENTIDAD);
		f_identidad.setParameter(MaeEntidad.P_ID_ENTIDAD, permiso.getIdEntidad());
		TypedQuery<MaeEntidad> queryEntidad = this.sf.getCurrentSession()
				.createNamedQuery(MaeEntidad.Q_ENTIDAD_BY_ACTIVO, MaeEntidad.class);
		MaeEntidad maeEntidad = queryEntidad.getResultStream().findFirst().orElse(null);

		Filter f_idoperacion = this.sf.getCurrentSession().enableFilter(MaeOperacion.F_ID_OPERACION);
		f_idoperacion.setParameter(MaeOperacion.P_ID_OPERACION, permiso.getIdOperacion());
		TypedQuery<MaeOperacion> queryOperacion = this.sf.getCurrentSession()
				.createNamedQuery(MaeOperacion.Q_OPERACION_BY_ACTIVO, MaeOperacion.class);
		MaeOperacion maeOperacion = queryOperacion.getResultStream().findFirst().orElse(null);

		if (maeEntidad == null || maeOperacion == null) {
			logger.warn("{} No se ha encontrado coincidencia para modificar el permiso a modificar.", cuo);
			throw new Exception("No se ha encontrado coincidencia para modificar el permiso a modificar.");
		}

		Filter filter_b1 = this.sf.getCurrentSession().enableFilter(MovSolicitud.F_ID_ENTIDAD);
		filter_b1.setParameter(MovSolicitud.P_ID_ENTIDAD, permiso.getIdEntidad());

		Filter filter_b2 = this.sf.getCurrentSession().enableFilter(MovSolicitud.F_ID_OPERACION);
		filter_b2.setParameter(MovSolicitud.P_ID_OPERACION, permiso.getIdOperacion());

		Filter filter_b3 = this.sf.getCurrentSession().enableFilter(MovSolicitud.F_ID_ESTADO_SOLICITUD);
		filter_b3.setParameter(MovSolicitud.P_ID_ESTADO_SOLICITUD, ConstantesSCPide.ESTADO_SOLICITUD_ID_PENDIENTE);

		TypedQuery<Long> queryBitacora = this.sf.getCurrentSession().createNamedQuery(MovSolicitud.Q_SOLICITUD_ACTIVO_COUNT_FILTER, Long.class);
		Long count = queryBitacora.getSingleResult();

		if (count.intValue() < 1) {
			logger.warn(
					"{} No se puede realizar cambio de estado del permiso seleccionado porque no cuenta con solicitud aprobada.",
					cuo);
			throw new Exception(
					"No se puede realizar cambio de estado del permiso seleccionado porque no cuenta con solicitud aprobada.");
		}

		try {

			// Obtener permiso
			Filter filter1 = this.sf.getCurrentSession().enableFilter(MovAsignacionAcceso.F_ID_ENTIDAD);
			filter1.setParameter(MovAsignacionAcceso.P_ID_ENTIDAD, permiso.getIdEntidad());

			Filter filter2 = this.sf.getCurrentSession().enableFilter(MovAsignacionAcceso.F_ID_OPERACION);
			filter2.setParameter(MovAsignacionAcceso.P_ID_OPERACION, permiso.getIdOperacion());

			TypedQuery<MovAsignacionAcceso> query_permiso = this.sf.getCurrentSession().createNamedQuery(MovAsignacionAcceso.Q_ASIGNACION_ACCESO_BY_FILTERS, MovAsignacionAcceso.class);
			permi = query_permiso.getResultStream().findFirst().orElse(new MovAsignacionAcceso());
			
			permi.setCAud(ConstantesSCPide.SQL_ACCION_UPDATE);
			permi.setFAud(UtilsSCPide.getFechaActualDate());
			permi.setCAudIp(UtilsSCPide.getIp());
			permi.setCAudMcAddr(UtilsSCPide.getMac());
			permi.setCAudPc(UtilsSCPide.getPc());
			permi.setFechaRegistro(UtilsSCPide.getFechaActualDate());
			
			permi.setActivo(permiso.getActivo());
			
			this.sf.getCurrentSession().update(permi);
			
			rpta = true;

		} catch (Exception e) {
			logger.error("{} Error: {}", cuo, e.getMessage());
			e.printStackTrace();
		}
		return rpta;
	}
	@Override
	public boolean registrarOperacion(String cuo, RequestOperacionDTO operacion) throws Exception {
		MaeOperacion opera;
		TypedQuery<MaeOperacion> query = this.sf.getCurrentSession().createNamedQuery(MaeOperacion.Q_OPERACION_BY_ENDPOINT,
				MaeOperacion.class);
		query.setParameter(MaeOperacion.P_ENDPOINT_OPERACION, operacion.getEndpoint());
		opera = query.getResultStream().findFirst().orElse(new MaeOperacion());
		if (opera.getIdOperacion()!= null && opera.getIdOperacion() > 0) {
			logger.warn("{} EL endpoint ingresado ya se encuentra registrado.", cuo);
			throw new Exception("El endpoint ingresado ya se encuentra registrado.");
		} else {
			opera.setNombre(operacion.getNombre());
			opera.setOperacion(operacion.getOperacion());
			opera.setDescripcion(operacion.getDescripcion());
			opera.setEndpoint(operacion.getEndpoint());
			opera.setCuotaDefecto(operacion.getCuotaDefecto());
			opera.setRequiereAprobacionAcceso(operacion.getRequiereAprobacionAcceso());
			opera.setRequiereAprobacionCuota(operacion.getRequiereAprobacionCuota());
			opera.setRequiereAprobacionEstado(operacion.getRequiereAprobacionEstado());
			opera.setRequiereAprobacionIps(operacion.getRequiereAprobacionIps());
			opera.setActivo(operacion.getActivo());
			
			
			opera.setCAud(ConstantesSCPide.SQL_ACCION_UPDATE);
			opera.setFAud(UtilsSCPide.getFechaActualDate());
			opera.setCAudIp(UtilsSCPide.getIp());
			opera.setCAudMcAddr(UtilsSCPide.getMac());
			opera.setCAudPc(UtilsSCPide.getPc());
			opera.setFechaRegistro(UtilsSCPide.getFechaActualDate());
			
			
			this.sf.getCurrentSession().save(opera);
		}
		return opera.getIdOperacion() > 0;
	}
	
	@Override
	public boolean modificarOperacion(String cuo, RequestOperacionDTO operacion, Integer idOperacion) throws Exception {
		
		
		boolean rpta=false;
		MaeOperacion operacionPrevia;
		this.sf.getCurrentSession().enableFilter(MaeOperacion.F_ID_OPERACION)
		.setParameter(MaeOperacion.P_ID_OPERACION, idOperacion);	
		TypedQuery<MaeOperacion> query = this.sf.getCurrentSession().createNamedQuery(MaeOperacion.Q_FIND_BY_FILTERS,
				MaeOperacion.class);
		
		operacionPrevia = query.getResultStream().findFirst().orElse(new MaeOperacion());
		
		if(operacionPrevia == null)  {
			throw new Exception("No se pudo realizar la modificación debido a que no se encontro coincidencias para la operacion ingresado.");
		}
		
		try {

			operacionPrevia.setOperacion(operacion.getOperacion());
			operacionPrevia.setDescripcion(operacion.getDescripcion());
			operacionPrevia.setEndpoint(operacion.getEndpoint());
			operacionPrevia.setCuotaDefecto(operacion.getCuotaDefecto());
			operacionPrevia.setRequiereAprobacionAcceso(operacion.getRequiereAprobacionAcceso());
			operacionPrevia.setRequiereAprobacionCuota(operacion.getRequiereAprobacionCuota());
			operacionPrevia.setRequiereAprobacionEstado(operacion.getRequiereAprobacionEstado());
			operacionPrevia.setRequiereAprobacionIps(operacion.getRequiereAprobacionIps());
			operacionPrevia.setActivo(operacion.getActivo());
			
			operacionPrevia.setCAud(ConstantesSCPide.SQL_ACCION_UPDATE);
			operacionPrevia.setFAud(UtilsSCPide.getFechaActualDate());
			operacionPrevia.setCAudIp(UtilsSCPide.getIp());
			operacionPrevia.setCAudMcAddr(UtilsSCPide.getMac());
			operacionPrevia.setCAudPc(UtilsSCPide.getPc());
			operacionPrevia.setFechaRegistro(UtilsSCPide.getFechaActualDate());
			
			this.sf.getCurrentSession().update(operacionPrevia);
			
			rpta = operacionPrevia.getIdOperacion()  > 0;
			
		} catch (Exception e) {
			logger.error("{} Error en dao modificarOperacion: {}", cuo, e.getMessage());
			e.printStackTrace();
		}
		
		return rpta;
	}

}
