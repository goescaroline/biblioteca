package br.com.cdgoes.dao;

import java.util.List;

import br.com.cdgoes.dao.generic.IGenericDAO;
import br.com.cdgoes.domain.Leitor;

public interface ILeitorDAO extends IGenericDAO<Leitor, Long>{
	List<Leitor> filtrarLeitor(String query);
}
