package br.com.cdgoes.dao;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.cdgoes.dao.generic.GenericDAO;
import br.com.cdgoes.domain.Emprestimo;
import br.com.cdgoes.domain.Leitor;
import br.com.cdgoes.domain.Livro;
import br.com.cdgoes.exceptions.DAOException;
import br.com.cdgoes.exceptions.TipoChaveNaoEncontradaException;

public class EmprestimoDAO extends GenericDAO<Emprestimo, Long> implements IEmprestimoDAO {

	public EmprestimoDAO() {
		super(Emprestimo.class);
	}
	
	@Override
	public void finalizarEmprestimo(Emprestimo emprestimo) throws TipoChaveNaoEncontradaException, DAOException {
		super.alterar(emprestimo);
	}

	@Override
	public void cancelarEmprestimo(Emprestimo emprestimo) throws TipoChaveNaoEncontradaException, DAOException {
		super.alterar(emprestimo);
	}

	@Override
	public void excluir(Emprestimo entity) throws DAOException {
		throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
	}

	
	@Override
	public Emprestimo cadastrar(Emprestimo entity) throws TipoChaveNaoEncontradaException, DAOException {
		try {
			entity.getLivros().forEach(prod -> {
				Livro prodJpa = entityManager.merge(prod.getLivro());
				prod.setLivro(prodJpa);
			});
			Leitor leitor = entityManager.merge(entity.getLeitor());
			entity.setLeitor(leitor);
			entityManager.persist(entity);
//			entityManager.getTransaction().commit();
			return entity;
		} catch (Exception e) {
			throw new DAOException("ERRO SALVANDO EMPRÉSTIMO ", e);
		}
		
	}
	
	@Override
	public Emprestimo consultarComCollection(Long id) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Emprestimo> query = builder.createQuery(Emprestimo.class);
		Root<Emprestimo> root = query.from(Emprestimo.class);
		root.fetch("leitor");
		root.fetch("livros");
		query.select(root).where(builder.equal(root.get("id"), id));
		TypedQuery<Emprestimo> tpQuery = 
				entityManager.createQuery(query);
		Emprestimo emprestimo = tpQuery.getSingleResult(); 
		return emprestimo;
	}

}
