package pe.gob.pj.pide.service;

import pe.gob.pj.pide.dao.dto.PaginationDTO;
import pe.gob.pj.pide.dao.dto.RequestBusquedaPermisosDTO;
import pe.gob.pj.pide.dao.dto.RequestBusquedaSolicitudDTO;
import pe.gob.pj.pide.dao.dto.ResponseHistorialCuotaDTO;
import pe.gob.pj.pide.dao.dto.ResponseValidarDocumetoIdentidadDTO;
import pe.gob.pj.pide.dao.dto.ResponseValidarRucDTO;
import pe.gob.pj.pide.dao.dto.pide.DetalleSolicitudDTO;

public interface BusquedasService {
	public PaginationDTO buscarAsignaccionAcceso(String cuo, RequestBusquedaPermisosDTO request, Integer page, Integer pageSize) throws Exception;
	public PaginationDTO buscarSolicitudCuota(String cuo, RequestBusquedaSolicitudDTO request, Integer page, Integer pageSize) throws Exception;
	public DetalleSolicitudDTO buscarDetalleSolicitud(String cuo, Integer idSolicitud) throws Exception;
	public ResponseValidarDocumetoIdentidadDTO validarDniReniec(String cuo, String nroDocumento) throws Exception;
	public ResponseValidarRucDTO validarRucSunat(String cuo, String ruc) throws Exception;
	public ResponseHistorialCuotaDTO historialCuota(String cuo, Integer idEntidad, Integer idOperacion, Integer anio, Integer mes) throws Exception;
}
