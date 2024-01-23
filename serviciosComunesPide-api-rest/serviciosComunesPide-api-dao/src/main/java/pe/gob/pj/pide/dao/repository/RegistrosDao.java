package pe.gob.pj.pide.dao.repository;

import pe.gob.pj.pide.dao.dto.RequestEvaluarSolicitudCuotaDTO;
import pe.gob.pj.pide.dao.dto.RequestModificarPermisoDTO;
import pe.gob.pj.pide.dao.dto.RequestRegistrarEntidadDTO;
import pe.gob.pj.pide.dao.dto.RequestRegistrarIpEntidadDTO;
import pe.gob.pj.pide.dao.dto.RequestRegistrarSolicitudCuotaDTO;
import pe.gob.pj.pide.dao.dto.pide.SolicitudDTO;

public interface RegistrosDao {
	public SolicitudDTO registrarSolicitudCuota(String cuo, RequestRegistrarSolicitudCuotaDTO solicitud) throws Exception;
	public boolean evaluarSolicitudCuota(String cuo, RequestEvaluarSolicitudCuotaDTO evaluacion) throws Exception;
	public boolean registrarEntidad(String cuo, RequestRegistrarEntidadDTO entidad) throws Exception;
	public boolean registrarIpEntidad(String cuo, RequestRegistrarIpEntidadDTO ip) throws Exception;
	public boolean modificarPermiso(String cuo, RequestModificarPermisoDTO permiso) throws Exception;
}
