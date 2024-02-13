package pe.gob.pj.pide.dao.repository.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import pe.gob.pj.pide.dao.dto.PaginationDTO;
import pe.gob.pj.pide.dao.dto.pide.UsuarioDTO;
import pe.gob.pj.pide.dao.entity.pide.MaePerfil;
import pe.gob.pj.pide.dao.entity.pide.MaeUsuario;
import pe.gob.pj.pide.dao.repository.LoginDao;
import pe.gob.pj.pide.dao.utils.ConstantesSCPide;
import pe.gob.pj.pide.dao.utils.UtilsSCPide;


@Component("seguridadDao")
public class LoginDaoImpl implements LoginDao, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(LoginDaoImpl.class);
	
	@Autowired
	@Qualifier("sessionSeguridadPide")
	private SessionFactory sf;

	
	@Override
	public UsuarioDTO login(String cuo, String cUsuario) throws Exception {
		UsuarioDTO usuario = null;
		
		TypedQuery<MaeUsuario> query = this.sf.getCurrentSession().createNamedQuery(MaeUsuario.Q_FIND_FY_USUARIO, MaeUsuario.class);
		query.setParameter(MaeUsuario.P_USUARIO, cUsuario);
		MaeUsuario maeUsuario = query.getResultStream().findFirst().orElse(null);
		
		try {
			if(maeUsuario != null) {
				usuario = new UsuarioDTO();
				usuario.setIdUsuario(maeUsuario.getIdUsuario());
				usuario.setUsuario(maeUsuario.getUsuario());
				usuario.setClave(maeUsuario.getClave());
				usuario.setApellidosNombres(maeUsuario.getApellidosNombres());
				usuario.setActivo(maeUsuario.getActivo().equalsIgnoreCase(ConstantesSCPide.ESTADO_ACTIVO)?"Activo":"Inactivo");
				usuario.setFechaCambioClave(maeUsuario.getFechaCambioClave()!=null?UtilsSCPide.convertDateToString(maeUsuario.getFechaCambioClave(), ConstantesSCPide.PATTERN_FECHA_DD_MM_YYYY) : "");
				usuario.setFechaRegistro(maeUsuario.getFechaRegistro()!=null?UtilsSCPide.convertDateToString(maeUsuario.getFechaRegistro(), ConstantesSCPide.PATTERN_FECHA_DD_MM_YYYY) : "");
				usuario.setIdPerfil(maeUsuario.getPerfil().getIdPerfil());
				usuario.setDescripcionPerfil(maeUsuario.getPerfil().getDescripcion());
				usuario.setNombrePerfil(maeUsuario.getPerfil().getNombre());
				usuario.setCodigoRol(maeUsuario.getPerfil().getRol());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("{} Error: {}", cuo, e.getMessage());
		}
		return usuario;
	}

	@Override
	public UsuarioDTO obtenerUsuario(String cuo, Map<String, Object> filtros) throws Exception {
		UsuarioDTO usuario = null;
		
//		this.sf.getCurrentSession().enableFilter(MaeUsuario.F_ACTIVO)
//			.setParameter(MaeUsuario.P_ACTIVO, ConstantesSCPide.ESTADO_ACTIVO);
		
		if(!UtilsSCPide.isNullOrEmpty(filtros.get(MaeUsuario.P_ID_USUARIO))) {
			this.sf.getCurrentSession().enableFilter(MaeUsuario.F_ID_USUARIO)
				.setParameter(MaeUsuario.P_ID_USUARIO, filtros.get(MaeUsuario.P_ID_USUARIO));
		}
		
		TypedQuery<MaeUsuario> query = this.sf.getCurrentSession().createNamedQuery(MaeUsuario.Q_FIND_BY_FILTERS, MaeUsuario.class);
		MaeUsuario maeUsuario = query.getResultStream().findFirst().orElse(null);
		
		try {
			if(maeUsuario != null) {
				usuario = new UsuarioDTO();
				usuario.setIdUsuario(maeUsuario.getIdUsuario());
				usuario.setUsuario(maeUsuario.getUsuario());
				usuario.setClave(maeUsuario.getClave());
				usuario.setActivo(maeUsuario.getActivo().equalsIgnoreCase(ConstantesSCPide.ESTADO_ACTIVO)?"Activo":"Inactivo");
				usuario.setFechaCambioClave(maeUsuario.getFechaCambioClave()!=null?UtilsSCPide.convertDateToString(maeUsuario.getFechaCambioClave(), ConstantesSCPide.PATTERN_FECHA_DD_MM_YYYY) : "");
				usuario.setFechaRegistro(maeUsuario.getFechaRegistro()!=null?UtilsSCPide.convertDateToString(maeUsuario.getFechaRegistro(), ConstantesSCPide.PATTERN_FECHA_DD_MM_YYYY) : "");
				usuario.setIdPerfil(maeUsuario.getPerfil().getIdPerfil());
				usuario.setDescripcionPerfil(maeUsuario.getPerfil().getDescripcion());
				usuario.setNombrePerfil(maeUsuario.getPerfil().getNombre());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("{} Error: {}", cuo, e.getMessage());
		}
		return usuario;
	}

	@Override
	public boolean cambiarClave(String cuo, int idUsuario, String claveNueva) throws Exception {
		boolean rpta = false;
		try {
			Query query = this.sf.getCurrentSession().createNamedQuery(MaeUsuario.Q_USUARIO_UPDATE_CLAVE);
			query.setParameter(MaeUsuario.P_CLAVE_NUEVA, claveNueva);
			query.setParameter(MaeUsuario.P_FECHA_CAMBIO, UtilsSCPide.getFechaActualTimestamp());
			query.setParameter(MaeUsuario.P_ID_USUARIO, idUsuario);
			int countUpdate = query.executeUpdate();
			rpta = countUpdate>0;
		} catch (Exception e) {
			logger.info("{} Error cambiarClave: {}", cuo, e.getMessage());
			e.printStackTrace();
		}
				
		return rpta;
	}

	@Override
	public boolean registrarUsuario(String cuo, UsuarioDTO usuario) throws Exception {

		boolean rpta = false;
		MaeUsuario usuarioPrevio;
		MaePerfil perfil;
		
		//usuario existente
		this.sf.getCurrentSession().enableFilter(MaeUsuario.F_USUARIO)
			.setParameter(MaeUsuario.P_USUARIO, usuario.getUsuario());
		
		TypedQuery<MaeUsuario> query_ = this.sf.getCurrentSession().createNamedQuery(MaeUsuario.Q_FIND_BY_FILTERS, MaeUsuario.class);
		usuarioPrevio = query_.getResultStream().findFirst().orElse(null);
		
		//perfil
		this.sf.getCurrentSession().enableFilter(MaePerfil.F_ID_PERFIL)
			.setParameter(MaePerfil.P_ID_PERFIL, usuario.getIdPerfil());
		
		TypedQuery<MaePerfil> query_pefil = this.sf.getCurrentSession().createNamedQuery(MaePerfil.Q_FIND_FILTERS, MaePerfil.class);
		perfil = query_pefil.getResultStream().findFirst().orElse(null);
		
		if(perfil == null)  {
			throw new Exception("El perfil ingresado no existe o esta inactivo.");
		}
		
		if(usuarioPrevio != null)  {
			throw new Exception("No se pudo realizar el registro de usuario, debido a que  el usuario ingresado ya existe.");
		}
		
		try {
			
			usuarioPrevio = new MaeUsuario();
			usuarioPrevio.setUsuario(usuario.getUsuario());
			usuarioPrevio.setClave(usuario.getClave());
			usuarioPrevio.setPerfil(perfil);
			usuarioPrevio.setActivo(usuario.getActivo());
			usuarioPrevio.setApellidosNombres(usuario.getApellidosNombres().trim().toUpperCase());
			
			usuarioPrevio.setCAud(ConstantesSCPide.SQL_ACCION_INSERT);
			usuarioPrevio.setFAud(UtilsSCPide.getFechaActualDate());
			usuarioPrevio.setCAudIp(UtilsSCPide.getIp());
			usuarioPrevio.setCAudMcAddr(UtilsSCPide.getMac());
			usuarioPrevio.setCAudPc(UtilsSCPide.getPc());
			usuarioPrevio.setFechaRegistro(UtilsSCPide.getFechaActualDate());
			
			this.sf.getCurrentSession().save(usuarioPrevio);
			
			rpta = usuarioPrevio.getIdUsuario() > 0;
			
		} catch (Exception e) {
			logger.error("{} Error en dao registrarUsuario: {}", cuo, e.getMessage());
			e.printStackTrace();
		}
		
		return rpta;
	}

	@Override
	public boolean modificarUsuario(String cuo, UsuarioDTO usuario) throws Exception {
		
		boolean rpta = false;
		MaeUsuario usuarioPrevio;
		MaePerfil perfil;
		
		//usuario existente
		this.sf.getCurrentSession().enableFilter(MaeUsuario.F_USUARIO)
					.setParameter(MaeUsuario.P_USUARIO, usuario.getUsuario());	
		TypedQuery<MaeUsuario> query_ = this.sf.getCurrentSession().createNamedQuery(MaeUsuario.Q_FIND_BY_FILTERS, MaeUsuario.class);
		usuarioPrevio = query_.getResultStream().findFirst().orElse(null);
				
		//perfil
		this.sf.getCurrentSession().enableFilter(MaePerfil.F_ID_PERFIL)
					.setParameter(MaePerfil.P_ID_PERFIL, usuario.getIdPerfil());	
		TypedQuery<MaePerfil> query_pefil = this.sf.getCurrentSession().createNamedQuery(MaePerfil.Q_FIND_FILTERS, MaePerfil.class);
		perfil = query_pefil.getResultStream().findFirst().orElse(null);
		
		if(perfil == null)  {
			throw new Exception("El perfil ingresado no existe o esta inactivo.");
		}
		
		if(usuarioPrevio == null)  {
			throw new Exception("No se pudo realizar la modificación debido a que no se encontro coincidencias para el usuario ingresado.");
		}
		
		try {
			
			usuarioPrevio.setUsuario(usuario.getUsuario());
			usuarioPrevio.setPerfil(perfil);
			usuarioPrevio.setActivo(usuario.getActivo());
			usuarioPrevio.setApellidosNombres(usuario.getApellidosNombres().trim().toUpperCase());
			
			usuarioPrevio.setCAud(ConstantesSCPide.SQL_ACCION_UPDATE);
			usuarioPrevio.setFAud(UtilsSCPide.getFechaActualDate());
			usuarioPrevio.setCAudIp(UtilsSCPide.getIp());
			usuarioPrevio.setCAudMcAddr(UtilsSCPide.getMac());
			usuarioPrevio.setCAudPc(UtilsSCPide.getPc());
			usuarioPrevio.setFechaRegistro(UtilsSCPide.getFechaActualDate());
			
			this.sf.getCurrentSession().update(usuarioPrevio);
			
			rpta = usuarioPrevio.getIdUsuario() > 0;
			
		} catch (Exception e) {
			logger.error("{} Error en dao registrarUsuario: {}", cuo, e.getMessage());
			e.printStackTrace();
		}
		
		return rpta;
	}

	@Override
	public PaginationDTO buscarUsuario(String cuo, Map<String, Object> filtros, Integer page, Integer pageSize)
			throws Exception {
		PaginationDTO pagination = new PaginationDTO();
		List<UsuarioDTO> lista = new ArrayList<>();
		
		pagination.setPageSize(pageSize);
		pagination.setFirstPage(0);
		try {
			logger.info("{} Inicio de :{}", cuo, "buscarUsuario");
			if(!UtilsSCPide.isNullOrEmpty(filtros.get(MaeUsuario.P_ID_PERFIL)) && Integer.parseInt(String.valueOf(filtros.get(MaeUsuario.P_ID_PERFIL)))>0) {
				this.sf.getCurrentSession().enableFilter(MaeUsuario.F_ID_PERFIL)
					.setParameter(MaeUsuario.P_ID_PERFIL, filtros.get(MaeUsuario.P_ID_PERFIL));
				logger.info("{} Filtro "+MaeUsuario.P_ID_PERFIL+" : {}", cuo, filtros.get(MaeUsuario.P_ID_PERFIL));
			}
			if(!UtilsSCPide.isNullOrEmpty(filtros.get(MaeUsuario.P_USUARIO))) {
				this.sf.getCurrentSession().enableFilter(MaeUsuario.F_USUARIO)
					.setParameter(MaeUsuario.P_USUARIO, filtros.get(MaeUsuario.P_USUARIO));
				logger.info("{} Filtro "+MaeUsuario.P_USUARIO+" : {}", cuo, filtros.get(MaeUsuario.P_USUARIO));
			}
			if(!UtilsSCPide.isNullOrEmpty(filtros.get(MaeUsuario.P_ACTIVO))) {
				this.sf.getCurrentSession().enableFilter(MaeUsuario.F_ACTIVO)
					.setParameter(MaeUsuario.P_ACTIVO, filtros.get(MaeUsuario.P_ACTIVO));
				logger.info("{} Filtro "+MaeUsuario.P_ACTIVO+" : {}", cuo, filtros.get(MaeUsuario.P_ACTIVO));
			}
			
			TypedQuery<MaeUsuario> query = this.sf.getCurrentSession().createNamedQuery(MaeUsuario.Q_FIND_BY_FILTERS, MaeUsuario.class);
			query.getResultStream().forEach(x->{
				UsuarioDTO udto = new UsuarioDTO();
				udto.setIdUsuario(x.getIdUsuario());
				udto.setUsuario(x.getUsuario());
				udto.setApellidosNombres(x.getApellidosNombres());
				udto.setActivo(x.getActivo().equalsIgnoreCase(ConstantesSCPide.ESTADO_ACTIVO)?"Activo":"Inactivo");
				udto.setClave("*******");
				udto.setFechaRegistro(x.getFechaRegistro()!=null?UtilsSCPide.convertDateToString(x.getFechaRegistro(), ConstantesSCPide.PATTERN_FECHA_DD_MM_YYYY) : "");
				udto.setFechaCambioClave(x.getFechaCambioClave()!=null?UtilsSCPide.convertDateToString(x.getFechaCambioClave(), ConstantesSCPide.PATTERN_FECHA_DD_MM_YYYY) : "");
				
				udto.setIdPerfil(x.getPerfil().getIdPerfil());
				udto.setNombrePerfil(x.getPerfil().getNombre());
				udto.setDescripcionPerfil(x.getPerfil().getDescripcion());
				
				lista.add(udto);
			});
			
		} catch (Exception e) {
			logger.error("{}Error en buscarUsuario sin paginado: {}", cuo, e.getMessage());
			e.printStackTrace();
		}
		return pagination;
	}

	@Override
	public PaginationDTO buscarUsuario(String cuo, Map<String, Object> filtros) throws Exception {
		
		PaginationDTO pagination = new PaginationDTO();
		List<UsuarioDTO> lista = new ArrayList<>();
		
		try {
			logger.info("{} Inicio de :{}", cuo, "buscarUsuario");
			if(!UtilsSCPide.isNullOrEmpty(filtros.get(MaeUsuario.P_ID_PERFIL)) && Integer.parseInt(String.valueOf(filtros.get(MaeUsuario.P_ID_PERFIL)))>0) {
				this.sf.getCurrentSession().enableFilter(MaeUsuario.F_ID_PERFIL)
					.setParameter(MaeUsuario.P_ID_PERFIL, filtros.get(MaeUsuario.P_ID_PERFIL));
				logger.info("{} Filtro "+MaeUsuario.P_ID_PERFIL+" : {}", cuo, filtros.get(MaeUsuario.P_ID_PERFIL));
			}
			if(!UtilsSCPide.isNullOrEmpty(filtros.get(MaeUsuario.P_USUARIO))) {
				this.sf.getCurrentSession().enableFilter(MaeUsuario.F_USUARIO)
					.setParameter(MaeUsuario.P_USUARIO, filtros.get(MaeUsuario.P_USUARIO));
				logger.info("{} Filtro "+MaeUsuario.P_USUARIO+" : {}", cuo, filtros.get(MaeUsuario.P_USUARIO));
			}
			if(!UtilsSCPide.isNullOrEmpty(filtros.get(MaeUsuario.P_ACTIVO))) {
				this.sf.getCurrentSession().enableFilter(MaeUsuario.F_ACTIVO)
					.setParameter(MaeUsuario.P_ACTIVO, filtros.get(MaeUsuario.P_ACTIVO));
				logger.info("{} Filtro "+MaeUsuario.P_ACTIVO+" : {}", cuo, filtros.get(MaeUsuario.P_ACTIVO));
			}
			
			TypedQuery<MaeUsuario> query = this.sf.getCurrentSession().createNamedQuery(MaeUsuario.Q_FIND_BY_FILTERS, MaeUsuario.class);
			query.getResultStream().forEach(x->{
				UsuarioDTO udto = new UsuarioDTO();
				udto.setIdUsuario(x.getIdUsuario());
				udto.setUsuario(x.getUsuario());
				udto.setApellidosNombres(x.getApellidosNombres());
				udto.setActivo(x.getActivo().equalsIgnoreCase(ConstantesSCPide.ESTADO_ACTIVO)?"Activo":"Inactivo");
				udto.setClave("*******");
				udto.setFechaRegistro(x.getFechaRegistro()!=null?UtilsSCPide.convertDateToString(x.getFechaRegistro(), ConstantesSCPide.PATTERN_FECHA_DD_MM_YYYY) : "");
				udto.setFechaCambioClave(x.getFechaCambioClave()!=null?UtilsSCPide.convertDateToString(x.getFechaCambioClave(), ConstantesSCPide.PATTERN_FECHA_DD_MM_YYYY) : "");
				
				udto.setIdPerfil(x.getPerfil().getIdPerfil());
				udto.setNombrePerfil(x.getPerfil().getNombre());
				udto.setDescripcionPerfil(x.getPerfil().getDescripcion());
				
				lista.add(udto);
			});
			
			pagination.setList(lista);
			
		} catch (Exception e) {
			logger.error("{}Error en buscarUsuario sin paginado: {}", cuo, e.getMessage());
			e.printStackTrace();
		}
		

		return pagination;
	}

}
