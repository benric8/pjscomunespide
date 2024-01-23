package pe.gob.pj.pide.api.app.apis;

import java.io.Serializable;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.pj.pide.dao.dto.GlobalResponseDTO;
import pe.gob.pj.pide.dao.dto.pide.EntidadDTO;
import pe.gob.pj.pide.dao.dto.pide.EstadoSolicitudDTO;
import pe.gob.pj.pide.dao.dto.pide.IpEntidadDTO;
import pe.gob.pj.pide.dao.dto.pide.OperacionDTO;
import pe.gob.pj.pide.dao.dto.pide.PerfilDTO;
import pe.gob.pj.pide.dao.dto.pide.TipoSolicitudDTO;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;
import pe.gob.pj.pide.dao.utils.UtilsSCPide;
import pe.gob.pj.pide.service.MaestrosService;

@RestController
public class MaestrosApi implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(MaestrosApi.class);
	
	@Autowired
	private MaestrosService maestrosService;
	
	@RequestMapping(value = "/listarComboPerfil", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> listarComboPerfil(@RequestAttribute String cuo) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			logger.info("{}Inicio de endpoint:{}",cuo,"listarComboEntidad");
			List<PerfilDTO> lista = maestrosService.listarPerfilActivo(cuo);
			res.setCodigo(ConstantesSCPide.C_200);
			res.setDescripcion("Se realizo búsqueda de entidades para combo.");
			res.setData(lista);
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error en listarComboPerfil: {}", cuo , res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listarComboEntidad", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> listarComboEntidad(@RequestAttribute String cuo) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			logger.info("{}Inicio de endpoint:{}",cuo,"listarComboEntidad");
			List<EntidadDTO> lista = maestrosService.listarEntidadActiva(cuo);
			res.setCodigo(ConstantesSCPide.C_200);
			res.setDescripcion("Se realizo búsqueda de entidades para combo.");
			res.setData(lista);
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error en listarComboEntidad: {}", cuo , res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/completarComboEntidad", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> completarComboEntidad(@RequestAttribute String cuo, @RequestParam(name = "razonSocial") String razonSocial) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			logger.info("{}Inicio de endpoint:{}",cuo,"completarComboEntidad");
			List<EntidadDTO> lista = maestrosService.completarEntidadActiva(cuo, razonSocial);
			res.setCodigo(ConstantesSCPide.C_200);
			res.setDescripcion("Se realizo autocompletar de entidades para combo.");
			res.setData(lista);
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error en completarComboEntidad: {}", cuo , res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}	
	
	@RequestMapping(value = "/completarComboOperacion", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> completarComboOperacion(@RequestAttribute String cuo, @RequestParam(name = "operacion") String operacion) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			logger.info("{}Inicio de endpoint:{}",cuo,"completarComboOperacion");
			List<OperacionDTO> lista = maestrosService.completarOperacionActiva(cuo, operacion);
			res.setCodigo(ConstantesSCPide.C_200);
			res.setDescripcion("Se realizo autocompletar de operaciones para combo.");
			res.setData(lista);
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error en completarComboOperacion: {}", cuo , res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/listarComboOperacion", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> listarComboOperacion(@RequestAttribute String cuo) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			logger.info("{}Inicio de endpoint:{}",cuo,"listarComboOperacion");
			List<OperacionDTO> listaOperacion = maestrosService.listarOperacionActiva(cuo);
			res.setCodigo(ConstantesSCPide.C_200);
			res.setDescripcion("Se realizo búsqueda de operaciones para combo.");
			res.setData(listaOperacion);
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error en listarComboOperacion: {}", cuo , res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listarIpsEntidad", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> listarIpsEntidad(@RequestAttribute String cuo, @RequestParam(name = "idEntidad") Integer id) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			logger.info("{}Inicio de endpoint:{}",cuo,"listarIpsEntidad");
			List<IpEntidadDTO> lista = maestrosService.listarIpsEntidad(cuo, id);
			res.setCodigo(ConstantesSCPide.C_200);
			res.setDescripcion("Se realizo listado de ips de entidad.");
			res.setData(lista);
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error en listarIpsEntidad: {}", cuo , res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listarEstadosSolicitud", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> listarEstadosSolicitud(@RequestAttribute String cuo, @RequestParam(name = "paraEvaluacion", required = false) String paraEvaluacion) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			logger.info("{}Inicio de endpoint:{}",cuo,"listarEstadosSolicitud");
			List<EstadoSolicitudDTO> lista = maestrosService.listarEstadosSolicitud(cuo, paraEvaluacion);
			res.setCodigo(ConstantesSCPide.C_200);
			res.setDescripcion("Se realizo listado estados solicitud.");
			res.setData(lista);
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error al listar estados solicitud: {}", cuo , res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listarTiposSolicitud", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> listarTiposSolicitud(@RequestAttribute String cuo) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			logger.info("{}Inicio de endpoint:{}",cuo,"listarTiposSolicitud");
			List<TipoSolicitudDTO> lista = maestrosService.listarTiposSolicitud(cuo);
			res.setCodigo(ConstantesSCPide.C_200);
			res.setDescripcion("Se realizo listado tipos solicitud.");
			res.setData(lista);
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error al listar tipos solicitud: {}", cuo , res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
}
