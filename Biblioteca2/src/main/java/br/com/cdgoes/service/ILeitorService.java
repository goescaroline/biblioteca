package br.com.cdgoes.service;

import java.util.List;

import br.com.cdgoes.domain.Leitor;
import br.com.cdgoes.exceptions.DAOException;
import br.com.cdgoes.service.generic.IGenericService;

public interface ILeitorService extends IGenericService<Leitor, Long>{
	
	Leitor buscarPorCpf(Long cpf) throws DAOException;
	
	List<Leitor> filtrarLeitor(String query);

}
