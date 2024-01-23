package pe.gob.pj.pide.api.app.apis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

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

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import pe.gob.pj.pide.dao.dto.GlobalResponseDTO;
import pe.gob.pj.pide.dao.dto.PaginationDTO;
import pe.gob.pj.pide.dao.dto.RequestBusquedaUsuarioDTO;
import pe.gob.pj.pide.dao.dto.RequestCambiarPasswordDTO;
import pe.gob.pj.pide.dao.dto.RequestLoginDTO;
import pe.gob.pj.pide.dao.dto.pide.UsuarioDTO;
import pe.gob.pj.pide.dao.utils.CaptchaUtils;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;
import pe.gob.pj.pide.dao.utils.SecurityConstants;
import pe.gob.pj.pide.dao.utils.UtilsSCPide;
import pe.gob.pj.pide.service.LoginService;

@RestController
public class LoginApi implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(LoginApi.class);
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> ingresoSistema(@RequestAttribute String cuo, @RequestAttribute String ipRemota, @Validated @RequestBody RequestLoginDTO login) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			
			if(!UtilsSCPide.isNull(login.getAplicaCaptcha()).equals(ConstantesSCPide.STRING_S) || CaptchaUtils.validCaptcha(UtilsSCPide.isNull(login.getTokenCaptcha()), ipRemota, cuo)) {	
				
				res.setCodigo(ConstantesSCPide.C_200);
				res.setDescripcion("Realizando login de acceso de usuario");
				UsuarioDTO usuario = loginService.login(cuo, login.getUsuario(), login.getContrasenia());
				if(usuario != null) {
					String usuarioCompleto = login.getUsuario()+"-"+usuario.getUsuario();
					String token  = getNewToken(login.getToken(), usuarioCompleto, "any", ipRemota, cuo);
					if(UtilsSCPide.isNull(token).length() > 0) {
						usuario.setToken(token);
					} else {
						res.setCodigo(ConstantesSCPide.C_401);
						res.setDescripcion("Error no se pudo generar nuevo token");
					}
				} else {
					res.setCodigo(ConstantesSCPide.C_404);
					res.setDescripcion("Error no se recupero información de usuario");
				}
				res.setData(usuario);
				
			}else {
				res.setCodigo(ConstantesSCPide.C_404);
				res.setDescripcion("No se ha podido validar código captcha, por favor volver a intentarlo.");			
				logger.error("{} Error al autenticar usuario: {}", cuo , "No se ha podido validar código captcha, por favor volver a intentarlo.");			
			}
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error al autenticar usuario: {}", cuo , res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	public String getNewToken(String token, String usuario, String rol, String ipRemota,  String cuo){
		String newToken = "";
		try {
			byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();		
			try {				
				Jws<Claims> parsedToken = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token.replace("Bearer ", ""));
//				List<String> roles = new ArrayList<String>();
//				roles.add(rol);
				@SuppressWarnings("unchecked")
				List<String> roles = (List<String>) parsedToken.getBody().get("rol");
				String ipRemotaToken = parsedToken.getBody().get("remoteIp").toString();
				int total = (int) parsedToken.getBody().get("numero");
				String subject = parsedToken.getBody().getSubject();
				
				Integer tiempoToken = ConstantesSCPide.TOKEN_TIEMPO_PARA_EXPIRAR_SEGUNDOS * 1000 ;
				if(ipRemota.equals(ipRemotaToken)) {
					newToken = Jwts.builder()
							.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
							.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
							.setIssuer(SecurityConstants.TOKEN_ISSUER)
							.setAudience(SecurityConstants.TOKEN_AUDIENCE)
							.setSubject(subject)
							.setExpiration(new Date(System.currentTimeMillis() + tiempoToken))
							.claim("rol", roles)
							.claim("usuario", usuario)
							.claim("remoteIp", ipRemota)
							.claim("numero", total + 1)
							.compact();
				} 
			} catch (ExpiredJwtException e) {
				List<String> roles = new ArrayList<String>();
				roles.add(rol);
				String ipRemotaToken = e.getClaims().get("remoteIp").toString();
				int total = (int) e.getClaims().get("numero");
				String subject = e.getClaims().getSubject();				
				Integer tiempoToken = 900000;
				if(ipRemota.equals(ipRemotaToken)) {
					newToken = Jwts.builder()
							.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
							.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
							.setIssuer(SecurityConstants.TOKEN_ISSUER)
							.setAudience(SecurityConstants.TOKEN_AUDIENCE)
							.setSubject(subject)
							.setExpiration(new Date(System.currentTimeMillis() + tiempoToken))
							.claim("rol", roles)
							.claim("usuario", usuario)
							.claim("remoteIp", ipRemota)
							.claim("numero", total + 1)
							.compact();
				} 
			}			
		} catch (Exception e) {
			logger.error("{} error al intentar generar nuevo Token: {}", cuo , UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));
		}
		return newToken;
	}
	
	@RequestMapping(value = "/cambiarPassword", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> cambiarPassword(@RequestAttribute String cuo, @Validated @RequestBody RequestCambiarPasswordDTO request) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			if(!request.getClaveActual().equalsIgnoreCase(request.getClaveNueva())) {
				res.setCodigo(ConstantesSCPide.C_200);
				boolean rpta = loginService.cambiarClave(cuo, request.getUsuario(), request.getClaveActual(), request.getClaveNueva());
				if(rpta) {
					res.setDescripcion("Cambio de contraseña, exitoso.");
				}else {
					res.setDescripcion("Cambio de contraseña, fallido.");
				}
				res.setData(rpta ? "1" : "0");
			}else {
				res.setCodigo(ConstantesSCPide.C_400);
				res.setDescripcion("La clave actual con la clave nueva no pueden ser las mismas.");
				res.setData("0");
			}
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error al autenticar usuario: {}", cuo , res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/restablecerPassword", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> restablecerPassword(@RequestAttribute String cuo, @RequestParam(name = "idUsuario") int idUsuario) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			res.setCodigo(ConstantesSCPide.C_200);
			boolean rpta = loginService.restablecerClave(cuo, idUsuario);
			if(rpta) {
				res.setDescripcion("Se restablecio la contraseña de manera exitoso.");
			}else {
				res.setDescripcion("No se pudo restablecer la contraseña.");
			}
			res.setData(rpta ? "1" : "0");
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error al autenticar usuario: {}", cuo , res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/registrarUsuario", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> registrarUsuario(@RequestAttribute String cuo, @Validated @RequestBody UsuarioDTO request) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			res.setCodigo(ConstantesSCPide.C_200);
			res.setDescripcion("Realizando registro de usuario.");
			boolean rpta = loginService.registrarUsuario(cuo, request);
			if(rpta) {
				res.setDescripcion("Registro de usuario, exitoso.");
			}else {
				res.setDescripcion("Registro de usuario, fallido.");
			}
			res.setData(rpta ? "1" : "0");
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error en registrarUsuario: {}", cuo , res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/modificarUsuario", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> modificarUsuario(@RequestAttribute String cuo, @Validated @RequestBody UsuarioDTO request) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			res.setCodigo(ConstantesSCPide.C_200);
			res.setDescripcion("Realizando modificación de usuario.");
			boolean rpta = loginService.modificarUsuario(cuo, request);
			if(rpta) {
				res.setDescripcion("Modificación de usuario, exitoso.");
			}else {
				res.setDescripcion("Modificación de usuario, fallido.");
			}
			res.setData(rpta ? "1" : "0");
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error en modificarUsuario: {}", cuo , res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/buscarUsuarios", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponseDTO> buscarUsuarios(@RequestAttribute String cuo, @Valid @RequestBody RequestBusquedaUsuarioDTO request,
			@RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "rows", required = false) Integer rows) {
		GlobalResponseDTO res = new GlobalResponseDTO();
		try {
			logger.info("{}Inicio de método:{}",cuo,"buscarPermiso");			
			PaginationDTO pagination = loginService.buscarUsuario(cuo, request, page, rows);
			res.setCodigo(ConstantesSCPide.C_200);
			res.setDescripcion("Busqueda de asignación de accesos exitosa.");
			res.setData(pagination);
		} catch (Exception e) {
			res.setCodigo(ConstantesSCPide.C_500);
			res.setDescripcion(UtilsSCPide.isNull(e.getCause()).concat(e.getMessage()));			
			logger.error("{} Error en buscarPermisos: {}", cuo , res.getDescripcion());
		}	
		return new ResponseEntity<GlobalResponseDTO>(res, HttpStatus.OK);
	}
	
}
