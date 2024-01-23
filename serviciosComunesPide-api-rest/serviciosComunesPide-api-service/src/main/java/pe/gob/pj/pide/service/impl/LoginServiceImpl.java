package pe.gob.pj.pide.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.pj.pide.dao.dto.PaginationDTO;
import pe.gob.pj.pide.dao.dto.RequestBusquedaUsuarioDTO;
import pe.gob.pj.pide.dao.dto.pide.UsuarioDTO;
import pe.gob.pj.pide.dao.entity.pide.MaeUsuario;
import pe.gob.pj.pide.dao.repository.LoginDao;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;
import pe.gob.pj.pide.dao.utils.EncryptUtils;
import pe.gob.pj.pide.dao.utils.UtilsSCPide;
import pe.gob.pj.pide.service.LoginService;

@Service("loginService")
public class LoginServiceImpl implements LoginService, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(LoginServiceImpl.class);

	@Autowired
	private LoginDao loginDao;

	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, timeout = 120, readOnly = true, rollbackFor = {
			Exception.class, SQLException.class })
	public UsuarioDTO login(String cuo, String cUsuario, String clave) throws Exception {
		try {
			UsuarioDTO usuario = loginDao.login(cuo, cUsuario);
			String password = EncryptUtils.cryptBase64u(clave, Cipher.ENCRYPT_MODE);
			logger.info("{}Password : {}", cuo, password);
			if (UtilsSCPide.isNull(usuario.getClave()).equals(password)) {
				usuario.setClave("***********");
				return usuario;
			} else {
				logger.warn("{} Credenciales de usuario son incorrectas", cuo);
				throw new Exception("Credenciales de usuario son incorrectas");
			}
		} catch (Exception e) {
			logger.error("{} Error al realizar al realizar la busqueda {}", cuo, e.getMessage());
			throw new Exception("Credenciales de usuario son incorrectas");
		}
	}

	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, timeout = 120, readOnly = false, rollbackFor = {
			Exception.class, SQLException.class })
	public boolean cambiarClave(String cuo, String cUsuario, String claveActual, String claveNueva) throws Exception {
		boolean rpta = false;

		UsuarioDTO usuario = loginDao.login(cuo, cUsuario);
		if(usuario!=null) {
			String password = EncryptUtils.cryptBase64u(claveActual, Cipher.ENCRYPT_MODE);
			if (UtilsSCPide.isNull(usuario.getClave()).equalsIgnoreCase(password)) {
				rpta = loginDao.cambiarClave(cuo, usuario.getIdUsuario(), EncryptUtils.cryptBase64u(claveNueva, Cipher.ENCRYPT_MODE));
			} else {
				logger.warn("{} La contraseña actual es incorrecta.", cuo);
				throw new Exception("La contraseña actual es incorrecta.");
			}
		}else {
			logger.warn("{} Usuario ingresado no existe o esta inactivo.", cuo);
			throw new Exception("Usuario ingresado no existe o esta inactivo.");
		}

		return rpta;
	}

	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, timeout = 120, readOnly = false, rollbackFor = {
			Exception.class, SQLException.class })
	public boolean restablecerClave(String cuo, int idUsuario) throws Exception {
		boolean rpta = false;
		
		Map<String, Object> filtros = new HashMap<>();
		filtros.put(MaeUsuario.P_ID_USUARIO, idUsuario);

		UsuarioDTO usuario = loginDao.obtenerUsuario(cuo, filtros);
		if(usuario!=null) {
			rpta = loginDao.cambiarClave(cuo, usuario.getIdUsuario(), EncryptUtils.cryptBase64u(usuario.getUsuario(), Cipher.ENCRYPT_MODE));
		}else {
			logger.warn("{} Usuario ingresado no existe o esta inactivo.", cuo);
			throw new Exception("Usuario ingresado no existe o esta inactivo.");
		}
		return rpta;
	}

	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, timeout = 120, readOnly = false, rollbackFor = {
			Exception.class, SQLException.class })
	public boolean registrarUsuario(String cuo, UsuarioDTO usuario) throws Exception {
		usuario.setClave(EncryptUtils.cryptBase64u(usuario.getUsuario(), Cipher.ENCRYPT_MODE));
		return loginDao.registrarUsuario(cuo, usuario);
	}

	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, timeout = 120, readOnly = false, rollbackFor = {
			Exception.class, SQLException.class })
	public boolean modificarUsuario(String cuo, UsuarioDTO usuario) throws Exception {
		return loginDao.modificarUsuario(cuo, usuario);
	}

	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, timeout = 120, readOnly = false, rollbackFor = {
			Exception.class, SQLException.class })
	public PaginationDTO buscarUsuario(String cuo, RequestBusquedaUsuarioDTO request, Integer page, Integer pageSize)
			throws Exception {
		Map<String, Object> filtros = new HashMap<>();
		filtros.put(MaeUsuario.P_ID_PERFIL, request.getIdPerfil());
		filtros.put(MaeUsuario.P_USUARIO, request.getUsuario());
		filtros.put(MaeUsuario.P_ACTIVO, request.getEstado());
		if(page!=null && pageSize!=null) {
			page = page >= 0 ? page : 0;
			pageSize = pageSize > 0 ? pageSize : ConstantesSCPide.DEFAULT_PAGINATION_PAGE_SIZE;
			return loginDao.buscarUsuario(cuo, filtros, page, pageSize);
		}else {
			return loginDao.buscarUsuario(cuo, filtros);
		}
	}

}
