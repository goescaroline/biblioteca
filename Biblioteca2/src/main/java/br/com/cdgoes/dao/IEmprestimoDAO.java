package br.com.cdgoes.dao;

import br.com.cdgoes.dao.generic.IGenericDAO;
import br.com.cdgoes.domain.Emprestimo;
import br.com.cdgoes.exceptions.DAOException;
import br.com.cdgoes.exceptions.TipoChaveNaoEncontradaException;

public interface IEmprestimoDAO extends IGenericDAO<Emprestimo, Long>{
	
	public void finalizarEmprestimo(Emprestimo emprestimo) throws TipoChaveNaoEncontradaException, DAOException;
	
	public void cancelarEmprestimo(Emprestimo emprestimo) throws TipoChaveNaoEncontradaException, DAOException;
	
	public Emprestimo consultarComCollection(Long id);
}
