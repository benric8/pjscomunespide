package pe.gob.pj.pide.dao.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PaginationDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer totalRecords;
	private Integer pageSize;
	private Integer currentPage;
	private Object list;
 
	private Integer firstPage;
	private Integer lastPage;
}
