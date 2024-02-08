package pe.gob.pj.pide.dao.repository.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Filter;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import pe.gob.pj.pide.dao.dto.PaginationDTO;
import pe.gob.pj.pide.dao.dto.ResponseHistorialCuotaDTO;
import pe.gob.pj.pide.dao.dto.pide.AsignacionAccesoDTO;
import pe.gob.pj.pide.dao.dto.pide.DetalleSolicitudDTO;
import pe.gob.pj.pide.dao.dto.pide.IpEntidadDTO;
import pe.gob.pj.pide.dao.dto.pide.SolicitudDTO;
import pe.gob.pj.pide.dao.entity.pide.MaeUsuario;
import pe.gob.pj.pide.dao.entity.pide.MovAsignacionAcceso;
import pe.gob.pj.pide.dao.entity.pide.MovSolicitud;
import pe.gob.pj.pide.dao.repository.BusquedasDao;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;
import pe.gob.pj.pide.dao.utils.UtilsSCPide;

@Component
public class BusquedaDaoImpl implements BusquedasDao, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(BusquedaDaoImpl.class);

	@Autowired
	@Qualifier("sessionSeguridadPide")
	private SessionFactory sf;

	@Override
	public PaginationDTO buscarAsignacionAcceso(String cuo, Map<String, Object> filtros, Integer page, Integer pageSize) throws Exception {
		PaginationDTO pagination = new PaginationDTO();
		List<AsignacionAccesoDTO> lista = new ArrayList<>();
		
		pagination.setPageSize(pageSize);
		pagination.setFirstPage(0);
		
		try {
			logger.info("{} Inicio de :{}", cuo, "buscarAsignacionAcceso");
			if (!UtilsSCPide.isNullOrEmpty(filtros.get("idEntidad")) && Integer.parseInt(String.valueOf(filtros.get("idEntidad"))) > 0) {
				Filter filter1 = this.sf.getCurrentSession().enableFilter(MovAsignacionAcceso.F_ID_ENTIDAD);
				filter1.setParameter(MovAsignacionAcceso.P_ID_ENTIDAD, filtros.get("idEntidad"));
				logger.info("{} Filtro "+MovAsignacionAcceso.P_ID_ENTIDAD+" : {}", cuo, filtros.get("idEntidad"));
			}
			if (!UtilsSCPide.isNullOrEmpty(filtros.get("idOperacion")) && Integer.parseInt(String.valueOf(filtros.get("idOperacion"))) > 0) {
				Filter filter2 = this.sf.getCurrentSession().enableFilter(MovAsignacionAcceso.F_ID_OPERACION);
				filter2.setParameter(MovAsignacionAcceso.P_ID_OPERACION, filtros.get("idOperacion"));
				logger.info("{} Filtro "+MovAsignacionAcceso.P_ID_OPERACION+" : {}", cuo, filtros.get("idOperacion"));
			}
			if (!UtilsSCPide.isNullOrEmpty(filtros.get("estado"))) {
				Filter filter3 = this.sf.getCurrentSession().enableFilter(MovAsignacionAcceso.F_ESTADO);
				filter3.setParameter(MovAsignacionAcceso.P_ESTADO, filtros.get("estado"));
				logger.info("{} Filtro "+MovAsignacionAcceso.P_ESTADO+" : {}", cuo, filtros.get("estado"));
			}
			if (!UtilsSCPide.isNullOrEmpty(filtros.get("fechaDesde")) && !UtilsSCPide.isNullOrEmpty(filtros.get("fechaHasta"))) {
				Filter filter4 = this.sf.getCurrentSession().enableFilter(MovAsignacionAcceso.F_RANGO_FECHA);
				filter4.setParameter(MovAsignacionAcceso.P_FECHA_DESDE, filtros.get("fechaDesde"));
				filter4.setParameter(MovAsignacionAcceso.P_FECHA_HASTA, filtros.get("fechaHasta"));
				logger.info("{} Filtro "+MovAsignacionAcceso.P_FECHA_DESDE+" : {}", cuo, filtros.get("fechaDesde"));
				logger.info("{} Filtro "+MovAsignacionAcceso.P_FECHA_HASTA+" : {}", cuo, filtros.get("fechaHasta"));
			}
			TypedQuery<Long> queryCount = this.sf.getCurrentSession().createNamedQuery(MovAsignacionAcceso.Q_ASIGNACION_COUNT, Long.class);
			Long countResult = queryCount.getSingleResult();
			int lastPage = (int) Math.floor(countResult/pageSize);
			
			page = (page <= lastPage ? page : lastPage);
			
			pagination.setTotalRecords(countResult.intValue());
			pagination.setCurrentPage(page);
			pagination.setLastPage(lastPage);
			
			TypedQuery<MovAsignacionAcceso> query = this.sf.getCurrentSession().createNamedQuery(MovAsignacionAcceso.Q_ASIGNACION_ACCESO_BY_FILTERS, MovAsignacionAcceso.class);
			query.setFirstResult(page*pageSize);
			query.setMaxResults(pageSize);
			query.getResultStream().forEach(x -> {
				lista.add(new AsignacionAccesoDTO(
						x.getEntidad().getIdEntidad(), 
						x.getEntidad().getRuc(),
						x.getEntidad().getRazonSocial(), 
						x.getEntidad().getActivo(), 
						x.getOperacion().getIdOperacion(),
						x.getOperacion().getNombre(), 
						x.getOperacion().getEndpoint(),
						x.getOperacion().getCuotaDefecto(), 
						x.getOperacion().getActivo(), 
						x.getCuotaAsignada(),
						(x.getActivo().equalsIgnoreCase(ConstantesSCPide.ESTADO_ACTIVO)? "Activo" : "Inactivo"), 
						(UtilsSCPide.convertDateToString(x.getFechaRegistro(), ConstantesSCPide.PATTERN_FECHA_DD_MM_YYYY))));
			});
			pagination.setList(lista);

		} catch (Exception e) {
			pagination.setTotalRecords(0);
			pagination.setLastPage(0);
			pagination.setCurrentPage(0);
			logger.error("{}Error en buscarAsignacionAcceso:{}", cuo, e.getMessage());
		}
		return pagination;
	}

	@Override
	public PaginationDTO buscarAsignacionAcceso(String cuo, Map<String, Object> filtros) throws Exception {
		PaginationDTO pagination = new PaginationDTO();
		List<AsignacionAccesoDTO> lista = new ArrayList<>();
		
		try {
			logger.info("{} Inicio de :{}", cuo, "buscarAsignacionAcceso");
			if (!UtilsSCPide.isNullOrEmpty(filtros.get("idEntidad")) && Integer.parseInt(String.valueOf(filtros.get("idEntidad"))) > 0) {
				Filter filter1 = this.sf.getCurrentSession().enableFilter(MovAsignacionAcceso.F_ID_ENTIDAD);
				filter1.setParameter(MovAsignacionAcceso.P_ID_ENTIDAD, filtros.get("idEntidad"));
				logger.info("{} Filtro "+MovAsignacionAcceso.P_ID_ENTIDAD+" : {}", cuo, filtros.get("idEntidad"));
			}
			if (!UtilsSCPide.isNullOrEmpty(filtros.get("idOperacion")) && Integer.parseInt(String.valueOf(filtros.get("idOperacion"))) > 0) {
				Filter filter2 = this.sf.getCurrentSession().enableFilter(MovAsignacionAcceso.F_ID_OPERACION);
				filter2.setParameter(MovAsignacionAcceso.P_ID_OPERACION, filtros.get("idOperacion"));
				logger.info("{} Filtro "+MovAsignacionAcceso.P_ID_OPERACION+" : {}", cuo, filtros.get("idOperacion"));
			}
			if (!UtilsSCPide.isNullOrEmpty(filtros.get("estado"))) {
				Filter filter3 = this.sf.getCurrentSession().enableFilter(MovAsignacionAcceso.F_ESTADO);
				filter3.setParameter(MovAsignacionAcceso.P_ESTADO, filtros.get("estado"));
				logger.info("{} Filtro "+MovAsignacionAcceso.P_ESTADO+" : {}", cuo, filtros.get("estado"));
			}
			if (!UtilsSCPide.isNullOrEmpty(filtros.get("fechaDesde")) && !UtilsSCPide.isNullOrEmpty(filtros.get("fechaHasta"))) {
				Filter filter4 = this.sf.getCurrentSession().enableFilter(MovAsignacionAcceso.F_RANGO_FECHA);
				filter4.setParameter(MovAsignacionAcceso.P_FECHA_DESDE, filtros.get("fechaDesde"));
				filter4.setParameter(MovAsignacionAcceso.P_FECHA_HASTA, filtros.get("fechaHasta"));
				logger.info("{} Filtro "+MovAsignacionAcceso.P_FECHA_DESDE+" : {}", cuo, filtros.get("fechaDesde"));
				logger.info("{} Filtro "+MovAsignacionAcceso.P_FECHA_HASTA+" : {}", cuo, filtros.get("fechaHasta"));
			}
			
			TypedQuery<MovAsignacionAcceso> query = this.sf.getCurrentSession().createNamedQuery(MovAsignacionAcceso.Q_ASIGNACION_ACCESO_BY_FILTERS, MovAsignacionAcceso.class);
			query.getResultStream().forEach(x -> {
				lista.add(new AsignacionAccesoDTO(
						x.getEntidad().getIdEntidad(), 
						x.getEntidad().getRuc(),
						x.getEntidad().getRazonSocial(), 
						x.getEntidad().getActivo(), 
						x.getOperacion().getIdOperacion(),
						x.getOperacion().getNombre(), 
						x.getOperacion().getEndpoint(),
						x.getOperacion().getCuotaDefecto(), 
						x.getOperacion().getActivo(), 
						x.getCuotaAsignada(),
						(x.getActivo().equalsIgnoreCase(ConstantesSCPide.ESTADO_ACTIVO)? "Activo" : "Inactivo"), 
						(UtilsSCPide.convertDateToString(x.getFechaRegistro(), ConstantesSCPide.PATTERN_FECHA_DD_MM_YYYY))));
			});
			pagination.setList(lista);

		} catch (Exception e) {
			logger.error("{}Error en buscarAsignacionAcceso sin paginado:{}", cuo, e.getMessage());
		}
		return pagination;
	}

	@Override
	public PaginationDTO buscarSolicitudCuota(String cuo, Map<String, Object> filtros, Integer page, Integer pageSize)
			throws Exception {
		PaginationDTO pagination = new PaginationDTO();
		List<SolicitudDTO> lista = new ArrayList<>();
		Map<String, Boolean> filtrosActivos = new HashMap<>();
		filtrosActivos.put("idSolicitud", false);
		filtrosActivos.put("idEntidad", false);
		filtrosActivos.put("numeroRuc", false);
		filtrosActivos.put("idOperacion", false);
		filtrosActivos.put("idEstadoSolicitud", false);
		filtrosActivos.put("fechaDesde", false);
		filtrosActivos.put("fechaHasta", false);
		pagination.setPageSize(pageSize);
		pagination.setFirstPage(0);
		try {
			logger.info("{} Inicio de :{}", cuo, "buscarSolicitudCuota");
			StringBuilder stringQuery = new StringBuilder("SELECT mbc FROM MovSolicitud mbc");
			stringQuery.append(" JOIN mbc.entidad me");
			stringQuery.append(" LEFT JOIN mbc.operacion mo");
			stringQuery.append(" JOIN mbc.tipoSolicitud mts");
			stringQuery.append(" JOIN mbc.estadoSolicitud mes");
			stringQuery.append(" WHERE mbc.fechaSolicito BETWEEN : "+MovSolicitud.P_FECHA_DESDE +" AND : "+MovSolicitud.P_FECHA_HASTA);
			if (!UtilsSCPide.isNullOrEmpty(filtros.get("idSolicitud")) && Integer.parseInt(String.valueOf(filtros.get("idSolicitud"))) > 0) {
				stringQuery.append(" AND mbc.idSolicitud =: "+MovSolicitud.P_ID_SOLICITUD);
				filtrosActivos.put("idSolicitud",true);
				//
				
				logger.info("{} Filtro "+MovSolicitud.P_ID_SOLICITUD+" : {}", cuo, filtros.get("idSolicitud"));
			}
			if (!UtilsSCPide.isNullOrEmpty(filtros.get("idEntidad")) && Integer.parseInt(String.valueOf(filtros.get("idEntidad"))) > 0) {
				stringQuery.append(" AND me.idEntidad=: "+MovSolicitud.P_ID_ENTIDAD);
				filtrosActivos.put("idEntidad",true);
				logger.info("{} Filtro "+MovSolicitud.P_ID_ENTIDAD+" : {}", cuo, filtros.get("idEntidad"));
			}
			if (!UtilsSCPide.isNullOrEmpty(filtros.get("numeroRuc"))) {
				stringQuery.append(" AND me.ruc=: "+MovSolicitud.P_RUC);
				filtrosActivos.put("numeroRuc",true);
				logger.info("{} Filtro "+MovSolicitud.P_RUC+" : {}", cuo, filtros.get("idEntidad"));
			}
			if (!UtilsSCPide.isNullOrEmpty(filtros.get("idOperacion")) && Integer.parseInt(String.valueOf(filtros.get("idOperacion"))) > 0) {
				stringQuery.append(" AND mo.idOperacion=:"+MovSolicitud.P_ID_OPERACION);
				filtrosActivos.put("idOperacion",true);
				logger.info("{} Filtro "+MovSolicitud.P_ID_OPERACION+" : {}", cuo, filtros.get("idOperacion"));
			}
			if (!UtilsSCPide.isNullOrEmpty(filtros.get("idEstadoSolicitud")) && Integer.parseInt(String.valueOf(filtros.get("idEstadoSolicitud"))) > 0) {
				stringQuery.append(" AND mes.idEstadoSolicitud=:"+MovSolicitud.P_ID_ESTADO_SOLICITUD);
				filtrosActivos.put("idEstadoSolicitud",true);
				logger.info("{} Filtro "+MovSolicitud.P_ID_ESTADO_SOLICITUD+" : {}", cuo, filtros.get("idEstadoSolicitud"));
			}	
			stringQuery.append(" ORDER BY mbc.idSolicitud DESC");
			
			
		    TypedQuery<MovSolicitud> query = this.sf.getCurrentSession().createQuery(stringQuery.toString(), MovSolicitud.class);
		    query.setParameter(MovSolicitud.P_FECHA_DESDE, filtros.get("fechaDesde"));
		    query.setParameter(MovSolicitud.P_FECHA_HASTA, filtros.get("fechaHasta"));
		    
		    if(filtrosActivos.get("idSolicitud")) {
		    	query.setParameter(MovSolicitud.P_ID_SOLICITUD, filtros.get("idSolicitud"));
		    }
		    if(filtrosActivos.get("idEntidad")) {
		    	query.setParameter(MovSolicitud.P_ID_ENTIDAD, filtros.get("idEntidad"));
		    }
		    if(filtrosActivos.get("numeroRuc")) {
		    	query.setParameter(MovSolicitud.P_RUC, filtros.get("numeroRuc"));
		    }
		    if(filtrosActivos.get("idOperacion")) {
		    	query.setParameter(MovSolicitud.P_ID_OPERACION, filtros.get("idOperacion"));
		    }
		    if(filtrosActivos.get("idEstadoSolicitud")) {
		    	query.setParameter(MovSolicitud.P_ID_ESTADO_SOLICITUD, filtros.get("idEstadoSolicitud"));
		    }
		    
		    Long countResult = query.getResultStream().count();
			int lastPage = (int) Math.floor(countResult/pageSize);
			
			page = (page <= lastPage ? page : lastPage);
			
			pagination.setTotalRecords(countResult.intValue());
			pagination.setCurrentPage(page);
			pagination.setLastPage(lastPage);
		    
		    query.setFirstResult(page*pageSize);
			query.setMaxResults(pageSize);
		    query.getResultStream().forEach(x -> {
					lista.add(new SolicitudDTO(
							x.getIdSolicitud(),
							x.getEntidad().getIdEntidad(),
							x.getEntidad().getRazonSocial(), 
							x.getOperacion()==null ? null : x.getOperacion().getIdOperacion(), 
							x.getOperacion()==null ? null : x.getOperacion().getNombre(), 
							x.getNroDocumentoSolicitante(),
							x.getNombreSolicitante(), 
							x.getJustificacion(), 
							UtilsSCPide.convertDateToString(x.getFechaSolicito(), ConstantesSCPide.PATTERN_FECHA_DD_MM_YYYY_HH_MM), 
							UtilsSCPide.convertDateToString(x.getFechaEvaluacion(), ConstantesSCPide.PATTERN_FECHA_DD_MM_YYYY_HH_MM), 
							x.getCuotaCambio(), 
							( UtilsSCPide.isNullOrEmpty(x.getEstadoCambio())?"" :(x.getEstadoCambio().equalsIgnoreCase(ConstantesSCPide.ESTADO_ACTIVO) ? "Activo" : "Inactivo") ),
							x.getEstadoSolicitud().getNombreEstadoSolicitud(),
							x.getTipoSolicitud().getNombreTipoSolicitud()));
			});
			
			pagination.setList(lista);
		} catch (Exception e) {
			pagination.setTotalRecords(0);
			pagination.setLastPage(0);
			pagination.setCurrentPage(0);
			logger.error("{}Error en buscarSolicitudCuota:{}", cuo, e.getMessage());
		}
		return pagination;
	}

	@Override
	public PaginationDTO buscarSolicitudCuota(String cuo, Map<String, Object> filtros) throws Exception {
		PaginationDTO pagination = new PaginationDTO();
		List<SolicitudDTO> lista = new ArrayList<>();
		Map<String, Boolean> filtrosActivos = new HashMap<>();
		filtrosActivos.put("idSolicitud", false);
		filtrosActivos.put("idEntidad", false);
		filtrosActivos.put("numeroRuc", false);
		filtrosActivos.put("idOperacion", false);
		filtrosActivos.put("idEstadoSolicitud", false);
		filtrosActivos.put("fechaDesde", false);
		filtrosActivos.put("fechaHasta", false);
		try {
			logger.info("{} Inicio de :{}", cuo, "buscarSolicitudCuota");
			
			StringBuilder stringQuery = new StringBuilder("SELECT mbc FROM MovSolicitud mbc");
			stringQuery.append(" JOIN mbc.entidad me");
			stringQuery.append(" LEFT JOIN mbc.operacion mo");
			stringQuery.append(" JOIN mbc.tipoSolicitud mts");
			stringQuery.append(" JOIN mbc.estadoSolicitud mes");
			stringQuery.append(" WHERE mbc.fechaSolicito BETWEEN : "+MovSolicitud.P_FECHA_DESDE +" AND : "+MovSolicitud.P_FECHA_HASTA);
			if (!UtilsSCPide.isNullOrEmpty(filtros.get("idSolicitud")) && Integer.parseInt(String.valueOf(filtros.get("idSolicitud"))) > 0) {
				stringQuery.append(" AND mbc.idSolicitud =: "+MovSolicitud.P_ID_SOLICITUD);
				filtrosActivos.put("idSolicitud",true);
				//
				
				logger.info("{} Filtro "+MovSolicitud.P_ID_SOLICITUD+" : {}", cuo, filtros.get("idSolicitud"));
			}
			if (!UtilsSCPide.isNullOrEmpty(filtros.get("idEntidad")) && Integer.parseInt(String.valueOf(filtros.get("idEntidad"))) > 0) {
				stringQuery.append(" AND me.idEntidad=: "+MovSolicitud.P_ID_ENTIDAD);
				filtrosActivos.put("idEntidad",true);
				logger.info("{} Filtro "+MovSolicitud.P_ID_ENTIDAD+" : {}", cuo, filtros.get("idEntidad"));
			}
			if (!UtilsSCPide.isNullOrEmpty(filtros.get("numeroRuc"))) {
				stringQuery.append(" AND me.ruc=: "+MovSolicitud.P_RUC);
				filtrosActivos.put("numeroRuc",true);
				logger.info("{} Filtro "+MovSolicitud.P_RUC+" : {}", cuo, filtros.get("idEntidad"));
			}
			if (!UtilsSCPide.isNullOrEmpty(filtros.get("idOperacion")) && Integer.parseInt(String.valueOf(filtros.get("idOperacion"))) > 0) {
				stringQuery.append(" AND mo.idOperacion=:"+MovSolicitud.P_ID_OPERACION);
				filtrosActivos.put("idOperacion",true);
				logger.info("{} Filtro "+MovSolicitud.P_ID_OPERACION+" : {}", cuo, filtros.get("idOperacion"));
			}
			if (!UtilsSCPide.isNullOrEmpty(filtros.get("idEstadoSolicitud")) && Integer.parseInt(String.valueOf(filtros.get("idEstadoSolicitud"))) > 0) {
				stringQuery.append(" AND mes.idEstadoSolicitud=:"+MovSolicitud.P_ID_ESTADO_SOLICITUD);
				filtrosActivos.put("idEstadoSolicitud",true);
				logger.info("{} Filtro "+MovSolicitud.P_ID_ESTADO_SOLICITUD+" : {}", cuo, filtros.get("idEstadoSolicitud"));
			}	
			stringQuery.append(" ORDER BY mbc.idSolicitud DESC");
			
			
		    TypedQuery<MovSolicitud> query = this.sf.getCurrentSession().createQuery(stringQuery.toString(), MovSolicitud.class);
		    query.setParameter(MovSolicitud.P_FECHA_DESDE, filtros.get("fechaDesde"));
		    query.setParameter(MovSolicitud.P_FECHA_HASTA, filtros.get("fechaHasta"));
		    if(filtrosActivos.get("idSolicitud")) {
		    	query.setParameter(MovSolicitud.P_ID_SOLICITUD, filtros.get("idSolicitud"));
		    }
		    if(filtrosActivos.get("idEntidad")) {
		    	query.setParameter(MovSolicitud.P_ID_ENTIDAD, filtros.get("idEntidad"));
		    }
		    if(filtrosActivos.get("numeroRuc")) {
		    	query.setParameter(MovSolicitud.P_RUC, filtros.get("numeroRuc"));
		    }
		    if(filtrosActivos.get("idOperacion")) {
		    	query.setParameter(MovSolicitud.P_ID_OPERACION, filtros.get("idOperacion"));
		    }
		    if(filtrosActivos.get("idEstadoSolicitud")) {
		    	query.setParameter(MovSolicitud.P_ID_ESTADO_SOLICITUD, filtros.get("idEstadoSolicitud"));
		    }
		    
		    query.getResultStream().forEach(x -> {
					lista.add(new SolicitudDTO(
							x.getIdSolicitud(),
							x.getEntidad().getIdEntidad(),
							x.getEntidad().getRazonSocial(), 
							x.getOperacion()==null ? null : x.getOperacion().getIdOperacion(), 
							x.getOperacion()==null ? null : x.getOperacion().getNombre(), 
							x.getNroDocumentoSolicitante(),
							x.getNombreSolicitante(), 
							x.getJustificacion(), 
							UtilsSCPide.convertDateToString(x.getFechaSolicito(), ConstantesSCPide.PATTERN_FECHA_DD_MM_YYYY_HH_MM), 
							UtilsSCPide.convertDateToString(x.getFechaEvaluacion(), ConstantesSCPide.PATTERN_FECHA_DD_MM_YYYY_HH_MM), 
							x.getCuotaCambio(), 
							( UtilsSCPide.isNullOrEmpty(x.getEstadoCambio())?"" :(x.getEstadoCambio().equalsIgnoreCase(ConstantesSCPide.ESTADO_ACTIVO) ? "Activo" : "Inactivo") ),
							x.getEstadoSolicitud().getNombreEstadoSolicitud(),
							x.getTipoSolicitud().getNombreTipoSolicitud()));
			});
			
			pagination.setList(lista);
		} catch (Exception e) {
			logger.error("{}Error en buscarSolicitudCuota sin paginado: {}", cuo, e.getMessage());
		}
		return pagination;
	}
	
	@Override
	public DetalleSolicitudDTO buscarDetalleSolicitud(String cuo, Integer idSolicitud) throws Exception {
		DetalleSolicitudDTO detalle = null;
		
		this.sf.getCurrentSession().enableFilter(MovSolicitud.F_ID_SOLICITUD)
			.setParameter(MovSolicitud.P_ID_SOLICITUD, idSolicitud);
		
		Query query = this.sf.getCurrentSession().createNamedQuery(MovSolicitud.Q_DETALLE_SOLICITUD_BY_FILTER);
		@SuppressWarnings("unchecked")
		Map<String, Object> object =  (Map<String, Object>) query.getResultStream().findFirst().orElse(null);
		if(object!=null) {
			detalle = new DetalleSolicitudDTO();
			MovSolicitud solicitud = (MovSolicitud) object.get("solicitud");
			MaeUsuario usuarioRegistro =  (MaeUsuario) object.get("usuarioRegistro");
			MaeUsuario usuarioAprobo =  (MaeUsuario) object.get("usuarioAprobo");
			MovAsignacionAcceso permiso = (MovAsignacionAcceso) object.get("permiso");
			if(solicitud!=null) {
				detalle.setIdTipoSolicitud(solicitud.getTipoSolicitud().getIdTipoSolicitud());
				detalle.setNombreTipoSolicitud(solicitud.getTipoSolicitud().getNombreTipoSolicitud());
				detalle.setIdEstadoSolicitud(solicitud.getEstadoSolicitud().getIdEstadoSolicitud());
				detalle.setNombreEstadoSolicitud(solicitud.getEstadoSolicitud().getNombreEstadoSolicitud());
				detalle.setIdSolicitud(solicitud.getIdSolicitud());
				detalle.setFechaRegistro(UtilsSCPide.convertDateToString(solicitud.getFechaSolicito(), ConstantesSCPide.PATTERN_FECHA_DD_MM_YYYY_HH_MM));
				detalle.setFechaEvaluacion(UtilsSCPide.convertDateToString(solicitud.getFechaEvaluacion(), ConstantesSCPide.PATTERN_FECHA_DD_MM_YYYY_HH_MM));
				detalle.setCuotaCambio(solicitud.getCuotaCambio()==null? solicitud.getOperacion().getCuotaDefecto() :solicitud.getCuotaCambio());
				detalle.setEstadoCambio(UtilsSCPide.isNullOrEmpty(solicitud.getEstadoCambio())? "" : (solicitud.getEstadoCambio().equalsIgnoreCase(ConstantesSCPide.ESTADO_ACTIVO)?"Activo":"Inactivo"));
				detalle.setJustificacion(solicitud.getJustificacion());
				detalle.setSolicitante(solicitud.getNombreSolicitante());
				List<IpEntidadDTO> listaIps = new ArrayList<>();
				solicitud.getListaIps().forEach(y->{
					listaIps.add(new IpEntidadDTO(solicitud.getEntidad().getIdEntidad(), y.getIdIp(), y.getIpPublica().trim(), y.getActivo()));
				});
				detalle.setListaIpSolicitud(listaIps);
			}
			if(usuarioRegistro!=null) {
				detalle.setUsuarioRegistro(usuarioRegistro.getApellidosNombres());
			}
			if(usuarioAprobo!=null) {
				detalle.setUsuarioEvaluo(usuarioAprobo.getApellidosNombres());
			}
			if(permiso!=null) {
				detalle.setEstadoActual(permiso.getActivo().equalsIgnoreCase(ConstantesSCPide.ESTADO_ACTIVO)?"Activo":"Inactivo");
				detalle.setCuotaActual(permiso.getCuotaAsignada());
			}
		}
		return detalle;
	}

	@Override
	public ResponseHistorialCuotaDTO historialCuota(String cuo, Integer idEntidad, Integer idOperacion, Integer anio, Integer mes)
			throws Exception {

		ResponseHistorialCuotaDTO historial = new ResponseHistorialCuotaDTO();
		
		StringBuilder query = new StringBuilder("");
		query.append(" SELECT ");
		query.append(" to_char(mcc.mccuota.fechaConsumo,'MM') AS mes, ");
		query.append(" to_char(mcc.mccuota.fechaConsumo,'DD') AS dia, ");
		query.append(" mcc.cuotaConsumida AS consumoDia, ");
		query.append(" ( SELECT ms.cuotaCambio ");
		query.append(" FROM ");
		query.append(" MovSolicitud ms");
		query.append(" JOIN ms.tipoSolicitud smts ");
		query.append(" JOIN ms.estadoSolicitud smes ");
		query.append(" WHERE upper(smts.impacto) in ('ACCESO','CUOTA') AND upper(smes.nombreEstadoSolicitud) = 'APROBADO' ");
		query.append(" AND ms.entidad.idEntidad=mcc.mccuota.idEntidad AND ms.operacion.idOperacion=mcc.mccuota.idOperacion ");
		query.append(" AND (to_char(ms.fechaEvaluacion, 'YYYYMMDD') <= to_char(mcc.mccuota.fechaConsumo,'YYYYMMDD')) ");
		query.append(" AND ms.idSolicitud = ");
		query.append(" ( SELECT MAX(ms2.idSolicitud) FROM MovSolicitud ms2 ");
		query.append(" JOIN ms2.tipoSolicitud smts2 ");
		query.append(" JOIN ms2.estadoSolicitud smes2 ");
		query.append(" WHERE UPPER(smts2.impacto) in ('ACCESO','CUOTA') AND UPPER(smes2.nombreEstadoSolicitud) = 'APROBADO' ");
		query.append(" AND ms2.entidad.idEntidad=mcc.mccuota.idEntidad AND ms2.operacion.idOperacion=mcc.mccuota.idOperacion ");
		query.append(" AND to_char(ms2.fechaEvaluacion, 'YYYYMMDD') <= to_char(mcc.mccuota.fechaConsumo,'YYYYMMDD')) ");
		query.append(" ORDER BY ms.idSolicitud DESC ) AS cuotaLimite, ");
		query.append(" to_char(mcc.mccuota.fechaConsumo,'YYYYMMDD') AS fechaConsumo ");
		query.append(" FROM ");
		query.append(" MovConsumoCuota mcc ");
		query.append(" WHERE to_char(mcc.mccuota.fechaConsumo,'YYYY')=:anio AND to_char(mcc.mccuota.fechaConsumo,'MM')=:mes AND mcc.mccuota.idEntidad=:idEntidad AND mcc.mccuota.idOperacion=:idOperacion ");
		query.append(" ORDER BY 1,2 ASC ");
		
		try {
						
			Query query_ = this.sf.getCurrentSession().createQuery(query.toString());
			query_.setParameter("anio", String.valueOf(anio));
			query_.setParameter("mes", String.format("%02d", mes));
			query_.setParameter("idEntidad", idEntidad);
			query_.setParameter("idOperacion", idOperacion);
			
			Calendar fechaTemp = new GregorianCalendar(anio, mes, 1);
			int ultimoDiaMes = fechaTemp.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			@SuppressWarnings("unchecked")
			List<Object[]> lista = query_.getResultList();
						
			//Recupera solicitud de registro o cambio de cuota
			this.sf.getCurrentSession().enableFilter(MovSolicitud.F_ID_ENTIDAD)
				.setParameter(MovSolicitud.P_ID_ENTIDAD, idEntidad);
			
			this.sf.getCurrentSession().enableFilter(MovSolicitud.F_ID_OPERACION)
				.setParameter(MovSolicitud.P_ID_OPERACION, idOperacion);
			
			this.sf.getCurrentSession().enableFilter(MovSolicitud.F_ID_ESTADO_SOLICITUD)
				.setParameter(MovSolicitud.P_ID_ESTADO_SOLICITUD, ConstantesSCPide.ESTADO_SOLICITUD_ID_APROBADO);

			this.sf.getCurrentSession().enableFilter(MovSolicitud.F_IDS_TIPO_SOLICITUD)
				.setParameterList(MovSolicitud.P_IDS_TIPO_SOLICITUD, new Object[] {ConstantesSCPide.TIPO_SOLICITUD_ID_ACCESO, ConstantesSCPide.TIPO_SOLICITUD_ID_CUOTA});
			
	//		this.sf.getCurrentSession().enableFilter(MovSolicitud.F_TIPO_IMPACTO)
	//			.setParameter(MovSolicitud.P_TIPO_IMPACTO, ConstantesSCPide.TIPO_SOLICITUD_IMPACTO_ACCESO);
	//		
	//		this.sf.getCurrentSession().enableFilter(MovSolicitud.F_ESTADO_NOMBRE)
	//			.setParameter(MovSolicitud.P_ESTADO_NOMBRE, "APROBADO");
			
			TypedQuery<MovSolicitud> query_solicitud = this.sf.getCurrentSession().createNamedQuery(MovSolicitud.Q_SOLICITUD_BY_FILTER, MovSolicitud.class);
			List<MovSolicitud>  listMS = query_solicitud.getResultList();
			MovSolicitud soliBase=null, soliUltimo=null;
			if(listMS.size()==1) {
				soliBase = listMS.get(0);
				soliUltimo = listMS.get(0);
			}else {
				soliBase = listMS.get(listMS.size()-1);
				soliUltimo = listMS.get(0);
			}
			
			String fechaBase = soliBase!=null ? UtilsSCPide.convertDateToString(soliBase.getFechaEvaluacion(), ConstantesSCPide.PATTERN_FECHA_YYYYMMDD) : "";	
			String fechaUltimo = soliBase!=null ? UtilsSCPide.convertDateToString(soliUltimo.getFechaEvaluacion(), ConstantesSCPide.PATTERN_FECHA_YYYYMMDD) : "";
		
			String etiquetaMes=UtilsSCPide.getMesLetra(mes);
			List<Integer> consumoDia = new ArrayList<>();
			List<Integer> consumoCuota = new ArrayList<>();
			List<Integer> cuotaAsignada = new ArrayList<>();
			int diasConsumidos = 0;
			
			int diaSiguiente=1;
			String aniomes = String.valueOf(anio)+String.format("%02d", mes);
			String fechaDiaSiguiente = aniomes+String.format("%02d", diaSiguiente);
			int diasSeConsumio = 0;
			Integer ultimaCuotaAsignada = 0;
			for(Object[] obj : lista) {
				int diaRetornado = Integer.parseInt(String.valueOf(obj[1]));
				if(diaSiguiente<=ultimoDiaMes) {
					while(diaRetornado != diaSiguiente) {
						consumoCuota.add(0);
						cuotaAsignada.add(fechaDiaSiguiente.compareTo(fechaBase)>-1 ? (fechaDiaSiguiente.compareTo(fechaUltimo)>-1 ? soliUltimo.getCuotaCambio() : (Integer) obj[3]) : 0);
						consumoDia.add(diaSiguiente);
						diaSiguiente++;
						fechaDiaSiguiente = aniomes+String.format("%02d", diaSiguiente);
					}	
					diasSeConsumio++;
					consumoCuota.add((Integer) obj[2]);
					cuotaAsignada.add(fechaDiaSiguiente.compareTo(fechaBase)>-1 ? (fechaDiaSiguiente.compareTo(fechaUltimo)>-1 ? soliUltimo.getCuotaCambio() : (Integer) obj[3]) : 0);
					consumoDia.add(diaSiguiente);
					diaSiguiente = diaRetornado+1;
					fechaDiaSiguiente = aniomes+String.format("%02d", diaSiguiente);
				}
				ultimaCuotaAsignada = fechaDiaSiguiente.compareTo(fechaBase)>-1 ? (fechaDiaSiguiente.compareTo(fechaUltimo)>-1 ? soliUltimo.getCuotaCambio() : (Integer) obj[3]) : 0;
			}
			diasConsumidos = diasSeConsumio;
			while(diaSiguiente<=ultimoDiaMes) {
				consumoCuota.add(0);
				cuotaAsignada.add(ultimaCuotaAsignada!=0 ? ultimaCuotaAsignada : (fechaDiaSiguiente.compareTo(fechaUltimo)>-1 ? soliUltimo.getCuotaCambio() : 0));
				consumoDia.add(diaSiguiente);
				diaSiguiente++;
				fechaDiaSiguiente = aniomes+String.format("%02d", diaSiguiente);
			}	
			historial.setEtiquetaMes(etiquetaMes);
			historial.setConsumoDia(consumoDia);
			historial.setConsumoCuota(consumoCuota);
			historial.setDiasConsumidos(diasConsumidos);
			historial.setCuotaAsignada(cuotaAsignada);
		} catch (Exception e) {
			logger.error("{} Error: {}", cuo, e.getMessage());
			e.printStackTrace();
		}
		return historial;
	}

}
