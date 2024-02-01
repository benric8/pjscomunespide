package pe.gob.pj.pide.api.app.apis;

import java.io.Serializable;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.pj.pide.dao.dto.GlobalResponseDTO;
import pe.gob.pj.pide.dao.dto.PaginationDTO;
import pe.gob.pj.pide.dao.dto.RequestBusquedaPermisosDTO;
import pe.gob.pj.pide.dao.dto.RequestBusquedaSolicitudDTO;
import pe.gob.pj.pide.dao.dto.RequestValidarDocumetoIdentidadDTO;
import pe.gob.pj.pide.dao.dto.RequestValidarRucDTO;
import pe.gob.pj.pide.dao.dto.ResponseHistorialCuotaDTO;
import pe.gob.pj.pide.dao.dto.ResponseValidarDocumetoIdentidadDTO;
import pe.gob.pj.pide.dao.dto.ResponseValidarRucDTO;
import pe.gob.pj.pide.dao.dto.pide.DetalleSolicitudDTO;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;
import pe.gob.pj.pide.dao.utils.UtilsSCPide;
import pe.gob.pj.pide.service.BusquedasService;

@RestController
public class BusquedasApi implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(BusquedasApi.class);
	
	@Autowired
	private BusquedasService serv;
	

	@RequestMapping(value = "/buscarPermisos", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> buscarPermisos(@RequestAttribute String cuo, @Valid @RequestBody RequestBusquedaPermisosDTO request,
			@RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "rows", required = false) Integer rows) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			logger.info("{}Inicio de método:{}",cuo,"buscarPermiso");			
			PaginationDTO pagination = serv.buscarAsignaccionAcceso(cuo, request, page, rows);
			res.setCodigo(ConstantesSCPide.C_EXITO);
			res.setDescripcion(ConstantesSCPide.X_EXITO);
			res.setData(pagination);
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error en buscarPermisos: {}", cuo , res.getDescripcion());
		}	
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/buscarSolicitudes", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> buscarSolicitudes(@RequestAttribute String cuo, @Valid @RequestBody RequestBusquedaSolicitudDTO request,
			@RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "rows", required = false) Integer rows) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			logger.info("{}Inicio de método:{}",cuo,"buscarSolicitudes");			
			PaginationDTO pagination = serv.buscarSolicitudCuota(cuo, request, page, rows);
			res.setCodigo(ConstantesSCPide.C_EXITO);
			res.setDescripcion(ConstantesSCPide.X_EXITO);
			res.setData(pagination);
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error en buscarSolicitudes: {}", cuo , res.getDescripcion());
		}	
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/buscarDetalleSolicitud", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> buscarDetalleSolicitud(@RequestAttribute String cuo, @RequestParam(name="idSolicitud") Integer idSolicitud) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			logger.info("{}Inicio de método:{}",cuo,"buscarSolicitudes");			
			DetalleSolicitudDTO data = serv.buscarDetalleSolicitud(cuo, idSolicitud);
			res.setCodigo(ConstantesSCPide.C_EXITO);
			res.setDescripcion(ConstantesSCPide.X_EXITO);
			res.setData(data);
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error en buscarDetalleSolicitud: {}", cuo , res.getDescripcion());
		}	
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/historialCuota", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> historialCuota(@RequestAttribute String cuo, @RequestParam(name="idEntidad") Integer idEntidad,
			@RequestParam(name="idOperacion") Integer idOperacion, @RequestParam(name="anio") Integer anio, @RequestParam(name="mes") Integer mes) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			logger.info("{}Inicio de método:{}",cuo,"buscarSolicitudes");			
			ResponseHistorialCuotaDTO data = serv.historialCuota(cuo, idEntidad, idOperacion, anio, mes);
			res.setCodigo(ConstantesSCPide.C_EXITO);
			res.setDescripcion(ConstantesSCPide.X_EXITO);
			res.setData(data);
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error en historialCuota: {}", cuo , res.getDescripcion());
		}	
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/validarDocumentoIdentidad", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> validarDocumentoIdentidad(@RequestAttribute String cuo, @Valid @RequestBody RequestValidarDocumetoIdentidadDTO request) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			logger.info("{}Inicio de método:{}",cuo,"validarDocumentoIdentidad");			
			ResponseValidarDocumetoIdentidadDTO personaReniec = serv.validarDniReniec(cuo, request.getNroDocumento());
			res.setCodigo(ConstantesSCPide.C_EXITO);
			res.setDescripcion(ConstantesSCPide.X_EXITO);
			res.setData(personaReniec);
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error al validar dni: {}", cuo , res.getDescripcion());
		}	
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/validarRuc", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> validarRuc(@RequestAttribute String cuo, @Valid @RequestBody RequestValidarRucDTO request) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			logger.info("{}Inicio de método:{}",cuo,"validarRuc");			
			ResponseValidarRucDTO entidadSunat = serv.validarRucSunat(cuo, request.getRuc());
			res.setCodigo(ConstantesSCPide.C_EXITO);
			res.setDescripcion(ConstantesSCPide.X_EXITO);
			res.setData(entidadSunat);
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error al validar ruc: {}", cuo , res.getDescripcion());
		}	
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}

}
