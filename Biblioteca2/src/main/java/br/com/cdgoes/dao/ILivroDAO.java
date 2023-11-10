package br.com.cdgoes.dao;

import java.util.List;

import br.com.cdgoes.dao.generic.IGenericDAO;
import br.com.cdgoes.domain.Livro;

public interface ILivroDAO extends IGenericDAO<Livro, String> {
	
	List<Livro> filtrarLivros(String query);
}
