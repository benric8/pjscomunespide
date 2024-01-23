package pe.gob.pj.pide.dao.repository;

import java.util.Map;

import pe.gob.pj.pide.dao.dto.PaginationDTO;
import pe.gob.pj.pide.dao.dto.ResponseHistorialCuotaDTO;
import pe.gob.pj.pide.dao.dto.pide.DetalleSolicitudDTO;

public interface BusquedasDao {
	public PaginationDTO buscarAsignacionAcceso(String cuo, Map<String, Object> filtros, Integer page, Integer pageSize) throws Exception;
	public PaginationDTO buscarAsignacionAcceso(String cuo, Map<String, Object> filtros) throws Exception;
	public PaginationDTO buscarSolicitudCuota(String cuo, Map<String, Object> filtros, Integer page, Integer pageSize) throws Exception;
	public PaginationDTO buscarSolicitudCuota(String cuo, Map<String, Object> filtros) throws Exception;
	public DetalleSolicitudDTO buscarDetalleSolicitud(String cuo, Integer idSolicitud) throws Exception;
	public ResponseHistorialCuotaDTO historialCuota(String cuo, Integer idEntidad, Integer idOperacion, Integer anio, Integer mes) throws Exception;
}
