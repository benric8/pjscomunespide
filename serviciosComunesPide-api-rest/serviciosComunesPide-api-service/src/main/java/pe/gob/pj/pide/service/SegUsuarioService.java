package pe.gob.pj.pide.service;

public interface SegUsuarioService {

	public String autenticarUsuario(String codigoCliente, String codigoRol, String usuario, String clave, String cuo) throws Exception;
	
	public boolean validarAccesoMetodo(String usuario, String rol, String ruta, String cuo) throws Exception;
	
	public boolean getToken(String cuo);
	
}
