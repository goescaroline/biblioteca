package br.com.cdgoes.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import br.com.cdgoes.dao.generic.GenericDAO;
import br.com.cdgoes.domain.Leitor;
import br.com.cdgoes.exceptions.DAOException;
import br.com.cdgoes.exceptions.TipoChaveNaoEncontradaException;

public class LeitorDAO extends GenericDAO<Leitor, Long> implements ILeitorDAO{

	public LeitorDAO() {
		super(Leitor.class);
	}

	@Override
	public List<Leitor> filtrarLeitor(String query) {
		TypedQuery<Leitor> tpQuery = 
				this.entityManager.createNamedQuery("Leitor.findByNome", this.persistenteClass);
		tpQuery.setParameter("nome", "%" + query + "%");
        return tpQuery.getResultList();
	}
	

}
