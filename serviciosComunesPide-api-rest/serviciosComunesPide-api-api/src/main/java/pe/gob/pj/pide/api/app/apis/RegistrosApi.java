package pe.gob.pj.pide.api.app.apis;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.pj.pide.dao.dto.GlobalResponseDTO;
import pe.gob.pj.pide.dao.dto.RequestEvaluarSolicitudCuotaDTO;
import pe.gob.pj.pide.dao.dto.RequestModificarPermisoDTO;
import pe.gob.pj.pide.dao.dto.RequestRegistrarEntidadDTO;
import pe.gob.pj.pide.dao.dto.RequestRegistrarIpEntidadDTO;
import pe.gob.pj.pide.dao.dto.RequestOperacionDTO;
import pe.gob.pj.pide.dao.dto.RequestRegistrarSolicitudCuotaDTO;
import pe.gob.pj.pide.dao.dto.pide.SolicitudDTO;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;
import pe.gob.pj.pide.dao.utils.UtilsSCPide;
import pe.gob.pj.pide.service.RegistrosService;

@RestController
public class RegistrosApi implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(RegistrosApi.class);
	
	@Autowired
	private RegistrosService serv;
	
	@RequestMapping(value = "/registrarEntidad", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> registrarEntidad(@RequestAttribute String cuo, @Validated @RequestBody RequestRegistrarEntidadDTO request) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			res.setCodigo(ConstantesSCPide.C_200);
			res.setDescripcion("Realizando registro de entidad.");
			boolean rpta = serv.registrarEntidad(cuo, request);
			if(rpta) {
				res.setDescripcion("Registro de entidad, exitoso.");
			}else {
				res.setDescripcion("Registro de entidad, fallido.");
			}
			res.setData(rpta ? "1" : "0");
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error al registrar entidad: {}", cuo , res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/registrarIpEntidad", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> registrarIpEntidad(@RequestAttribute String cuo, @Validated @RequestBody RequestRegistrarIpEntidadDTO request) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			res.setCodigo(ConstantesSCPide.C_200);
			res.setDescripcion("Realizando Registro/Edición de ip entidad.");
			boolean rpta = serv.registrarIpEntidad(cuo, request);
			if(rpta) {
				res.setDescripcion("Registro/Edición de ip entidad, exitoso.");
			}else {
				res.setDescripcion("Registro/Edición de ip entidad, fallido.");
			}
			res.setData(rpta ? "1" : "0");
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error al registrar/editar ip de entidad: {}", cuo , res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/registrarSolicitudCuota", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> registrarSolicitudCuota(@RequestAttribute String cuo, @Validated @RequestBody RequestRegistrarSolicitudCuotaDTO request) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			res.setCodigo(ConstantesSCPide.C_200);
			res.setDescripcion("Realizando registro de solicitud de cuota.");
			SolicitudDTO rpta = serv.registrarSolicitudCuota(cuo, request);
			res.setData(rpta);
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error al registrar solicitud cuota: {}", cuo , res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/evaluarSolicitudCuota", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> evaluarSolicitudCuota(@RequestAttribute String cuo, @Validated @RequestBody RequestEvaluarSolicitudCuotaDTO request) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			res.setCodigo(ConstantesSCPide.C_200);
			res.setDescripcion("Realizando evaluación de solicitud de cuota.");
			boolean rpta = serv.evaluarSolicitudCuota(cuo, request);
			if(rpta) {
				res.setDescripcion("Evaluación de solicitud, exitoso.");
			}else {
				res.setDescripcion("Evaluación de solicitud, fallido.");
			}
			res.setData(rpta ? "1" : "0");
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error al evaluar solicitu cuota: {}", cuo , res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/modificarPermiso", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> modificarPermiso(@RequestAttribute String cuo, @Validated @RequestBody RequestModificarPermisoDTO request) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			res.setCodigo(ConstantesSCPide.C_200);
			res.setDescripcion("Realizando modificación de permiso.");
			boolean rpta = serv.modificarPermiso(cuo, request);
			if(rpta) {
				res.setDescripcion("Modificación de permiso, exitoso.");
			}else {
				res.setDescripcion("Modificación de permiso, fallido.");
			}
			res.setData(rpta ? "1" : "0");
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error al evaluar solicitu cuota: {}", cuo , res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	@RequestMapping(value = "/registrarOperacion", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> registrarOperacion(@RequestAttribute String cuo, @Validated @RequestBody RequestOperacionDTO request){
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			logger.info("{}Inicio de endpoint:{}",cuo,"registrarOperacion");
			res.setCodigo(ConstantesSCPide.C_200);
			res.setDescripcion("Realizando registro de operacion.");
			boolean rpta = serv.registrarOperacion(cuo, request);
			if(rpta) {
				res.setDescripcion("Registro de operacion, exitoso.");
			}else {
				res.setDescripcion("Registro de operacion, fallido.");
			}
			res.setData(rpta ? "1" : "0");
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error al registrar operacion: {}", cuo , res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/modificarOperacion", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> modificarUsuario(@RequestAttribute String cuo, @Validated @RequestBody RequestOperacionDTO request, @RequestParam Integer idOperacion) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			logger.info("{}Inicio de endpoint:{}",cuo,"modificarOperacion");
			res.setCodigo(ConstantesSCPide.C_200);
			res.setDescripcion("Realizando modificación de la Operacion.");
			boolean rpta = serv.modificarOperacion(cuo, request,idOperacion);
			if(rpta) {
				res.setDescripcion("Modificación de Operacion, exitoso.");
			}else {
				res.setDescripcion("Modificación de Operacion, fallido.");
			}
			res.setData(rpta ? "1" : "0");
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error en modificarOperacion: {}", cuo , res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
}
