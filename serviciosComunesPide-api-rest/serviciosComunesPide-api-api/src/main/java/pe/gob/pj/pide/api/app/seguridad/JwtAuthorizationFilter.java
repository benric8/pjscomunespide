package pe.gob.pj.pide.api.app.seguridad;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;
import lombok.Setter;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;
import pe.gob.pj.pide.dao.utils.SecurityConstants;
import pe.gob.pj.pide.dao.utils.UtilsSCPide;
import pe.gob.pj.pide.service.SegUsuarioService;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private static final Logger log = LogManager.getLogger(JwtAuthorizationFilter.class);

	@Getter
	@Setter
	private SegUsuarioService segUsuarioService;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, SegUsuarioService servicio) {
		super(authenticationManager);
		this.setSegUsuarioService(servicio);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		request.setAttribute(ConstantesSCPide.AUD_CUO, UtilsSCPide.obtenerCodigoUnico());
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		if (authentication == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			filterChain.doFilter(request, response);
			return;
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String remoteIp = request.getRemoteAddr();
		if (request.getRemoteAddr() == null) {
			remoteIp = request.getRemoteHost();
		}
		String urlReq = request.getRequestURI();
		String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
		String cuo = request.getAttribute(ConstantesSCPide.AUD_CUO).toString();
		byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
		if (StringUtils.isNotEmpty(token) && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			try {

				Jws<Claims> parsedToken = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token.replace("Bearer ", ""));

				@SuppressWarnings("unchecked")
				List<String> roles = (List<String>) parsedToken.getBody().get("rol");

				String usuario = (String) parsedToken.getBody().get("usuario");

				String username = parsedToken.getBody().getSubject();
				
				Date ahora = new Date();
				Date limiteExpira = parsedToken.getBody().getExpiration();
				Date limiteRefresh = UtilsSCPide.sumarRestarSegundos(limiteExpira, ConstantesSCPide.TOKEN_TIEMPO_PARA_REFRESCAR_SEGUNDOS);

				if (!urlReq.endsWith("refresh")) {
					boolean tieneAcceso = segUsuarioService.validarAccesoMetodo(username, roles.get(0), urlReq, cuo);
					if (!tieneAcceso) {
						log.info("{} El usuario [{}], no tiene acceso al método [{}] ", cuo, username, urlReq);
						return null;
					}
				}

				List<SimpleGrantedAuthority> authorities = ((List<?>) parsedToken.getBody().get("rol")).stream()
						.map(authority -> new SimpleGrantedAuthority((String) authority)).collect(Collectors.toList());

				String ipRemotaDeToken = parsedToken.getBody().get("remoteIp").toString();
				if (!remoteIp.equals(ipRemotaDeToken) || ahora.after(limiteRefresh)) {
					return null;
				}

				if (StringUtils.isNotEmpty(username)) {
					request.setAttribute(ConstantesSCPide.AUD_CUO, cuo);
					if (urlReq.endsWith("refresh") || urlReq.endsWith("login")) {
						request.setAttribute(ConstantesSCPide.REMOTE_IP, remoteIp);
					} else if (urlReq.endsWith("buscarSolicitudes")) {
						request.setAttribute("usuario", usuario);
					}
					return new UsernamePasswordAuthenticationToken(username, null, authorities);
				}
			} catch (ExpiredJwtException exception) {
				String ipRemotaToken = exception.getClaims().get("remoteIp").toString();
				String subject = exception.getClaims().getSubject();
				
//				Date ahora = new Date();
//				Date limiteExpira = exception.getClaims().getExpiration();
//				Date limiteRefresh = UtilsSCPide.sumarRestarSegundos(limiteExpira, ConstantesSCPide.TOKEN_TIEMPO_PARA_REFRESCAR_SEGUNDOS);
//				boolean expiroRefresh = ahora.after(limiteRefresh);
					
				if (remoteIp.equals(ipRemotaToken) && urlReq.endsWith("refresh")) {
					List<SimpleGrantedAuthority> authorities = ((List<?>) exception.getClaims().get("rol")).stream()
							.map(authority -> new SimpleGrantedAuthority((String) authority))
							.collect(Collectors.toList());
					request.setAttribute(ConstantesSCPide.AUD_CUO, cuo);
					request.setAttribute(ConstantesSCPide.REMOTE_IP, remoteIp);
					return new UsernamePasswordAuthenticationToken(subject, null, authorities);
				}
				log.warn(cuo + "Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
			} catch (UnsupportedJwtException exception) {
				log.warn(cuo + "Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
			} catch (MalformedJwtException exception) {
				log.warn(cuo + "Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
			} catch (SignatureException exception) {
				log.warn(cuo + "Request to parse JWT with invalid signature : {} failed : {}", token,
						exception.getMessage());
			} catch (IllegalArgumentException exception) {
				log.warn(cuo + "Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
			} catch (Exception e) {
				log.error(cuo + "No se optubo owner y código de BASE DE DATOS: "
						+ UtilsSCPide.convertExceptionToString(e));
				e.printStackTrace();
			}
		}
		log.error(cuo + "Hubo un problema con el token : " + token);
		return null;
	}

}