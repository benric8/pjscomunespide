package pe.gob.pj.pide.dao;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import pe.gob.pj.pide.dao.utils.UtilsSCPide;
import pe.gob.pj.seguridad.client.dto.ResponseAccesoMetodoDTO;
import pe.gob.pj.seguridad.client.dto.SeguridadDTO;
import pe.gob.pj.seguridad.client.dto.TokenDTO;
import pe.gob.pj.seguridad.client.service.ClientSeguridadService;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

	@Autowired
	private ClientSeguridadService clientSeguridadService;

	private TokenDTO token;

	private List<ResponseAccesoMetodoDTO> accesos;
	
	@Value("${configuracion.seguridad.idaplicativo}")
	private String idAplicativo;
	
	@Value("${configuracion.seguridad.codigo.auth.seguridad}")
	private String usuarioAuthSeguridad;
	
	@Value("${configuracion.seguridad.password.auth.seguridad}")
	private String passwordAuthSeguridad;
	
	@Value("${configuracion.seguridad.codigo.usuario.cliente.auth.seguridad}")
	private String usuarioAuthSeguridadCliente;
	
	@Value("${configuracion.seguridad.password.usuario.cliente.auth.seguridad}")
	private String passwordAuthSeguridadCliente;
	
	/* Creación de conexión con base de datos seguridad esquema pide*/
	@Bean(name = "cxSeguridadPideDS")
	public DataSource jndiConexionSeguridadPide() throws IllegalArgumentException, NamingException {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("java:jboss/datasources/serviciosComunesPideApiDS");
		bean.setProxyInterface(DataSource.class);
		bean.setLookupOnStartup(false);
		bean.setCache(true);
		bean.afterPropertiesSet();
		return (DataSource) bean.getObject();
	}	
		
	@Bean(name = "sessionSeguridadPide")
	public SessionFactory getSessionFactorySeguridadPide(@Qualifier("cxSeguridadPideDS") DataSource seguridadDS) throws IOException {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setPackagesToScan("pe.gob.pj.pide.dao.entity.pide");
		sessionFactoryBean.setHibernateProperties(getHibernateProperties());
		sessionFactoryBean.setDataSource(seguridadDS);
		sessionFactoryBean.afterPropertiesSet();
		return sessionFactoryBean.getObject();
	}

	@Bean(name = "txManagerSeguridadPide")
	public HibernateTransactionManager getTransactionManagerSeguridadPide(@Qualifier("sessionSeguridadPide") SessionFactory sessionSeguridad) throws IOException {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionSeguridad);
		return transactionManager;
	} 	
	
	private static Properties getHibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");
		hibernateProperties.put("hibernate.show_sql", false);
		return hibernateProperties;
	}	

	public TokenDTO getToken() {
		return token;
	}

	public void setToken(TokenDTO token) {
		this.token = token;
	}	

	public List<ResponseAccesoMetodoDTO> getAccesos() {
		try {
			if(accesos == null || accesos.size() <= 0) {
				
				SeguridadDTO seg = new SeguridadDTO();
				seg.setPasswordSpring(passwordAuthSeguridad);
				seg.setUsuarioSpring(usuarioAuthSeguridad);
				seg.setPassword(passwordAuthSeguridadCliente);
				seg.setUsername(usuarioAuthSeguridadCliente);

				String cuo = UtilsSCPide.obtenerCodigoUnico();
				token = clientSeguridadService.recuperaToken(cuo, seg);
				this.accesos = clientSeguridadService.validarAccesoMetodoPorAplicativo(cuo, Integer.parseInt(idAplicativo),token.getAccess_token());
			}			
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return accesos;
	}

	public void setAccesos(List<ResponseAccesoMetodoDTO> accesos) {
		this.accesos = accesos;
	}

	public String getIdAplicativo() {
		return idAplicativo;
	}

	public String getUsuarioAuthSeguridad() {
		return usuarioAuthSeguridad;
	}

	public String getPasswordAuthSeguridad() {
		return passwordAuthSeguridad;
	}

	public String getUsuarioAuthSeguridadCliente() {
		return usuarioAuthSeguridadCliente;
	}

	public String getPasswordAuthSeguridadCliente() {
		return passwordAuthSeguridadCliente;
	}
	
}