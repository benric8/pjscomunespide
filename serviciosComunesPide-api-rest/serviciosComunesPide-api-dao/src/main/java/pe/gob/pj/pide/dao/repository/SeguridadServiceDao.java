package pe.gob.pj.pide.dao.repository;

import java.util.List;

import pe.gob.pj.pide.dao.dto.seguridad.RolDTO;
import pe.gob.pj.pide.dao.dto.seguridad.UsuarioDTO;

public interface SeguridadServiceDao {
	public String autenticarUsuario(String cuo, String codigoCliente, String codigoRol, String usuario, String clave) throws Exception;
	public UsuarioDTO recuperaInfoUsuario(String cuo, String id) throws Exception;
	public List<RolDTO> recuperarRoles(String cuo, String id) throws Exception;
	public String validarAccesoMetodo(String cuo, String usuario, String rol, String operacion) throws Exception;
}
