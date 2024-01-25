package pe.gob.pj.pide.api.app.seguridad;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;
import pe.gob.pj.pide.dao.utils.EncryptUtils;
import pe.gob.pj.pide.dao.utils.ProjectProperties;
import pe.gob.pj.pide.dao.utils.SecurityConstants;
import pe.gob.pj.pide.dao.utils.UtilsSCPide;
import pe.gob.pj.pide.service.SeguridadService;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private static final Logger logger = LogManager.getLogger(JwtAuthorizationFilter.class);

	@Getter @Setter
	private SeguridadService seguridadService;

	private final AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, SeguridadService service) {
		this.authenticationManager = authenticationManager;
		this.setSeguridadService(service);
		setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
	}

	/**
	* Descripción : evalua la autenticacion del usuario
	* @param HttpServletRequest request - peticion HTTP
	* @param HttpServletResponse response - respuesta HTTP    
	* @return Authentication - respuesta de la evaluacion de usuario
	* @exception Captura excepcion generica
	*/
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {		
		String cuo= UtilsSCPide.obtenerCodigoUnico();
		String username = request.getHeader(SecurityConstants.CRE_USERNAME);
		String password = request.getHeader(SecurityConstants.CRE_PASSWORD);
		String codigoCliente = request.getHeader(SecurityConstants.CRE_COD_CLIENTE);
		String codigoRol= request.getHeader(SecurityConstants.CRE_COD_ROL);
		String idUsuario = null;
		try {
			username= EncryptUtils.decryptPastFrass(username);
			password= EncryptUtils.decryptPastFrass(password);
			idUsuario = seguridadService.autenticarUsuario(cuo, codigoCliente, codigoRol, username, password);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			logger.error("{} ERROR AUTENTIFICANDO USUARIO CON BASE DE DATOS DE SEGURIDAD : {}",cuo,UtilsSCPide.convertExceptionToString(e));
			return null;
		}
		if (idUsuario != null && !idUsuario.isEmpty()) {
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(idUsuario, EncryptUtils.encrypt(username, password)));
		}
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return null;
	}

	/**
	* Descripción : Procesa la evaluacion positiva y genera el token
	* @param HttpServletRequest request - peticion HTTP
	* @param HttpServletResponse response - respuesta HTTP    
	* @param FilterChain filterChain - cadenas filtro
	* @param Authentication authentication - resultado de la evaluacion
	* @return void
	* @exception Captura excepcion generica
	*/
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,FilterChain filterChain, Authentication authentication) throws IOException {
		User user = ((User) authentication.getPrincipal());
		List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
		
		Date ahora = new Date();
		int tiempoSegundosExpira = ProjectProperties.getInstance().getSeguridadTiempoExpiraSegundos();
		int tiempoSegundosRefresh = ProjectProperties.getInstance().getSeguridadTiempoRefreshSegundos();
	
		String token = Jwts.builder()
				.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
				.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
				.setIssuer(SecurityConstants.TOKEN_ISSUER)
				.setAudience(SecurityConstants.TOKEN_AUDIENCE)
				.setSubject(user.getUsername())
				.setExpiration(UtilsSCPide.sumarRestarSegundos(ahora, tiempoSegundosExpira))
				.claim(ConstantesSCPide.CLAIM_ROL, roles)
				.claim(ConstantesSCPide.CLAIM_USUARIO, user.getUsername())
				.claim(ConstantesSCPide.CLAIM_IP, request.getRemoteAddr())
				.claim(ConstantesSCPide.CLAIM_ACCESO, ConstantesSCPide.TOKEN_ACCESO_NEUTRO)
				.claim(ConstantesSCPide.CLAIM_LIMIT, UtilsSCPide.sumarRestarSegundos(ahora, tiempoSegundosExpira + tiempoSegundosRefresh))
				.claim(ConstantesSCPide.CLAIM_NUMERO, 1)
				.compact();
		response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);
		response.setContentType("application/json");
		response.getWriter().write("{\"token\":\""+token+"\",\"exps\":\""+tiempoSegundosExpira+"\",\"refs\":\""+tiempoSegundosRefresh+"\"}");
	}
	
	/**
	* Descripción : Procesa la evaluacion negativa 
	* @param HttpServletRequest request - peticion HTTP
	* @param HttpServletResponse response - respuesta HTTP    
	* @param AuthenticationException failed - excepcion por el fallo
	* @return void
	* @exception Captura excepcion generica
	*/
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
		logger.error("ERROR CON LA AUTORIZACION DE SPRING SECURITY: "+failed.getMessage());
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}
}