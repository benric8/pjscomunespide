package pe.gob.pj.pide.dao.repository.impl;

import java.io.Serializable;

import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import pe.gob.pj.pide.dao.entity.pide.MaeEntidad;
import pe.gob.pj.pide.dao.entity.pide.MaeEstadoSolicitud;
import pe.gob.pj.pide.dao.entity.pide.MaeOperacion;
import pe.gob.pj.pide.dao.entity.pide.MaeTipoSolicitud;
import pe.gob.pj.pide.dao.entity.pide.MovAsignacionAcceso;
import pe.gob.pj.pide.dao.entity.pide.MovSolicitud;
import pe.gob.pj.pide.dao.repository.GeneralDao;

@Component("generalDao")
public class GeneralDaoImpl implements GeneralDao, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(GeneralDaoImpl.class);
	
	@Autowired
	@Qualifier("sessionSeguridadPide")
	private SessionFactory sf;

	@Override
	public MaeEntidad findEntidadById(Integer idEntidad) throws Exception {
		this.sf.getCurrentSession().enableFilter(MaeEntidad.F_ID_ENTIDAD)
			.setParameter(MaeEntidad.P_ID_ENTIDAD, idEntidad);
		TypedQuery<MaeEntidad> queryEntidad = this.sf.getCurrentSession().createNamedQuery(MaeEntidad.Q_ENTIDAD_BY_ACTIVO, MaeEntidad.class);
		MaeEntidad maeEntidad = queryEntidad.getResultStream().findFirst().orElse(null);
		return maeEntidad;
	}

	@Override
	public MaeOperacion findOperacionById(Integer idOperacion) throws Exception {
		this.sf.getCurrentSession().enableFilter(MaeOperacion.F_ID_OPERACION)
			.setParameter(MaeOperacion.P_ID_OPERACION, idOperacion);
		TypedQuery<MaeOperacion> queryOperacion = this.sf.getCurrentSession().createNamedQuery(MaeOperacion.Q_OPERACION_BY_ACTIVO, MaeOperacion.class);
		MaeOperacion maeOperacion = queryOperacion.getResultStream().findFirst().orElse(null);
		return maeOperacion;
	}

	@Override
	public Long countSolicitudByFilters(Integer idEntidad, Integer idOperacion, Integer idEstado) throws Exception {
		if(idEntidad!=null && idEntidad>0) {
			this.sf.getCurrentSession().enableFilter(MovSolicitud.F_ID_ENTIDAD)
				.setParameter(MovSolicitud.P_ID_ENTIDAD, idEntidad);
		}
		if(idOperacion!=null && idOperacion>0) {
			this.sf.getCurrentSession().enableFilter(MovSolicitud.F_ID_OPERACION)
				.setParameter(MovSolicitud.P_ID_OPERACION, idOperacion);
		}
		if(idEstado!=null && idEstado>0) {
			this.sf.getCurrentSession().enableFilter(MovSolicitud.F_ID_ESTADO_SOLICITUD)
			.setParameter(MovSolicitud.P_ID_ESTADO_SOLICITUD, idEstado);
		}
		TypedQuery<Long> queryCountSolicitud = this.sf.getCurrentSession().createNamedQuery(MovSolicitud.Q_SOLICITUD_ACTIVO_COUNT_FILTER, Long.class);
		Long count = queryCountSolicitud.getSingleResult();
		
		return count;
	}

	@Override
	public MovAsignacionAcceso findAsignacionByFilters(Integer idEntidad, Integer idOperacion) throws Exception {
		
		this.sf.getCurrentSession().enableFilter(MovAsignacionAcceso.F_ID_ENTIDAD)
				.setParameter(MovAsignacionAcceso.P_ID_ENTIDAD, idEntidad);
		
		
		this.sf.getCurrentSession().enableFilter(MovAsignacionAcceso.F_ID_OPERACION)
				.setParameter(MovAsignacionAcceso.P_ID_OPERACION, idOperacion);
		
		
		logger.info("{} Filtro " + MovAsignacionAcceso.P_ID_ENTIDAD + " : {}", idEntidad);
		logger.info("{} Filtro " + MovAsignacionAcceso.P_ID_OPERACION + " : {}", idOperacion);
		
		TypedQuery<MovAsignacionAcceso> queryPermiso = this.sf.getCurrentSession().createNamedQuery(MovAsignacionAcceso.Q_ASIGNACION_ACCESO_BY_FILTERS, MovAsignacionAcceso.class);
		MovAsignacionAcceso permiso = queryPermiso.getResultStream().findFirst().orElse(null);
		
		return permiso;
	}

	@Override
	public MaeTipoSolicitud findTipoSolicitudById(Integer idTipoSolicitud) throws Exception {
		this.sf.getCurrentSession().enableFilter(MaeTipoSolicitud.F_TSOLICITUD_ID)
			.setParameter(MaeTipoSolicitud.P_TSOLICITUD_ID, idTipoSolicitud);
		TypedQuery<MaeTipoSolicitud> queryTS = this.sf.getCurrentSession().createNamedQuery(MaeTipoSolicitud.Q_TSOLICITUD_FILTERS, MaeTipoSolicitud.class);
		MaeTipoSolicitud maeTipoSolicitud = queryTS.getResultStream().findFirst().orElse(null);
		return maeTipoSolicitud;
	}

	@Override
	public MaeEstadoSolicitud findEstadoSolicitudById(Integer idEstadoSolicitud) throws Exception {
		this.sf.getCurrentSession().enableFilter(MaeEstadoSolicitud.F_ESOLICITUD_ID)
			.setParameter(MaeEstadoSolicitud.P_ESOLICITUD_ID, idEstadoSolicitud);
		TypedQuery<MaeEstadoSolicitud> queryEstado = this.sf.getCurrentSession().createNamedQuery(MaeEstadoSolicitud.Q_ESOLICITUD_FILTERS, MaeEstadoSolicitud.class);
		MaeEstadoSolicitud maeEstado = queryEstado.getResultStream().findFirst().orElse(null);
		return maeEstado;
	}

}
