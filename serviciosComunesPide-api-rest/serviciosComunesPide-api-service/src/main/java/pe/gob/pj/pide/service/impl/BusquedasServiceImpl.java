package pe.gob.pj.pide.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.pj.pide.dao.dto.PaginationDTO;
import pe.gob.pj.pide.dao.dto.RequestBusquedaPermisosDTO;
import pe.gob.pj.pide.dao.dto.RequestBusquedaSolicitudDTO;
import pe.gob.pj.pide.dao.dto.ResponseHistorialCuotaDTO;
import pe.gob.pj.pide.dao.dto.ResponseValidarDocumetoIdentidadDTO;
import pe.gob.pj.pide.dao.dto.ResponseValidarRucDTO;
import pe.gob.pj.pide.dao.dto.pide.DetalleSolicitudDTO;
import pe.gob.pj.pide.dao.repository.BusquedasDao;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;
import pe.gob.pj.pide.dao.utils.UtilsSCPide;
import pe.gob.pj.pide.service.BusquedasService;
import pe.gob.pj.pide.ws.ReniecWsService;
import pe.gob.pj.pide.ws.SunatWsService;
import pe.gob.pj.pide.ws.bean.EntidadPideBean;
import pe.gob.pj.pide.ws.bean.PersonaBean;
import pe.gob.pj.pide.ws.bean.PersonaReniecBean;

@Service("busquedasService")
public class BusquedasServiceImpl implements BusquedasService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(BusquedasServiceImpl.class);

	@Autowired
	private BusquedasDao dao;

	@Autowired
	private ReniecWsService servReniec;
	
	@Autowired
	private SunatWsService servSunat;

	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = {
			Exception.class, SQLException.class })
	public PaginationDTO buscarAsignaccionAcceso(String cuo, RequestBusquedaPermisosDTO request, Integer page,
			Integer pageSize) throws Exception {
		Map<String, Object> filtros = new HashMap<>();
		filtros.put("idEntidad", request.getIdEntidad());
		filtros.put("idOperacion", request.getIdOperacion());
		filtros.put("estado", request.getEstado());
		filtros.put("fechaDesde", request.getFechaDesde());
		filtros.put("fechaHasta", UtilsSCPide.sumarRestarDias(request.getFechaHasta(), 1));
		if(page!=null && pageSize!=null) {
			page = page >= 0 ? page : 0;
			pageSize = pageSize > 0 ? pageSize : ConstantesSCPide.DEFAULT_PAGINATION_PAGE_SIZE;
			return dao.buscarAsignacionAcceso(cuo, filtros, page, pageSize);
		}else {
			return dao.buscarAsignacionAcceso(cuo, filtros);
		}
	}

	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = {
			Exception.class, SQLException.class })
	public PaginationDTO buscarSolicitudCuota(String cuo, RequestBusquedaSolicitudDTO request, Integer page,
			Integer pageSize) throws Exception {
		Map<String, Object> filtros = new HashMap<>();
		filtros.put("idSolicitud", request.getIdSolicitud());
		filtros.put("idEntidad", request.getIdEntidad());
		filtros.put("idOperacion", request.getIdOperacion());
		filtros.put("idEstadoSolicitud", request.getIdEstadoSolicitud());
		filtros.put("numeroRuc", request.getNumeroRuc());
		filtros.put("fechaDesde", request.getFechaDesde());
		filtros.put("fechaHasta", UtilsSCPide.sumarRestarDias(request.getFechaHasta(), 1));
		if(page!=null && pageSize!=null) {
			page = page >= 0 ? page : 0;
			pageSize = pageSize > 0 ? pageSize : ConstantesSCPide.DEFAULT_PAGINATION_PAGE_SIZE;
			return dao.buscarSolicitudCuota(cuo, filtros, page, pageSize);
		}else {
			return dao.buscarSolicitudCuota(cuo, filtros);
		}
		
	}

	@Override
	public ResponseValidarDocumetoIdentidadDTO validarDniReniec(String cuo, String nroDocumento) throws Exception {
		logger.info("{}Inicio de método: {}", cuo, "validarDniReniec");
		logger.info("{}nroDocumento: {}", cuo, nroDocumento);
		ResponseValidarDocumetoIdentidadDTO per = new ResponseValidarDocumetoIdentidadDTO();
		PersonaBean persona = new PersonaBean();
		persona.setcNumeIdentidad(nroDocumento);

		PersonaReniecBean personaReniec = servReniec.consultaReniecxDni(persona);
		if(personaReniec==null) {
			per.setRespuestaReniec("El servicio de RENIEC no responde, intentelo en otro momento.");
		}else if (personaReniec.getApePat() == null && personaReniec.getApeMat() == null && personaReniec.getNombres() == null) {
			per.setRespuestaReniec("El número de documento de identidad no existe en RENIEC.");
		} else if(!UtilsSCPide.isNullOrEmpty(personaReniec.getFecFall())){
			per.setRespuestaReniec("El número de documento de identidad pertenece a una persona fallecida.");
		}else{
			per.setRespuestaReniec("Datos encontrados.");
			per.setNroDocumento(personaReniec.getNroDNI());
			per.setApellidoPaterno(personaReniec.getApePat());
			per.setApellidoMaterno(personaReniec.getApeMat());
			per.setNombres(personaReniec.getNombres());
		}
		return per;
	}

	@Override
	public ResponseValidarRucDTO validarRucSunat(String cuo, String ruc) throws Exception {
		logger.info("{}Inicio de método: {}", cuo, "validarRucSunat");
		logger.info("{}ruc: {}", cuo, ruc);
		ResponseValidarRucDTO enti = new ResponseValidarRucDTO();
		
		EntidadPideBean entidadSunat = servSunat.consultarSunatxRuc(ruc);
		if(entidadSunat==null) {
			enti.setRespuestaSunat("El servicio Pide SUNAT no responde, intentelo en otro momento.");
		}else if(UtilsSCPide.isNullOrEmpty(entidadSunat.getRazonSocial())) {
			enti.setRespuestaSunat("El número de ruc no existe en SUNAT.");
		}else {
			enti.setRespuestaSunat("Datos encontrados.");
			enti.setNroRuc(entidadSunat.getNroRuc().trim());
			enti.setRazonSocial(entidadSunat.getRazonSocial().trim());
		}
		return enti;
	}

	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = {
			Exception.class, SQLException.class })
	public DetalleSolicitudDTO buscarDetalleSolicitud(String cuo, Integer idSolicitud) throws Exception {
		return dao.buscarDetalleSolicitud(cuo, idSolicitud);
	}

	@Override
	@Transactional(transactionManager = "txManagerSeguridadPide", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = {
			Exception.class, SQLException.class })
	public ResponseHistorialCuotaDTO historialCuota(String cuo, Integer idEntidad, Integer idOperacion, Integer anio, Integer mes)
			throws Exception {
		return dao.historialCuota(cuo, idEntidad, idOperacion, anio, mes);
	}

}
