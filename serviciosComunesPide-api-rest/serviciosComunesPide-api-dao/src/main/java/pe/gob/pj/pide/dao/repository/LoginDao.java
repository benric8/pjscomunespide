package pe.gob.pj.pide.dao.repository;

import java.util.Map;

import pe.gob.pj.pide.dao.dto.PaginationDTO;
import pe.gob.pj.pide.dao.dto.pide.UsuarioDTO;

public interface LoginDao {
	
	public UsuarioDTO login(String cuo, String cUsuario) throws Exception;
	public UsuarioDTO obtenerUsuario(String cuo, Map<String, Object> filtros) throws Exception;
	public boolean registrarUsuario(String cuo, UsuarioDTO usuario) throws Exception;
	public boolean modificarUsuario(String cuo, UsuarioDTO usuario) throws Exception;
	
	public boolean cambiarClave(String cuo, int idUsuario, String claveNueva) throws Exception;
	
	public PaginationDTO buscarUsuario(String cuo, Map<String, Object> filtros, Integer page, Integer pageSize) throws Exception;
	public PaginationDTO buscarUsuario(String cuo, Map<String, Object> filtros) throws Exception;

}
