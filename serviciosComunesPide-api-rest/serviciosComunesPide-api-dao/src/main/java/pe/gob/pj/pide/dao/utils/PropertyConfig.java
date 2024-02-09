package pe.gob.pj.pide.dao.utils;


import java.io.Serializable;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Getter
@Configuration
@PropertySources(value = { @PropertySource("classpath:application.properties") })
@Component
public class PropertyConfig implements Serializable{
	
	private static final long serialVersionUID = 1L;	
	
	@Value("${configuracion.seguridad.secretToken:null}")
	private String seguridadSecretToken;
	
	@Value("${configuracion.seguridad.idaplicativo:0}")
	private Integer seguridadIdAplicativo;
	
	@Value("${configuracion.seguridad.authenticate.token.tiempo.expira.segundos:600}")
	private Integer seguridadTiempoExpiraSegundos;
	
	@Value("${configuracion.seguridad.authenticate.token.tiempo.refresh.segundos:300}")
	private Integer seguridadTiempoRefreshSegundos;
	
	// #CAPTCHA
	@Value("${captcha.url:null}")
	private String captchaUrl;
	
	@Value("${captcha.token:null}")
	private String captchaToken;
	
	//# RENIEC
	@Value("${pe.gob.pj.scpide.servicio.wsreniec.endpoint:null}")
	private String EndPointReniec;

	@Value("${pe.gob.pj.scpide.servicio.wsreniec.timeout:null}")
	private String TimeOutReniec;

	@Value("${pe.gob.pj.scpide.servicio.wsreniec.dniconsultante:null}")
	private String DniConsultanteReniec;
	
	@Value("${pe.gob.pj.scpide.servicio.wsreniec.usuario:null}")
	private String usuarioReniec;
	

	// #PIDE
	@Value("${pe.gob.pj.scpide.servicio.wspide.endpoint:null}")
	private String endpointPide;

	@Value("${pe.gob.pj.scpide.servicio.wspide.timeout:null}")
	private String timeoutPide;

	@Value("${pe.gob.pj.scpide.servicio.wspide.user:null}")
	private String userPide;

	@Value("${pe.gob.pj.scpide.servicio.wspide.pass:null}")
	private String passPide;

	@Value("${pe.gob.pj.scpide.servicio.wspide.appCode:null}")
	private String codigoAplicativoPide;

	@Value("${pe.gob.pj.scpide.servicio.wspide.rol:null}")
	private String codigoRolPide;

	@Value("${pe.gob.pj.scpide.servicio.wspide.clientCode:null}")
	private String codigoClientePide ;
	
}

