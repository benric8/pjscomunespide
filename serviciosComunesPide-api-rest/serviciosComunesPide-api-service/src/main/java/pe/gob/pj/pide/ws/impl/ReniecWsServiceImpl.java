package pe.gob.pj.pide.ws.impl;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import org.springframework.stereotype.Service;

import pe.gob.pj.pide.dao.utils.ConstantesSCPide;
import pe.gob.pj.pide.dao.utils.ProjectProperties;
import pe.gob.pj.pide.dao.utils.UtilsSCPide;
import pe.gob.pj.pide.ws.ReniecWsService;
import pe.gob.pj.pide.ws.bean.ParamConfigReniecBean;
import pe.gob.pj.pide.ws.bean.PersonaBean;
import pe.gob.pj.pide.ws.bean.PersonaReniecBean;
import pe.gob.pj.ws.client.reniec.consultas.wsreniec.ConsultaReniec;
import pe.gob.pj.ws.client.reniec.consultas.wsreniec.ConsultaReniecPortType;
import pe.gob.pj.ws.client.reniec.consultas.wsreniec.ConsultaReniecResponse;
import pe.gob.pj.ws.client.reniec.consultas.wsreniec.ConsultaReniecService;


@Service("reniecWsService")
public class ReniecWsServiceImpl implements ReniecWsService {
	
	@Override
	public PersonaReniecBean consultaReniecxDni(PersonaBean preRegistro) throws Exception{
		
		PersonaReniecBean personaBean= new PersonaReniecBean();
		
		try {
						
			ParamConfigReniecBean beanParam = new ParamConfigReniecBean();
			beanParam.setEndpoint(ProjectProperties.getInstance().getEndPointReniec());
			beanParam.setDniConsultante(ProjectProperties.getInstance().getDniConsultanteReniec());
			beanParam.setTimeout(ProjectProperties.getInstance().getTimeOutReniec());
			
			ConsultaReniec consultaReniecRequest = new ConsultaReniec();
			consultaReniecRequest.setReqDni(preRegistro.getcNumeIdentidad());
			consultaReniecRequest.setReqDniConsultante(beanParam.getDniConsultante());
			consultaReniecRequest.setReqUsuario("SIJ");
			consultaReniecRequest.setReqIp(UtilsSCPide.getIp());
			consultaReniecRequest.setReqTrama(ConstantesSCPide.LETRA_VACIO);
			consultaReniecRequest.setReqTipoConsulta(ConstantesSCPide.STRING_DOS);
			consultaReniecRequest.setReqNombres(ConstantesSCPide.LETRA_VACIO);
			consultaReniecRequest.setReqApellidoPaterno(ConstantesSCPide.LETRA_VACIO);
			consultaReniecRequest.setReqApellidoMaterno(ConstantesSCPide.LETRA_VACIO);
			consultaReniecRequest.setReqNroRegistros(ConstantesSCPide.LETRA_VACIO);
			consultaReniecRequest.setReqGrupo(ConstantesSCPide.LETRA_VACIO);
			consultaReniecRequest.setReqDniApoderado(ConstantesSCPide.LETRA_VACIO);
			consultaReniecRequest.setReqTipoVinculoApoderado(ConstantesSCPide.LETRA_VACIO);
			
			ConsultaReniecPortType port= getPortReniec(beanParam);
			ConsultaReniecResponse response = new ConsultaReniecResponse();
			
			Holder<String> resTrama = new Holder <String>() ;
			Holder<String> resCodigo = new Holder <String>() ;
			Holder<String> resDescripcion = new Holder <String>();
			Holder<String> resTotalRegistros = new Holder <String>();
			Holder<String> resPersona= new Holder <String>();
			Holder<byte[]> resFoto = new Holder <byte[]>();
			Holder<byte[]> resFirma =  new Holder <byte[]>();
			Holder<String> resListaPersonas = new Holder <String>();
			
			port.consultaReniec(consultaReniecRequest.getReqTrama(), consultaReniecRequest.getReqDniConsultante(), consultaReniecRequest.getReqTipoConsulta(), consultaReniecRequest.getReqUsuario(), consultaReniecRequest.getReqIp(), consultaReniecRequest.getReqDni(), consultaReniecRequest.getReqNombres(), 
					consultaReniecRequest.getReqApellidoPaterno(), consultaReniecRequest.getReqApellidoMaterno(), consultaReniecRequest.getReqNroRegistros(), consultaReniecRequest.getReqGrupo(), consultaReniecRequest.getReqDniApoderado(), consultaReniecRequest.getReqTipoVinculoApoderado(), 
					resTrama, resCodigo, resDescripcion, resTotalRegistros, resPersona, resFoto, resFirma, resListaPersonas);
			
			response.setResCodigo(resCodigo.value);
			response.setResDescripcion(resDescripcion.value);
			response.setResFirma(resFirma.value);
			response.setResFoto(resFoto.value);
			response.setResListaPersonas(resListaPersonas.value);
			response.setResPersona(resPersona.value);
			response.setResTotalRegistros(resTotalRegistros.value);
			response.setResTrama(resTrama.value);
			
			/********************************/

			personaBean = cargarPersonaReniecResponseBean(response);
			
			
		}catch(Exception e){
			e.printStackTrace();
			personaBean=null;
		}
		
		return personaBean;
		
	}
	
	
	
