package pe.gob.pj.pide.dao.repository;

import java.util.List;

import pe.gob.pj.seguridad.client.dto.ResponseAccesoMetodoDTO;
import pe.gob.pj.seguridad.client.dto.TokenDTO;

public interface SegAudServicioDao {
	
	public void setToken(TokenDTO token);
	
	public TokenDTO getToken();
	
	public List<ResponseAccesoMetodoDTO> getAccesos();

}
