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

import pe.gob.pj.pide.dao.dto.seguridad.RolDTO;
import pe.gob.pj.pide.dao.dto.seguridad.RoleSecurity;
import pe.gob.pj.pide.dao.dto.seguridad.UserSecurity;
import pe.gob.pj.pide.dao.dto.seguridad.UsuarioDTO;

import org.springframework.security.core.userdetails.User;


import pe.gob.pj.pide.dao.utils.UtilsSCPide;
import pe.gob.pj.pide.service.SeguridadService;

@Service
public class CustomUserDetailsService implements UserDetailsService, Serializable {

	
	
	private static final Logger logger = LogManager.getLogger(CustomUserDetailsService.class);

	private static final long serialVersionUID = 1L;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SeguridadService service;

	public CustomUserDetailsService(SeguridadService service, PasswordEncoder passwordEncoder) {
		super();
		this.service = service;
		this.passwordEncoder = passwordEncoder;
		// TODO Auto-generated constructor stub
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserSecurity user = new UserSecurity();
		try {
			UsuarioDTO u = service.recuperaInfoUsuario("", username);
			if(u != null && u.getId() > 0) {
				user.setId(u.getId());
				user.setName(u.getCUsuario());
				user.setPassword(passwordEncoder.encode(u.getCClave()));
				List<RoleSecurity> roles = new ArrayList<RoleSecurity>();
				List<RolDTO> rolesB = service.recuperarRoles("", username);
				rolesB.forEach(rol ->{
					roles.add(new RoleSecurity(rol.id, rol.getCRol()));
				});
				user.setRoles(roles);
			} else {
				throw new Exception("Usuario con ID:  " + username + " not found");
			}
		} catch (Exception e) {
			logger.debug("ERROR AL RECUPERAR USUARIO Y ROLES PARA SPRING SECURITY: " + UtilsSCPide.convertExceptionToString(e));			
			e.printStackTrace();
			new UsernameNotFoundException("Usuario con ID:  " + username + " not found");
		}
		
		return new User(user.getName(), user.getPassword(), getAuthorities(user));
	}
	
	private static Collection<? extends GrantedAuthority> getAuthorities(UserSecurity user) {
		String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
		return authorities;
	}
}