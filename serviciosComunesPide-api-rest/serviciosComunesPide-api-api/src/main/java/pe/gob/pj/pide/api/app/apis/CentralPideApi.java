package pe.gob.pj.pide.api.app.apis;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import pe.gob.pj.pide.dao.dto.GlobalResponseDTO;

import pe.gob.pj.pide.dao.utils.ConstantesSCPide;
import pe.gob.pj.pide.dao.utils.ProjectProperties;
import pe.gob.pj.pide.dao.utils.SecurityConstants;
import pe.gob.pj.pide.dao.utils.UtilsSCPide;
import pe.gob.pj.pide.service.SeguridadService;

@RestController
public class CentralPideApi implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(CentralPideApi.class);
	
	@Autowired
	@Qualifier("seguridadService")
	private SeguridadService seguridad ;

	/**
	 * Método que sirve para verificar versión actual del aplicativo
	 * 
	 * @param cuo Código único de log
	 * @return Datos del aplicativo
	 */
	@RequestMapping(value = "/healthcheck", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> verificarConexiones() {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			res.setCodigo(ConstantesSCPide.C_EXITO);
			res.setDescripcion(ConstantesSCPide.X_EXITO);
			Map<String, String> healthcheck = new HashMap<String, String>();
			healthcheck.put("Aplicativo", "Servicios Comunes Pide API-REST");
			healthcheck.put("Estado", "Disponible");
			healthcheck.put("Versión", ConstantesSCPide.VERSION);
			res.setData(healthcheck);
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));
			logger.error("Error al consultar versión de aplicativo serviciosComunesPide-api: {}", res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}


	/**
	 * MÉTODO QUE GENERA NUEVO TOKEN A PARTIR DE TOKEN ANTERIOR
	 * 
	 * @param token           es token antentior
	 * @param ipRemota        es la ip desde donde lo solicita
	 * @param tokenAdmin      es el token de la seccion administrador
	 * @param validTokenAdmin indicador si necesitamos validar token del admin
	 * @param cuo             código único de log
	 * @return un nuevo token
	 */
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/seguridad/refresh", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> refreshToken(@RequestParam String token, @RequestAttribute String cuo, @RequestAttribute String ipRemota, @RequestAttribute Date limit) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {			
			logger.info("Iniciamos el refresh del Token ip: {} limite: {}",ipRemota,limit);
			byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
			res.setCodigo(ConstantesSCPide.C_500);
			
			try {
				Jws<Claims> parsedToken = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token.replace("Bearer ", ""));
				String accesoBase =  (String) parsedToken.getBody().get(ConstantesSCPide.CLAIM_ACCESO);
				List<String> roles = (List<String>) parsedToken.getBody().get(ConstantesSCPide.CLAIM_ROL);
				String ipRemotaToken = parsedToken.getBody().get(ConstantesSCPide.CLAIM_IP).toString();
				//Date limiteRefreshClaim = new Date(Long.parseLong(parsedToken.getBody().get(ConstantesSCPide.CLAIM_LIMIT).toString()));
				int total = (int) parsedToken.getBody().get(ConstantesSCPide.CLAIM_NUMERO);
				String subject = parsedToken.getBody().getSubject();
				int tiempoSegundosExpira = ProjectProperties.getInstance().getSeguridadTiempoExpiraSegundos();
				int tiempoSegundosRefresh = ProjectProperties.getInstance().getSeguridadTiempoRefreshSegundos();
				
				Date ahora = new Date();
				Date limiteExpira = parsedToken.getBody().getExpiration();
				Date limiteRefresh = UtilsSCPide.sumarRestarSegundos(limiteExpira, tiempoSegundosRefresh);
				
				
				if (ipRemota.equals(ipRemotaToken)) {
					
						if(!ahora.after(limiteRefresh)) {
							String tokenResult = Jwts.builder()
									.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
									.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
									.setIssuer(SecurityConstants.TOKEN_ISSUER)
									.setAudience(SecurityConstants.TOKEN_AUDIENCE)
									.setSubject(subject).setExpiration(UtilsSCPide.sumarRestarSegundos(ahora, tiempoSegundosExpira))
									.claim(ConstantesSCPide.CLAIM_ROL, roles)
									.claim(ConstantesSCPide.CLAIM_IP, ipRemota)
									.claim(ConstantesSCPide.CLAIM_ACCESO, accesoBase)
									.claim(ConstantesSCPide.CLAIM_LIMIT, UtilsSCPide.sumarRestarSegundos(ahora, tiempoSegundosExpira + tiempoSegundosRefresh))
									.claim(ConstantesSCPide.CLAIM_NUMERO, total + 1)
									.compact();
							res.setCodigo(ConstantesSCPide.C_EXITO);
							res.setDescripcion(ConstantesSCPide.X_EXITO);
							res.setData(tokenResult);
							return new ResponseEntity<>(res, HttpStatus.OK);
						}else {
							res.setCodigo(ConstantesSCPide.C_E001);
							res.setDescripcion(ConstantesSCPide.X_E001);
							return new ResponseEntity<>(res, HttpStatus.OK);
						}
									
				} else {
					res.setCodigo(ConstantesSCPide.C_401);
					return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
				}
			} catch (ExpiredJwtException e) {
				String accesoBase =  (String) e.getClaims().get(ConstantesSCPide.CLAIM_ACCESO);
				List<String> roles = (List<String>) e.getClaims().get(ConstantesSCPide.CLAIM_ROL);
				String ipRemotaToken = e.getClaims().get(ConstantesSCPide.CLAIM_IP).toString();
				int total = (int) e.getClaims().get(ConstantesSCPide.CLAIM_NUMERO);
				String subject = e.getClaims().getSubject();
				//Date limiteRefreshClaim = new Date(Long.parseLong(e.getClaims().get(ConstantesSCPide.CLAIM_LIMIT).toString()));
				int tiempoSegundosExpira = ProjectProperties.getInstance().getSeguridadTiempoExpiraSegundos();
				int tiempoSegundosRefresh = ProjectProperties.getInstance().getSeguridadTiempoRefreshSegundos();

				Date ahora = new Date();
				Date limiteExpira = e.getClaims().getExpiration();
				Date limiteRefresh = UtilsSCPide.sumarRestarSegundos(limiteExpira, tiempoSegundosRefresh);
				
				
				if (ipRemota.equals(ipRemotaToken)) {
					
						if(!ahora.after(limiteRefresh)) {
							String tokenResult = Jwts.builder()
									.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
									.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
									.setIssuer(SecurityConstants.TOKEN_ISSUER)
									.setAudience(SecurityConstants.TOKEN_AUDIENCE)
									.setSubject(subject).setExpiration(UtilsSCPide.sumarRestarSegundos(ahora, tiempoSegundosExpira))
									.claim(ConstantesSCPide.CLAIM_ROL, roles)
									.claim(ConstantesSCPide.CLAIM_IP, ipRemota)
									.claim(ConstantesSCPide.CLAIM_ACCESO, accesoBase)
									.claim(ConstantesSCPide.CLAIM_LIMIT, UtilsSCPide.sumarRestarSegundos(ahora,  tiempoSegundosExpira + tiempoSegundosRefresh))
									.claim(ConstantesSCPide.CLAIM_NUMERO, total + 1)
									.compact();
							res.setCodigo(ConstantesSCPide.C_EXITO);
							res.setDescripcion(ConstantesSCPide.X_EXITO);
							res.setData(tokenResult);
							return new ResponseEntity<>(res, HttpStatus.OK);
						}else {
							res.setCodigo(ConstantesSCPide.C_E001);
							res.setDescripcion(ConstantesSCPide.X_E001);
							return new ResponseEntity<>(res, HttpStatus.OK);
						}
					
				} else {
					res.setCodigo(ConstantesSCPide.C_401);
					logger.warn(
							"{} No se ha encontrado coincidencias válidas del token anterior o se ha excedido el tiempo limite para refrescar token.",
							cuo);
					return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
				}
			}
		} catch (Exception e) {
			logger.error("{} error al intentar generar nuevo Token: {}", cuo, UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));
		}
		return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
	}

}
