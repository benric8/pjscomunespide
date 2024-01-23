package pe.gob.pj.pide.dao.repository;

import java.util.List;

import pe.gob.pj.pide.dao.dto.pide.EntidadDTO;
import pe.gob.pj.pide.dao.dto.pide.EstadoSolicitudDTO;
import pe.gob.pj.pide.dao.dto.pide.IpEntidadDTO;
import pe.gob.pj.pide.dao.dto.pide.OperacionDTO;
import pe.gob.pj.pide.dao.dto.pide.PerfilDTO;
import pe.gob.pj.pide.dao.dto.pide.TipoSolicitudDTO;

public interface MaestrosDao {
	public List<EntidadDTO> listarEntidadActiva(String cuo) throws Exception;
	public List<EntidadDTO> completarEntidadActiva(String cuo, String razonSocial) throws Exception;
	public List<OperacionDTO> listarOperacionActiva(String cuo) throws Exception;
	public List<OperacionDTO> completarOperacionActiva(String cuo, String operacion) throws Exception;
	public List<IpEntidadDTO> listarIpsEntidad(String cuo, Integer idEntidad) throws Exception;
	public List <EstadoSolicitudDTO> listarEstadosSolicitud(String cuo, String paraEvaluacion) throws Exception;
	public List<TipoSolicitudDTO> listarTiposSolicitud(String cuo) throws Exception;
	public List<PerfilDTO> listarPerfilActivo(String cuo) throws Exception;
}
