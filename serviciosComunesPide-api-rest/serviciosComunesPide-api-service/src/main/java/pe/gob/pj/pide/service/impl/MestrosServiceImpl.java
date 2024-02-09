package pe.gob.pj.pide.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.pj.pide.dao.dto.pide.EntidadDTO;
import pe.gob.pj.pide.dao.dto.pide.EstadoSolicitudDTO;
import pe.gob.pj.pide.dao.dto.pide.IpEntidadDTO;
import pe.gob.pj.pide.dao.dto.pide.OperacionDTO;
import pe.gob.pj.pide.dao.dto.pide.PerfilDTO;
import pe.gob.pj.pide.dao.dto.pide.TipoSolicitudDTO;
import pe.gob.pj.pide.dao.repository.MaestrosDao;
import pe.gob.pj.pide.service.MaestrosService;

@Service("mestrosService")
public class MestrosServiceImpl implements MaestrosService, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MaestrosDao dao;

	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = { Exception.class, SQLException.class})
	public List<EntidadDTO> listarEntidadActiva(String cuo) throws Exception {
		return dao.listarEntidadActiva(cuo);
	}

	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = { Exception.class, SQLException.class})
	public List<EntidadDTO> completarEntidadActiva(String cuo, String razonSocial) throws Exception {
		return dao.completarEntidadActiva(cuo, razonSocial);
	}

	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = { Exception.class, SQLException.class})
	public List<OperacionDTO> listarOperacionActiva(String cuo) throws Exception {
		return dao.listarOperacionActiva(cuo);
	}

	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = { Exception.class, SQLException.class})
	public List<OperacionDTO> completarOperacionActiva(String cuo, String operacion) throws Exception {
		return dao.completarOperacionActiva(cuo, operacion);
	}

	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = { Exception.class, SQLException.class})
	public List<IpEntidadDTO> listarIpsEntidad(String cuo, Integer idEntidad) throws Exception {
		return dao.listarIpsEntidad(cuo, idEntidad);
	}

	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = { Exception.class, SQLException.class})
	public List<EstadoSolicitudDTO> listarEstadosSolicitud(String cuo, String paraEvaluacion) throws Exception {
		return dao.listarEstadosSolicitud(cuo, paraEvaluacion);
	}

	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = { Exception.class, SQLException.class})
	public List<TipoSolicitudDTO> listarTiposSolicitud(String cuo) throws Exception {
		return dao.listarTiposSolicitud(cuo);
	}

	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = { Exception.class, SQLException.class})
	public List<PerfilDTO> listarPerfilActivo(String cuo) throws Exception {
		return dao.listarPerfilActivo(cuo);
	}
	
	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = { Exception.class, SQLException.class})
	public List<OperacionDTO> listarOperacion(String cuo) throws Exception {
		return dao.listarOperacion(cuo);
	}


}
