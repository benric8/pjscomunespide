package pe.gob.pj.pide.service;

import java.util.List;

import pe.gob.pj.pide.dao.dto.pide.EntidadDTO;
import pe.gob.pj.pide.dao.dto.pide.EstadoSolicitudDTO;
import pe.gob.pj.pide.dao.dto.pide.IpEntidadDTO;
import pe.gob.pj.pide.dao.dto.pide.OperacionDTO;
import pe.gob.pj.pide.dao.dto.pide.PerfilDTO;
import pe.gob.pj.pide.dao.dto.pide.TipoSolicitudDTO;

public interface MaestrosService {
	
	public List<PerfilDTO> listarPerfilActivo(String cuo) throws Exception;
	
	/**
	 * 
	 * @param cuo
	 * @return Lista de entidades activas
	 * @throws Exception
	 */
	public List<EntidadDTO> listarEntidadActiva(String cuo) throws Exception;

	/**
	 * 
	 * @param cuo
	 * @param razonSocial Razón social de la entidad
	 * @return Lista de entidades activas con razón social similar a la ingresada
	 * @throws Exception
	 */
	public List<EntidadDTO> completarEntidadActiva(String cuo, String razonSocial) throws Exception;
	
	
	/**
	 * 
	 * @param cuo
	 * @return Lista de operaciones activas
	 * @throws Exception
	 */
	public List<OperacionDTO> listarOperacionActiva(String cuo) throws Exception;
	
	/**
	 * 
	 * @param cuo
	 * @param operacion Nombre de óperacion
	 * @return Lista de operaciones activas con operación similar a la ingresada
	 * @throws Exception
	 */
	public List<OperacionDTO> completarOperacionActiva(String cuo, String operacion) throws Exception;
	
	/**
	 * 
	 * @param cuo
	 * @param operacion
	 * @return
	 * @throws Exception
	 */
	public List<IpEntidadDTO> listarIpsEntidad(String cuo, Integer idEntidad) throws Exception;
	
	/**
	 * 
	 * @param cuo
	 * @param paraEvaluacion Flag que indica si el estado sirve como respuesta a evaluación
	 * @return
	 * @throws Exception
	 */
	public List <EstadoSolicitudDTO> listarEstadosSolicitud(String cuo, String paraEvaluacion) throws Exception;
	
	/**
	 * 
	 * @param cuo
	 * @return lista de tipos solicitud
	 * @throws Exception
	 */
	public List<TipoSolicitudDTO> listarTiposSolicitud(String cuo) throws Exception;
	
}
