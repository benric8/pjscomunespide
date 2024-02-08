package pe.gob.pj.pide.dao.repository.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
import pe.gob.pj.pide.dao.dto.seguridad.RolDTO;
import pe.gob.pj.pide.dao.dto.seguridad.UsuarioDTO;
import pe.gob.pj.pide.dao.entity.security.MaeRol;
import pe.gob.pj.pide.dao.entity.security.MaeRolUsuario;
import pe.gob.pj.pide.dao.entity.security.MaeUsuario;
import pe.gob.pj.pide.dao.repository.SeguridadServiceDao;
import pe.gob.pj.pide.dao.utils.EncryptUtils;
import pe.gob.pj.pide.dao.utils.ProjectProperties;
import pe.gob.pj.pide.dao.utils.UtilsSCPide;

@Log4j2
@Component("seguridadServiceDao")
public class SeguridadServiceDaoImpl implements SeguridadServiceDao {
	
	@Autowired
	@Qualifier("sessionSeguridad")
	private SessionFactory sf;

	@Override
	public String autenticarUsuario(String cuo, String codigoCliente, String codigoRol, String usuario, String clave) throws Exception {
		UsuarioDTO user = new UsuarioDTO();
		int nAplicacion = ProjectProperties.getInstance().getSeguridadIdAplicativo();
		Object[] params = { usuario, codigoRol, nAplicacion, codigoCliente };
		log.info("parametros de conexion base de datos r: {} u: {} c: {} cl: {}",codigoRol,usuario,codigoCliente,clave);
		try {			
			TypedQuery<MaeUsuario> query = this.sf.getCurrentSession().createNamedQuery(MaeRolUsuario.AUTENTICAR_USUARIO, MaeUsuario.class);
			query.setParameter(MaeRolUsuario.P_COD_USUARIO, usuario);
			query.setParameter(MaeRolUsuario.P_COD_ROL, codigoRol);
			query.setParameter(MaeRolUsuario.P_COD_CLIENTE, codigoCliente);
			query.setParameter(MaeRolUsuario.P_N_APLICATIVO, nAplicacion);
			MaeUsuario usr =  query.getSingleResult();
			log.info("usuario clave {}",usr.getCClave());
			String claveFinal = EncryptUtils.encrypt(usuario, clave);
			
			log.info("clave enviada por el usuario {}",claveFinal);
			if(UtilsSCPide.isNull(usr.getCClave()).trim().equals(claveFinal)) {
				user.setId(usr.getNUsuario());
				user.setCClave(UtilsSCPide.isNull(usr.getCClave()));
			}
			log.info("usuario autenticado correctamente {}",Arrays.toString(params));
		} catch (NoResultException not) {
			log.info(cuo.concat("No se encontro usuario registrado en BD Seguridad con los datos ->").concat(Arrays.toString(params)));
		} catch (Exception e) {
			log.error(cuo.concat(e.getMessage()));
		}
		log.info("resultado que retorna {} ",user.getId() == null? null: user.getId().toString());
		return user.getId() == null? null: user.getId().toString();
	}
	
	@Override
	public UsuarioDTO recuperaInfoUsuario(String cuo, String id) throws Exception {
		UsuarioDTO user = new UsuarioDTO();
		Object[] params = { Integer.parseInt(id) };
		try {
			TypedQuery<MaeUsuario> query = this.sf.getCurrentSession().createNamedQuery(MaeUsuario.FIND_BY_ID, MaeUsuario.class);
			query.setParameter(MaeUsuario.P_N_USUARIO, Integer.parseInt(id));
			MaeUsuario u = query.getSingleResult();
			user.setCClave(u.getCClave());
			user.setCUsuario(u.getCUsuario());
			user.setId(u.getNUsuario());
			user.setLActivo(u.getLActivo());
		} catch (NoResultException not) {
			log.info(cuo.concat("No se encontro usuario registrado en BD con los datos ->").concat(Arrays.toString(params)));
			user = null;
		} catch (Exception e) {
			log.error(cuo.concat(e.getMessage()));
			user = null;
		}
		return user;
	}
	
	@Override
	public List<RolDTO> recuperarRoles(String cuo, String id) throws Exception {
		List<RolDTO> lista = new ArrayList<RolDTO>();
		Object[] params = { Integer.parseInt(id) };
		try {
			TypedQuery<MaeRol> query = this.sf.getCurrentSession().createNamedQuery(MaeRol.FIND_ROLES_BY_ID_USUARIO, MaeRol.class);
			query.setParameter(MaeUsuario.P_N_USUARIO, Integer.parseInt(id));
			query.getResultStream().forEach(maeRol -> {
				lista.add(new RolDTO(maeRol.getNRol(), maeRol.getCRol(), maeRol.getXRol(), maeRol.getLActivo()));
			});
		} catch (NoResultException not) {
			log.info(cuo.concat("No se encontro roles registrado en BD con los datos -> ").concat(Arrays.stream(params)
	                .map(Object::toString)
	                .collect(Collectors.joining(", "))));
		} catch (Exception e) {
			log.error(cuo.concat(e.getMessage()));
		}
		return lista;
	}

	@Override
	public String validarAccesoMetodo(String cuo, String usuario, String rol, String operacion) throws Exception {
		StringBuilder rpta = new StringBuilder("");
		Object[] params = {usuario,rol,operacion};
		log.info("validamos el permiso del metodo {}",Arrays.stream(params));
		try {
			TypedQuery<MaeRolUsuario> query = this.sf.getCurrentSession().createNamedQuery(MaeRolUsuario.VALIDAR_ACCESO_METODO , MaeRolUsuario.class);
			query.setParameter(MaeRolUsuario.P_COD_USUARIO, usuario);
			query.setParameter(MaeRolUsuario.P_COD_ROL, rol);
			query.setParameter(MaeRolUsuario.P_OPERACION, operacion);
			MaeRolUsuario rolusuario = query.getResultStream().findFirst().orElse(null);
			if(rolusuario!=null) {
				rolusuario.getMaeRol().getMaeOperacions().forEach(x->{
					if(x.getXEndpoint().equalsIgnoreCase(operacion))
						rpta.append(x.getXOperacion());
				});
			}
			log.info("validacion Exitosa",Arrays.toString(params));
		} catch (NoResultException not) {
			log.info(cuo.concat("No se encontro permiso a la operacion con el rol del usuario ").concat(Arrays.toString(params)));
		} catch (Exception e) {
			log.error(cuo.concat(e.getMessage()));
		}		
		return rpta.toString();
	}
}
