package pe.gob.pj.pide.ws.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import org.springframework.stereotype.Service;

import pe.gob.pj.pide.dao.utils.ProjectProperties;
import pe.gob.pj.pide.dao.utils.UtilsSCPide;
import pe.gob.pj.pide.ws.SunatWsService;
import pe.gob.pj.pide.ws.bean.EntidadPideBean;
import pe.gob.pj.pide.ws.bean.ParamConfigPideBean;
import pe.gob.pj.pidews.handler.PideHandler;
import pe.gob.pj.pidews.type.RequestConsultarDatosPrincipalesRUCType;
import pe.gob.pj.pidews.type.ResponseConsultarDatosPrincipalesRUCType;
import pe.gob.pj.pidews.ws.Auditoria;
import pe.gob.pj.pidews.ws.PideServicio;
import pe.gob.pj.pidews.ws.PideServicio_Service;
import pe.gob.pj.pidews.ws.Seguridad;

@Service("sunatWsService")
public class SunatWsServiceImpl implements SunatWsService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public EntidadPideBean consultarSunatxRuc(String rucConsulta) {
		EntidadPideBean entidad = new EntidadPideBean();
		try {
			ParamConfigPideBean entidadParametros = new ParamConfigPideBean();
			entidadParametros.setCodigoAplicativo(ProjectProperties.getInstance().getCodigoAplicativoPide());
			entidadParametros.setCodigoCliente(ProjectProperties.getInstance().getCodigoClientePide());
			entidadParametros.setCodigoRol(ProjectProperties.getInstance().getCodigoRolPide());
			entidadParametros.setEndpoint(ProjectProperties.getInstance().getEndpointPide());
			entidadParametros.setPass(ProjectProperties.getInstance().getPassPide());
			entidadParametros.setTimeout(ProjectProperties.getInstance().getTimeoutPide());
			entidadParametros.setUser(ProjectProperties.getInstance().getUserPide());

			RequestConsultarDatosPrincipalesRUCType requestDatosPrincipalesRUC = new RequestConsultarDatosPrincipalesRUCType();
			requestDatosPrincipalesRUC.setCRUC(rucConsulta);
			
			PideServicio port = getPortPide(entidadParametros);
			
			ResponseConsultarDatosPrincipalesRUCType responsePide = port.consultarDatosPrincipalesRUC(getSeguridadPide(entidadParametros), getAuditoriaPide(), requestDatosPrincipalesRUC);
			entidad.setNroRuc(responsePide.getDatosPrincipalesRUC().getCNumruc());		
			entidad.setRazonSocial(responsePide.getDatosPrincipalesRUC().getXNombre());

		} catch (Exception e) {
			entidad=null;
			e.printStackTrace();
		}
		return entidad;
	}
	
	@SuppressWarnings("rawtypes")
	public static PideServicio getPortPide(ParamConfigPideBean paramConfigPideBean) {
		
		PideServicio_Service pideService = new PideServicio_Service();
		PideServicio port = pideService.getPideServicioSOAP();

		List<Handler> handlerChain = new ArrayList<Handler>(); // preguntar a oscar
		handlerChain.add(new PideHandler(paramConfigPideBean.getUser(), paramConfigPideBean.getPass()));

		BindingProvider bp = (BindingProvider) port;
		bp.getBinding().setHandlerChain(handlerChain);
		// Se asigna el endpoint del WS d
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, paramConfigPideBean.getEndpoint());
		// Se asigna los tiempos de timeout de la consulta al WS
		bp.getRequestContext().put("com.sun.xml.ws.connect.timeout",Integer.parseInt(paramConfigPideBean.getTimeout()) * 1000);
		bp.getRequestContext().put("com.sun.xml.ws.request.timeout",Integer.parseInt(paramConfigPideBean.getTimeout()) * 1000);

		return port;
	}
	
	private Seguridad getSeguridadPide(ParamConfigPideBean paramConfigPideBean){
		Seguridad seguridad = new Seguridad();
		seguridad.setCodigoAplicativo(paramConfigPideBean.getCodigoAplicativo());
		seguridad.setCodigoCliente(paramConfigPideBean.getCodigoCliente());
		seguridad.setCodigoRol(paramConfigPideBean.getCodigoRol());
		
		return seguridad;
	}
	
	private Auditoria getAuditoriaPide(){
		Auditoria auditoria = new Auditoria();		
		auditoria.setIpPc(UtilsSCPide.getIp());
		auditoria.setMacAddressPc(UtilsSCPide.getMac());
		auditoria.setNombreSo("");
		auditoria.setPcName(UtilsSCPide.getPc());
		auditoria.setUsuarioRed("SCPAPI");
		auditoria.setUsuarioSis("SCPAPI");		
		return auditoria;
	}

}