	/************************************/
	
	
	
	
	
	public static ConsultaReniecPortType getPortReniec(ParamConfigReniecBean paramConfigReniecBean ) {
		
		ConsultaReniecService service = new ConsultaReniecService();
		ConsultaReniecPortType port = service.getConsultaReniec();
		
	    BindingProvider bp = (BindingProvider)port;
	    // Se asigna el endpoint del WS d
	    bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, paramConfigReniecBean.getEndpoint());
		// Se asigna los tiempos de timeout de la consulta al WS 
		bp.getRequestContext().put("com.sun.xml.ws.connect.timeout", Integer.parseInt(paramConfigReniecBean.getTimeout()) * 1000);
		bp.getRequestContext().put("com.sun.xml.ws.request.timeout", Integer.parseInt(paramConfigReniecBean.getTimeout()) * 1000);
				
		return port;
	}
	
	
	
	public  PersonaReniecBean cargarPersonaReniecResponseBean(ConsultaReniecResponse consultaReniecResponse) {

		String[] arr_pers = consultaReniecResponse.getResPersona().split("\t");
		PersonaReniecBean persona = new PersonaReniecBean();
		
		int i = 0;

		try {
			persona.setNroDNI(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setFlgVerif(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setApePat(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setApeMat(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setApeCas(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setNombres(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setCodUbiDepDom(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setCodUbiProDom(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setCodUbiDisDom(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setCodUbiLocDom(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setDepDom(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setProDom(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setDisDom(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setLocDom(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setEstCiv(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setGraInst(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setEstatura(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setSexo(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setDocSustTipDoc(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setDocSustNroDoc(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setCodUbiDepNac(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setCodUbiProNac(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setCodUbiDisNac(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setCodUbiLocNac(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setDepNac(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setProNac(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setDisNac(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setLocNac(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setFecNac(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setDocPadTipDoc(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setDocPadNumDoc(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setNomPad(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setDocMadTipDoc(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setDocMadNumDoc(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setNomMad(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setFecIns(arr_pers[i++]);
		} catch (Exception e) {
		}
		;
		try {
			persona.setFecExp(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setFecFall(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setConsVot(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setFecCad(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setRestric(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setPrefDir(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setDireccion(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setNroDir(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setBlocOChal(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setInterior(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setUrbanizacion(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setEtapa(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setManzana(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setLote(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setPreBlocOChal(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setPreDptoPisoInt(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setPreUrbCondResid(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setReservado(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setLongitudFoto(Integer.parseInt(arr_pers[i++]));
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setLongitudFirma(Integer.parseInt(arr_pers[i++]));
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setReservadoFotoFirma1(Integer.parseInt(arr_pers[i++]));
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setReservadoFotoFirma2(arr_pers[i++]);
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setFoto(consultaReniecResponse.getResFoto());
		} catch (Throwable ex) {
		}
		;
		try {
			persona.setFirma(consultaReniecResponse.getResFirma());
		} catch (Throwable ex) {
		}
		;
		return persona;
	}

}
