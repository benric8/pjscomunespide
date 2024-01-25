package pe.gob.pj.pide.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.pj.pide.dao.dto.seguridad.RolDTO;
import pe.gob.pj.pide.dao.dto.seguridad.UsuarioDTO;
import pe.gob.pj.pide.dao.repository.SeguridadServiceDao;
import pe.gob.pj.pide.service.SeguridadService;

@Service("seguridadService")
public class SeguridadServiceImpl implements SeguridadService{
	
	@Autowired
	private SeguridadServiceDao persistence;	
	
	@Override
	@Transactional(transactionManager = "txManagerSeguridad", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = { Exception.class, SQLException.class})
	public String autenticarUsuario(String cuo, String codigoCliente, String codigoRol, String usuario, String clave)
			throws Exception {
		return persistence.autenticarUsuario(cuo, codigoCliente, codigoRol, usuario, clave);
	}

	@Override
	@Transactional(transactionManager = "txManagerSeguridad", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = { Exception.class, SQLException.class})
	public UsuarioDTO recuperaInfoUsuario(String cuo, String id) throws Exception {
		return persistence.recuperaInfoUsuario(cuo, id);
	}

	@Override
	@Transactional(transactionManager = "txManagerSeguridad", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = { Exception.class, SQLException.class})
	public List<RolDTO> recuperarRoles(String cuo, String id) throws Exception {
		return persistence.recuperarRoles(cuo, id);
	}

	@Override
	@Transactional(transactionManager = "txManagerSeguridad", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = { Exception.class, SQLException.class})
	public String validarAccesoMetodo(String cuo, String usuario, String rol, String operacion) throws Exception {
		return persistence.validarAccesoMetodo(cuo, usuario, rol, operacion);
	}
}
