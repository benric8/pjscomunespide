package pe.gob.pj.pide.dao.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestBusquedaPermisosDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("idEntidad")
	private int idEntidad;
	@JsonProperty("idOperacion")
	private int idOperacion;
	@JsonProperty("estado")
	private String estado;
	@NotNull(message = "La fecha desde no puede ser null")
	@JsonProperty("fechaDesde")
	private Timestamp fechaDesde;
	@NotNull(message = "La fecha desde no puede ser null")
	@JsonProperty("fechaHasta")
	private Timestamp fechaHasta;

}
