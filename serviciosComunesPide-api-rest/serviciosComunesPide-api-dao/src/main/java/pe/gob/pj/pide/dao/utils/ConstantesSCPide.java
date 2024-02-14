package pe.gob.pj.pide.dao.utils;

public class ConstantesSCPide {

	public static final int DEFAULT_PAGINATION_PAGE_SIZE = 10;
	
	public static final int TOKEN_TIEMPO_PARA_EXPIRAR_SEGUNDOS = 300;
	public static final int NRO_VECES_REFRESH_CON_TOKEN_EXPIRADO = 100;
	public static final String METHOD_CORTA_ULTIMA_BARRA_INVERTIDA = "PUT";
	
	public static final String VERSION = "1.1.0";

	public static final String ESQUEMA_SEGURIDAD = "seguridad";
	public static final String ESQUEMA_PIDE = "pide";

	
	public static final String TOKEN_ACCESO_INTERNO = "in";
	public static final String TOKEN_ACCESO_EXTERNO = "out";
	
	public final static String PATTERN_NUMBER = "[0-9]+";
	public static final String PATTERN_IP = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	public static final String PATTERN_MAC = "([0-9A-F]{2}[:-]){5}([0-9A-F]{2})";
	public static final String PATTERN_ENDPOINT = "^/[a-zA-Z0-9\\-]+/[a-zA-Z0-9\\-]+/[a-zA-Z0-9\\-]+$";
	
	public static final String PATTERN_FECHA_YYYYMMDD = "yyyyMMdd";
	public static final String PATTERN_FECHA_YYYY_MM_DD = "yyyy/MM/dd";
	public static final String PATTERN_FECHA_YYYY_MM_DD_ = "yyyy-MM-dd";
	public static final String PATTERN_FECHA_DD_MM_YYYY = "dd/MM/yyyy";
	public static final String PATTERN_FECHA_DD_MM_YYYY_HH_MM = "dd/MM/yyyy hh:mm a";

	public static final String SQL_ACCION_INSERT = "I";
	public static final String SQL_ACCION_UPDATE = "U";
	
	public static final String ESTADO_ACTIVO = "1";
	public static final String ESTADO_INACTIVO = "0";

	public static final String RPTA_0 = "0";
	public static final String RPTA_1 = "1";
	
	public static final Integer ESTADO_SOLICITUD_ID_PENDIENTE = 1;
	public static final Integer ESTADO_SOLICITUD_ID_APROBADO = 2;
	public static final Integer ESTADO_SOLICITUD_ID_RECHAZADO = 3;

	public static final Integer TIPO_SOLICITUD_ID_ACCESO = 1;
	public static final Integer TIPO_SOLICITUD_ID_CUOTA = 2;
	public static final Integer TIPO_SOLICITUD_ID_IP = 3;
	public static final Integer TIPO_SOLICITUD_ID_ESTADO = 4;
	
	public static final String TIPO_SOLICITUD_IMPACTO_ACCESO = "ACCESO";
	public static final String TIPO_SOLICITUD_IMPACTO_CUOTA = "CUOTA";
	public static final String TIPO_SOLICITUD_IMPACTO_IP = "IP";
	public static final String TIPO_SOLICITUD_IMPACTO_ESTADO = "ESTADO";

	public static final String AUD_CUO = "cuo";
	public static final String AUD_LOG = "log";
	public static final String HASH_MAP_LOCAL = "infoLocal";
	public static final String REMOTE_IP = "ipRemota";
	public static final String TOKEN_ADMIN = "tokenAdmin";
	public static final String VALID_TOKEN_ADMIN = "validTokenAdmin";

	public static final String CLAIM_ROL = "rol";
	public static final String CLAIM_USUARIO = "usuario";
	public static final String CLAIM_IP = "remoteIp";
	public static final String CLAIM_NUMERO = "numero";
	public static final String CLAIM_INIPAGE = "page_i";
	public static final String CLAIM_LIMIT= "limit";
	public static final String CLAIM_ACCESO = "log_";

	public static final String C_500 = "500";
	public static final String C_404 = "404";
	
	public static final String C_400 = "400";
	public static final String C_401 = "401";
	public static final String C_403 = "403";
	
