package pe.gob.pj.pide.dao.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import pe.gob.pj.pide.dao.dto.CaptchaValidDTO;

public class CaptchaUtils {
	
	private static final Logger logger = LogManager.getLogger(CaptchaUtils.class);
	
	public static final boolean validCaptcha(String token, String remoteIp, String cuo) {
		try {
			
			String URL = ProjectProperties.getInstance().getCaptchaUrl();
			String TOKEN = ProjectProperties.getInstance().getCaptchaToken();
			
			RestTemplate plantilla = new RestTemplate();			
			UriComponents builder = UriComponentsBuilder.fromHttpUrl(URL)
		                .queryParam("secret", TOKEN)
		                .queryParam("response", token)
		                .queryParam("remoteip", remoteIp).build();
			logger.info("{} {}", cuo ,  builder.toUriString());
			CaptchaValidDTO resultado = plantilla.postForObject(builder.toUriString(), null, CaptchaValidDTO.class);
			logger.info("{} {}", cuo, resultado.getSuccess());
			
			
			if(resultado.getSuccess().equals("true")) {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			logger.error("{} {}", cuo, UtilsSCPide.isNull(e.getCause()).concat(" ").concat(e.getMessage()));
			logger.fatal(cuo, e);
		}
		return Boolean.FALSE;
	}

}
