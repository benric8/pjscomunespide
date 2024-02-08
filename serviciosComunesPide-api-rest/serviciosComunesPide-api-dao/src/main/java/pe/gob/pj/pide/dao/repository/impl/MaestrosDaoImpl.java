package pe.gob.pj.pide.dao.repository.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import pe.gob.pj.pide.dao.dto.pide.EntidadDTO;
import pe.gob.pj.pide.dao.dto.pide.EstadoSolicitudDTO;
import pe.gob.pj.pide.dao.dto.pide.IpEntidadDTO;
import pe.gob.pj.pide.dao.dto.pide.OperacionDTO;
import pe.gob.pj.pide.dao.dto.pide.PerfilDTO;
import pe.gob.pj.pide.dao.dto.pide.TipoSolicitudDTO;
import pe.gob.pj.pide.dao.entity.pide.MaeEntidad;
import pe.gob.pj.pide.dao.entity.pide.MaeEstadoSolicitud;
import pe.gob.pj.pide.dao.entity.pide.MovIpAcceso;
import pe.gob.pj.pide.dao.entity.pide.MaeOperacion;
import pe.gob.pj.pide.dao.entity.pide.MaePerfil;
import pe.gob.pj.pide.dao.entity.pide.MaeTipoSolicitud;
import pe.gob.pj.pide.dao.repository.MaestrosDao;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;
import pe.gob.pj.pide.dao.utils.UtilsSCPide;