	public static final String C_ERROR = "E";
	public static final String C_EXITO  = "0000";
	public static final String C_0001 = "0001";
	public static final String C_E000 = "E000";
	public static final String C_E001 = "E001";
	public static final String C_E002 = "E002";
	public static final String C_E003 = "E003";
	public static final String C_E004 = "E004";
	public static final String C_E005 = "E005";
	public static final String C_E013 = "E013";
	public static final String C_E014 = "E014";
	public static final String C_E015 = "E015";

	public static final String X_TRAZA_LOG ="TRAZA-LOG";	
	public static final String X_CML_NOT_FOUND = "ERROR: No se puede identificar las CLASE-METODO-LINEA.";
	
	public static final String X_EXITO = "La operación se realizo de manera exitosa.";
	public static final String X_0001 = "Datos no encontrados al ";
	public static final String X_E000 = " - Error inesperado.";
	public static final String X_E001 = "Lo sentimos, la sesión ha expirado.";
	public static final String X_E002 = "Lo sentimos, hubo un problema al validar el token.";
	public static final String X_E003 = "Error al generar un nuevo token, el tiempo límite para refrescar el token enviado ha expirado.";
	public static final String X_ERROR = "Error al ";
	public static final String X_E004 = " - Problemas en la ejecución del SP: ";
	public static final String X_E005 = " - No se estableció conexión con la BD PJ Seguridad.";
	public static final String X_E013 = " - Problemas con el consumo del endpoint: ";
	public static final String X_E014 = " - El token captcha es nulo o no es válido. ";
	public static final String X_E015 = " - problemas en la ejecución de la sentencia JPA. ";
	
	public static final String STRING_S="S";
	
	//reniec
	public static final String LETRA_VACIO="";
	public static final String STRING_DOS= "2";
	
	//Codigos de respuestas
	public static final String CODIGO_OPERACION_CORRECTA_WS="0000";
	

	public class Mensajes {
		public static final String MSG_ERROR_GENERICO_CONVERSION = "El tipo de dato de entrada es incorrecto";
	}
	
	public class Reniec {
		public static final String STRING_END_POINT = "pe.gob.pj.scpide.servicio.wsreniec.endpoint";
		public static final String STRING_TIME_OUT = "pe.gob.pj.scpide.servicio.wsreniec.timeout";
		public static final String STRING_DNI_CONSULTANTE = "pe.gob.pj.scpide.servicio.wsreniec.dniconsultante";
	}
	
	public class Pide {
		public static final String PARAM_CONFIG_ENDPOINT_PIDE = "pe.gob.pj.scpide.servicio.wspide.endpoint";
		public static final String PARAM_CONFIG_TIMEOUT_PIDE = "pe.gob.pj.scpide.servicio.wspide.timeout";
		public static final String PARAM_CONFIG_USER_PIDE = "pe.gob.pj.scpide.servicio.wspide.user";
		public static final String PARAM_CONFIG_PASS_PIDE = "pe.gob.pj.scpide.servicio.wspide.pass";
		public static final String PARAM_CONFIG_CODIGO_APLICATIVO_PIDE = "pe.gob.pj.scpide.servicio.wspide.appCode";
		public static final String PARAM_CONFIG_CODIGO_ROL_PIDE = "pe.gob.pj.scpide.servicio.wspide.rol";
		public static final String PARAM_CONFIG_CODIGO_CLIENTE_PIDE = "pe.gob.pj.scpide.servicio.wspide.clientCode";
	}

	public class Seguridad {
		public static final String SECRET_TOKEN = "configuracion.seguridad.secretToken";
		
		public static final String ID_APLICATIVO = "configuracion.seguridad.idaplicativo";
		
		public static final String USUARIO_AUTH_SEGURIDAD = "configuracion.seguridad.codigo.auth.seguridad";
		public static final String PASSWORD_AUTH_SEGURIDAD = "configuracion.seguridad.password.auth.seguridad";
		public static final String USUARIO_AUTH_SEGURIDAD_CLIENTE = "configuracion.seguridad.codigo.usuario.cliente.auth.seguridad";
		public static final String PASSWORD_AUTH_SEGURIDAD_CLIENTE = "configuracion.seguridad.password.usuario.cliente.auth.seguridad";
	}
	
	public class Captcha{
		public static final String CAPTCHA_TOKEN = "consulta.scpide.captcha.token";
		public static final String CAPTCHA_URL = "consulta.scpide.captcha.url";
	}

}
