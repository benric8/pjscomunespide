package pe.gob.pj.pide.dao.repository;

import pe.gob.pj.pide.dao.entity.pide.MaeEntidad;
import pe.gob.pj.pide.dao.entity.pide.MaeEstadoSolicitud;
import pe.gob.pj.pide.dao.entity.pide.MaeOperacion;
import pe.gob.pj.pide.dao.entity.pide.MaeTipoSolicitud;
import pe.gob.pj.pide.dao.entity.pide.MovAsignacionAcceso;

public interface GeneralDao {
	public MaeEntidad findEntidadById(Integer idEntidad) throws Exception;
	
	public MaeOperacion findOperacionById(Integer idOperacion) throws Exception;
	
	public Long countSolicitudByFilters(Integer idEntidad, Integer idOperacion, Integer idEstado) throws Exception;
	
	public MovAsignacionAcceso findAsignacionByFilters(Integer idEntidad, Integer idOperacion) throws Exception;
	
	public MaeTipoSolicitud findTipoSolicitudById(Integer idTipoSolicitud) throws Exception;
	
	public MaeEstadoSolicitud findEstadoSolicitudById(Integer idEstadoSolicitud) throws Exception;
}
