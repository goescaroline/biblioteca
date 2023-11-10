package br.com.cdgoes.dao;


import java.util.List;

import javax.persistence.TypedQuery;

import br.com.cdgoes.dao.generic.GenericDAO;
import br.com.cdgoes.domain.Livro;



public class LivroDAO extends GenericDAO<Livro, String> implements ILivroDAO{
	
	public LivroDAO() {
		super(Livro.class);
		
	}

	@Override
	public List<Livro> filtrarLivros(String query) {
		TypedQuery<Livro> tpQuery = 
				this.entityManager.createNamedQuery("Livro.findByNome", this.persistenteClass);
		tpQuery.setParameter("titulo", "%" + query + "%");
        return tpQuery.getResultList();
	}
}
