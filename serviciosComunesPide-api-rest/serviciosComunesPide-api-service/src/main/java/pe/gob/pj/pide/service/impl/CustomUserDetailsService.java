package pe.gob.pj.pide.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pe.gob.pj.pide.dao.entity.Role;
import pe.gob.pj.pide.dao.entity.User;
import pe.gob.pj.pide.dao.repository.SegAudServicioDao;
import pe.gob.pj.pide.dao.utils.ConfiguracionPropiedades;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;
import pe.gob.pj.pide.dao.utils.UtilsSCPide;
import pe.gob.pj.seguridad.client.dto.SeguridadDTO;
import pe.gob.pj.seguridad.client.dto.TokenDTO;
import pe.gob.pj.seguridad.client.dto.Usuario;
import pe.gob.pj.seguridad.client.service.ClientSeguridadService;

@Service
public class CustomUserDetailsService implements UserDetailsService, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(CustomUserDetailsService.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SegAudServicioDao segAudServicioDao;

	@Autowired
	private ClientSeguridadService clientSeguridadService;

	private String usuarioAuthSeguridad = ConfiguracionPropiedades.getInstance()
			.getProperty(ConstantesSCPide.Seguridad.USUARIO_AUTH_SEGURIDAD);
	private String passwordAuthSeguridad = ConfiguracionPropiedades.getInstance()
			.getProperty(ConstantesSCPide.Seguridad.PASSWORD_AUTH_SEGURIDAD);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = new User();
		String cuo = UtilsSCPide.obtenerCodigoUnico();
		try {
			SeguridadDTO seg = new SeguridadDTO();
			seg.setPasswordSpring(passwordAuthSeguridad);
			seg.setUsuarioSpring(usuarioAuthSeguridad);

			TokenDTO token = segAudServicioDao.getToken();
			Usuario u = clientSeguridadService.recuperaUsuarioPorId(cuo, UtilsSCPide.isInt(username), token.getAccess_token());
			if (u != null && u.getId() > 0) {
				user.setId(u.getId().intValue());
				user.setPassword(passwordEncoder.encode(u.getPassword()));
				user.setName(u.getUsername());
				List<Role> roles = new ArrayList<Role>();
				if (u.getRoles() != null) {
					for (pe.gob.pj.seguridad.client.dto.Role r : u.getRoles()) {
						Role rol = new Role();
						rol.setId(r.getId().intValue());
						rol.setName(r.getNombre());
						roles.add(rol);
					}
				}
				user.setRoles(roles);
			} else {
				logger.error("{} Usuario con ID: {} no existe.", cuo, username);
				throw new Exception("Usuario con ID:  " + username + " not found");
			}
		} catch (Exception e) {
			logger.error("{} error al atenticar con spring security: {}", cuo, UtilsSCPide.convertExceptionToString(e));
			logger.fatal(cuo, e);
			new UsernameNotFoundException("Usuario con ID:  " + username + " no existe");
		}
		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
				getAuthorities(user));
	}

	private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
		String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
		return authorities;
	}
}