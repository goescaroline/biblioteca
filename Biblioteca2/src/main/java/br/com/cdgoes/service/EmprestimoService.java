package br.com.cdgoes.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.cdgoes.dao.IEmprestimoDAO;
import br.com.cdgoes.dao.generic.IGenericDAO;
import br.com.cdgoes.domain.Emprestimo;
import br.com.cdgoes.domain.Emprestimo.Status;
import br.com.cdgoes.exceptions.DAOException;
import br.com.cdgoes.exceptions.TipoChaveNaoEncontradaException;
import br.com.cdgoes.service.generic.GenericService;


@Stateless
public class EmprestimoService extends GenericService<Emprestimo, Long> implements IEmprestimoService {
	
	
	IEmprestimoDAO dao;
	
	@Inject
	public EmprestimoService(IEmprestimoDAO dao) {
		super(dao);
		this.dao = dao;
	}


	
	@Override
	public void finalizarEmprestimo(Emprestimo emprestimo) throws TipoChaveNaoEncontradaException, DAOException {
		emprestimo.setStatus(Status.CONCLUIDA);
		dao.finalizarEmprestimo(emprestimo);
	}

	@Override
	public void cancelarEmprestimo(Emprestimo emprestimo) throws TipoChaveNaoEncontradaException, DAOException {
		emprestimo.setStatus(Status.CANCELADA);
		dao.cancelarEmprestimo(emprestimo);
	}

	@Override
	public Emprestimo consultarComCollection(Long id) {
		return dao.consultarComCollection(id);
	}

	@Override
	public Emprestimo cadastrar(Emprestimo entity) throws TipoChaveNaoEncontradaException, DAOException {
		entity.setStatus(Status.INICIADA);
		return super.cadastrar(entity);
	}
}
