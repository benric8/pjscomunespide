package pe.gob.pj.pide.service.impl;

import java.io.Serializable;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.pj.pide.dao.dto.RequestEvaluarSolicitudCuotaDTO;
import pe.gob.pj.pide.dao.dto.RequestModificarPermisoDTO;
import pe.gob.pj.pide.dao.dto.RequestRegistrarEntidadDTO;
import pe.gob.pj.pide.dao.dto.RequestRegistrarIpEntidadDTO;
import pe.gob.pj.pide.dao.dto.RequestRegistrarSolicitudCuotaDTO;
import pe.gob.pj.pide.dao.dto.pide.SolicitudDTO;
import pe.gob.pj.pide.dao.repository.RegistrosDao;
import pe.gob.pj.pide.service.RegistrosService;

@Service("registrosService")
public class RegistrosServiceImpl implements RegistrosService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private RegistrosDao dao;

	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, timeout = 120, readOnly = false, rollbackFor = {
			Exception.class, SQLException.class })
	public SolicitudDTO registrarSolicitudCuota(String cuo, RequestRegistrarSolicitudCuotaDTO solicitud) throws Exception {
		return dao.registrarSolicitudCuota(cuo, solicitud);
	}

	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, timeout = 120, readOnly = false, rollbackFor = {
			Exception.class, SQLException.class })
	public boolean evaluarSolicitudCuota(String cuo, RequestEvaluarSolicitudCuotaDTO evaluacion) throws Exception {
		return dao.evaluarSolicitudCuota(cuo, evaluacion);
	}

	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, timeout = 120, readOnly = false, rollbackFor = {
			Exception.class, SQLException.class })
	public boolean registrarEntidad(String cuo, RequestRegistrarEntidadDTO entidad) throws Exception {
		return dao.registrarEntidad(cuo, entidad);
	}

	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, timeout = 120, readOnly = false, rollbackFor = {
			Exception.class, SQLException.class })
	public boolean registrarIpEntidad(String cuo, RequestRegistrarIpEntidadDTO ip) throws Exception {
		return dao.registrarIpEntidad(cuo, ip);
	}

	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, timeout = 120, readOnly = false, rollbackFor = {
			Exception.class, SQLException.class })
	public boolean modificarPermiso(String cuo, RequestModificarPermisoDTO permiso) throws Exception {
		return dao.modificarPermiso(cuo, permiso);
	}

}
