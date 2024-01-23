package pe.gob.pj.pide.ws;

import pe.gob.pj.pide.ws.bean.PersonaBean;
import pe.gob.pj.pide.ws.bean.PersonaReniecBean;

public interface ReniecWsService {

	public PersonaReniecBean consultaReniecxDni(PersonaBean preRegistro) throws Exception;
	
}
