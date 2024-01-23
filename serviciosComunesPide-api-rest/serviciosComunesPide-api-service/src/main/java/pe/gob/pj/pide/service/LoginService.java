package pe.gob.pj.pide.service;

import pe.gob.pj.pide.dao.dto.PaginationDTO;
import pe.gob.pj.pide.dao.dto.RequestBusquedaUsuarioDTO;
import pe.gob.pj.pide.dao.dto.pide.UsuarioDTO;

public interface LoginService {

	public UsuarioDTO login(String cuo, String cUsuario, String contrasena) throws Exception;
	public boolean registrarUsuario(String cuo, UsuarioDTO usuario) throws Exception;
	public boolean modificarUsuario(String cuo, UsuarioDTO usuario) throws Exception;
	
	public boolean cambiarClave(String cuo, String cUsuario, String claveActual, String claveNueva) throws Exception;
	public boolean restablecerClave(String cuo, int idUsuario) throws Exception;
	
	public PaginationDTO buscarUsuario(String cuo, RequestBusquedaUsuarioDTO request, Integer page, Integer pageSize) throws Exception;
	
}