@Component("maestrosDao")
public class MaestrosDaoImpl implements MaestrosDao, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(MaestrosDaoImpl.class);
	
	@Autowired
	@Qualifier("sessionSeguridadPide")
	private SessionFactory sf;

	@Override
	public List<EntidadDTO> listarEntidadActiva(String cuo)  throws Exception{
		List<EntidadDTO> lista = new  ArrayList<>();
		try {
			logger.info("{}Inicio de método:{}",cuo,"listarEntidadActiva");
			TypedQuery<MaeEntidad> query = this.sf.getCurrentSession().createNamedQuery(MaeEntidad.Q_ENTIDAD_BY_ACTIVO, MaeEntidad.class);
			query.getResultStream().forEach(x -> {
				lista.add(new EntidadDTO(x.getIdEntidad(), x.getRuc(), x.getRazonSocial(),x.getActivo()));
			});
		} catch (Exception e) {
			logger.error("{}Error:{}",cuo,e.getMessage());
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public List<EntidadDTO> completarEntidadActiva(String cuo, String razonSocial) throws Exception {
		List<EntidadDTO> lista = new  ArrayList<>();
		try {
			logger.info("{}Inicio de método:{}",cuo,"completarEntidadActiva");
			TypedQuery<MaeEntidad> query = this.sf.getCurrentSession().createNamedQuery(MaeEntidad.Q_ENTIDAD_ACTIVO_BY_RSOCIAL, MaeEntidad.class);
			query.setParameter(MaeEntidad.P_RAZON_SOCIAL, "%"+razonSocial.toUpperCase().trim()+"%");
			query.getResultStream().forEach(x -> {
				lista.add(new EntidadDTO(x.getIdEntidad(), x.getRuc(), x.getRazonSocial(),x.getActivo()));
			});
		} catch (Exception e) {
			logger.error("{}Error:{}",cuo,e.getMessage());
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public List<OperacionDTO> completarOperacionActiva(String cuo, String operacion) throws Exception {
		List<OperacionDTO> lista = new ArrayList<>();
		try {
			logger.info("{}Inicio de método:{}",cuo,"completarOperacionActiva");
			TypedQuery<MaeOperacion> query = this.sf.getCurrentSession().createNamedQuery(MaeOperacion.Q_OPERACION_ACTIVO_BY_OPERACION, MaeOperacion.class);
			query.setParameter(MaeOperacion.P_OPERACION, "%"+operacion.toUpperCase().trim()+"%");
			query.getResultStream().forEach(x -> {
				lista.add(new OperacionDTO(x.getIdOperacion(), x.getNombre()));
			});
		} catch (Exception e) {
			logger.error("{}Error:{}",cuo,e.getMessage());
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public List<OperacionDTO> listarOperacion(String cuo) throws Exception{
		List<OperacionDTO> lista = new ArrayList<>();
		try {
			logger.info("{}Inicio de método:{}",cuo,"listarOperacion");
			TypedQuery<MaeOperacion> query = this.sf.getCurrentSession().createNamedQuery(MaeOperacion.Q_OPERACION, MaeOperacion.class);
			query.getResultStream().forEach(x -> {
				lista.add(new OperacionDTO(x.getIdOperacion(), x.getNombre(), x.getOperacion(),x.getDescripcion(), x.getEndpoint(), x.getCuotaDefecto(),
						x.getRequiereAprobacionAcceso(),x.getRequiereAprobacionCuota(),x.getRequiereAprobacionIps(),x.getRequiereAprobacionEstado(), x.getActivo()));
			});
		} catch (Exception e) {
			logger.error("{}Error:{}",cuo,e.getMessage());
			e.printStackTrace();
		}
		return lista;
	}
	
	@Override
	public List<OperacionDTO> listarOperacionActiva(String cuo) throws Exception{
		List<OperacionDTO> lista = new ArrayList<>();
		try {
			logger.info("{}Inicio de método:{}",cuo,"listarOperacionActiva");
			TypedQuery<MaeOperacion> query = this.sf.getCurrentSession().createNamedQuery(MaeOperacion.Q_OPERACION_BY_ACTIVO, MaeOperacion.class);
			query.getResultStream().forEach(x -> {
				lista.add(new OperacionDTO(x.getIdOperacion(), x.getNombre()));
			});
		} catch (Exception e) {
			logger.error("{}Error:{}",cuo,e.getMessage());
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public List<IpEntidadDTO> listarIpsEntidad(String cuo, Integer idEntidad) throws Exception {
		List<IpEntidadDTO> lista = new ArrayList<>();
		try {
			logger.info("{}Inicio de método:{}",cuo,"listarIpsEntidad");

			this.sf.getCurrentSession().enableFilter(MovIpAcceso.F_BY_ID_ENTIDAD)
				.setParameter(MovIpAcceso.P_ID_ENTIDAD, idEntidad);
			
			TypedQuery<MovIpAcceso> query = this.sf.getCurrentSession().createNamedQuery(MovIpAcceso.Q_IP_BY_FILTERS, MovIpAcceso.class);
			query.getResultStream().forEach(z->{
				lista.add(new IpEntidadDTO(z.getEntidad().getIdEntidad(), z.getIdIpacceso(), z.getIpPublica().trim(), (z.getActivo().equalsIgnoreCase(ConstantesSCPide.ESTADO_ACTIVO) ? "Activo" : "Inactivo")));
			});
			
		} catch (Exception e) {
			logger.error("{}Error:{}",cuo,e.getMessage());
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public List<EstadoSolicitudDTO> listarEstadosSolicitud(String cuo, String paraEvaluacion) throws Exception {
		List<EstadoSolicitudDTO> lista = new ArrayList<>();
		try {
			if(!UtilsSCPide.isNullOrEmpty(paraEvaluacion)) {
				this.sf.getCurrentSession().enableFilter(MaeEstadoSolicitud.F_ESOLICITUD_PARA_EVALUACION)
					.setParameter(MaeEstadoSolicitud.P_ESOLICITUD_PARA_EVALUACION, paraEvaluacion);
			}
			TypedQuery<MaeEstadoSolicitud> query = this.sf.getCurrentSession().createNamedQuery(MaeEstadoSolicitud.Q_ESOLICITUD_FILTERS, MaeEstadoSolicitud.class);
			query.getResultStream().forEach(x->{
				lista.add(new EstadoSolicitudDTO(
						x.getIdEstadoSolicitud(), 
						x.getNombreEstadoSolicitud(), 
						x.getDescripcionEstadoSolicitud(), 
						x.getParaEvaluacion(), 
						(x.getActivo().equalsIgnoreCase(ConstantesSCPide.ESTADO_ACTIVO) ? "Activo":"Inactivo")));
			});
		} catch (Exception e) {
			logger.error("{}Error:{}",cuo,e.getMessage());
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public List<TipoSolicitudDTO> listarTiposSolicitud(String cuo) throws Exception {
		List<TipoSolicitudDTO> lista = new ArrayList<>();
		try {
			TypedQuery<MaeTipoSolicitud> query = this.sf.getCurrentSession().createNamedQuery(MaeTipoSolicitud.Q_TSOLICITUD_FILTERS, MaeTipoSolicitud.class);
			query.getResultStream().forEach(x->{
				lista.add(new TipoSolicitudDTO(
						x.getIdTipoSolicitud(), 
						x.getNombreTipoSolicitud(), 
						x.getDescripcionTipoSolicitud(), 
						(x.getActivo().equalsIgnoreCase(ConstantesSCPide.ESTADO_ACTIVO)? "Activo":"Inactivo"),
						x.getRequiereAcceso(),
						x.getImpacto()));
			});
			
		} catch (Exception e) {
			logger.error("{}Error:{}",cuo,e.getMessage());
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public List<PerfilDTO> listarPerfilActivo(String cuo) throws Exception {
		List<PerfilDTO> lista = new ArrayList<>();
		try {
			this.sf.getCurrentSession().enableFilter(MaePerfil.F_ACTIVO)
				.setParameter(MaePerfil.P_ACTIVO, ConstantesSCPide.ESTADO_ACTIVO);
			TypedQuery<MaePerfil> query_ = this.sf.getCurrentSession().createNamedQuery(MaePerfil.Q_FIND_FILTERS, MaePerfil.class);
			query_.getResultStream().forEach(x->{
				lista.add(new PerfilDTO(
						x.getIdPerfil(), 
						x.getNombre(), 
						x.getDescripcion(), 
						x.getActivo()));
			});
		} catch (Exception e) {
			logger.error("{}Error:{}",cuo,e.getMessage());
			e.printStackTrace();
		}
		return lista;
	}

}
