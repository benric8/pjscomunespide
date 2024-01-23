package pe.gob.pj.pide.dao.repository.impl;


import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.gob.pj.pide.dao.DataSourceConfig;
import pe.gob.pj.pide.dao.repository.SegAudServicioDao;
import pe.gob.pj.seguridad.client.dto.ResponseAccesoMetodoDTO;
import pe.gob.pj.seguridad.client.dto.TokenDTO;

@Component("segAudServicioDao")
public class SegAudServicioDaoImpl implements SegAudServicioDao, Serializable{

	private static final long serialVersionUID = 1L;
		
	@Autowired
	private DataSourceConfig dataSourceConfig;

	@Override
	public void setToken(TokenDTO token) {
		dataSourceConfig.setToken(token);
	}
	
	@Override
	public TokenDTO getToken() {
		return dataSourceConfig.getToken();
	}
	
	@Override
	public List<ResponseAccesoMetodoDTO> getAccesos() {
		return dataSourceConfig.getAccesos();
	}
}
