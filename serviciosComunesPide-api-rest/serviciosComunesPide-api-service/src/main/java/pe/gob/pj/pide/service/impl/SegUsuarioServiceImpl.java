package pe.gob.pj.pide.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.gob.pj.pide.dao.DataSourceConfig;
import pe.gob.pj.pide.dao.repository.SegAudServicioDao;
import pe.gob.pj.pide.dao.utils.ConfiguracionPropiedades;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;
import pe.gob.pj.pide.dao.utils.UtilsSCPide;
import pe.gob.pj.pide.service.SegUsuarioService;
import pe.gob.pj.seguridad.client.dto.RequestLoginDTO;
import pe.gob.pj.seguridad.client.dto.RequestValidaMetodoDTO;
import pe.gob.pj.seguridad.client.dto.ResponseAccesoMetodoDTO;
import pe.gob.pj.seguridad.client.dto.ResponseUsuarioDTO;
import pe.gob.pj.seguridad.client.dto.SeguridadDTO;
import pe.gob.pj.seguridad.client.dto.TokenDTO;
import pe.gob.pj.seguridad.client.service.ClientSeguridadService;

@Service("segUsuarioService")
public class SegUsuarioServiceImpl implements SegUsuarioService, Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(SegUsuarioServiceImpl.class);
		
	@Autowired
	private SegAudServicioDao segAudServicioDao;

	@Autowired
	private ClientSeguridadService clientSeguridadService;

	@Autowired
	private DataSourceConfig dataSourceConfig; 
	
	private String idAplicativo = ConfiguracionPropiedades.getInstance().getProperty(ConstantesSCPide.Seguridad.ID_APLICATIVO);
	
	//Indicados en el propertis de ws seguridad, son iguales
	private String usuarioAuthSeguridad = ConfiguracionPropiedades.getInstance().getProperty(ConstantesSCPide.Seguridad.USUARIO_AUTH_SEGURIDAD);
	private String passwordAuthSeguridad = ConfiguracionPropiedades.getInstance().getProperty(ConstantesSCPide.Seguridad.PASSWORD_AUTH_SEGURIDAD);
	
	//Para consumir api seguridad
	private String usuarioAuthSeguridadCliente = ConfiguracionPropiedades.getInstance().getProperty(ConstantesSCPide.Seguridad.USUARIO_AUTH_SEGURIDAD_CLIENTE);
	private String passwordAuthSeguridadCliente = ConfiguracionPropiedades.getInstance().getProperty(ConstantesSCPide.Seguridad.PASSWORD_AUTH_SEGURIDAD_CLIENTE);
	
	@Override
	public String autenticarUsuario(String codigoCliente, String codigoRol, String usuario, String clave, String cuo) throws Exception {
		String idUsuario = null;
		Long tiempoInicial = System.currentTimeMillis();
		try {
			RequestLoginDTO login = new RequestLoginDTO();
			login.setClave(clave);
			login.setCodigoCliente(codigoCliente);
			login.setCodigoRol(codigoRol);
			login.setCodigoUsuario(usuario);
			login.setIdAplicativo(Integer.parseInt(idAplicativo));		
			
			SeguridadDTO seg = new SeguridadDTO();
		    seg.setUsuarioSpring(usuarioAuthSeguridad);
		    seg.setPasswordSpring(passwordAuthSeguridad);
		    seg.setUsername(usuarioAuthSeguridadCliente);
		    seg.setPassword(passwordAuthSeguridadCliente);
			
		    TokenDTO token = clientSeguridadService.recuperaToken(cuo, seg);
		    segAudServicioDao.setToken(token);
		    ResponseUsuarioDTO result = clientSeguridadService.atenticaUsuario(cuo, login, token.getAccess_token());
		    if (result.equals(null) || result.getIdUsuario() == null) {
				result =null;
				throw new Exception("Resultado nulo o vacio");
			} else {
				idUsuario = result.getIdUsuario().toString();
			}
		} catch (Exception ex) {
			logger.error("{} error al autenticar usuario: {}", cuo , UtilsSCPide.isNull(ex.getCause()).concat(ex.getMessage()));
			logger.fatal(cuo, ex);
			throw ex;
		} finally {
			logger.info("{} ****** PROCESO SRV M-SEG 01 DURACIÓN=[{}(ms)] *******", cuo, (System.currentTimeMillis() - tiempoInicial));
		}
		return idUsuario;
	}

	@Override
	public boolean validarAccesoMetodo(String usuario, String rol, String ruta, String cuo) throws Exception {
		List<ResponseAccesoMetodoDTO> accesosList = segAudServicioDao.getAccesos();
		if(accesosList != null || (accesosList == null? 0: accesosList.size()) > 0) {
			long total = accesosList.stream().filter(x -> x.getCodigoRol().equals(rol) && x.getApi().equals(ruta) && x.getCodigoUsuario().equals(usuario)).count();
			if(total > 0L) {
				return Boolean.TRUE;
			}
		}
		RequestValidaMetodoDTO val = new RequestValidaMetodoDTO();
		val.setApi(ruta);
		val.setCodigoRol(rol);
		val.setCodigoUsuario(usuario);
		val.setIdAplicativo(UtilsSCPide.isInt(idAplicativo));

		SeguridadDTO seg = new SeguridadDTO();
		seg.setPasswordSpring(passwordAuthSeguridad);
		seg.setUsuarioSpring(usuarioAuthSeguridad);

		TokenDTO token = clientSeguridadService.refreshToken(cuo, segAudServicioDao.getToken().getRefresh_token(), seg);
		segAudServicioDao.setToken(token);
		List<ResponseAccesoMetodoDTO> accesos = clientSeguridadService.validarAccesoMetodo(cuo, val, token.getAccess_token());
		if ((accesos == null ? 0 : accesos.size()) > 0) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
	
	@Override
	public boolean getToken(String cuo) {
		try {
			SeguridadDTO seg = new SeguridadDTO();
			seg.setPasswordSpring(dataSourceConfig.getPasswordAuthSeguridad());
			seg.setUsuarioSpring(dataSourceConfig.getUsuarioAuthSeguridad());

			TokenDTO token = clientSeguridadService.refreshToken(cuo, dataSourceConfig.getToken().getRefresh_token(), seg);
			dataSourceConfig.setToken(token);
			if(dataSourceConfig.getToken() != null ) {
				if((dataSourceConfig.getToken().getAccess_token() == null ? "": dataSourceConfig.getToken().getAccess_token()).length() > 0 ) {
					return Boolean.TRUE;
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{} Error token no existe: [{}]", cuo, e.getMessage() );
		}
		return Boolean.FALSE;
	}
	
}