package br.com.cdgoes.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.cdgoes.dao.ILivroDAO;
import br.com.cdgoes.domain.Livro;
import br.com.cdgoes.service.generic.GenericService;

@Stateless
public class LivroService extends GenericService<Livro, String> implements ILivroService {

	private ILivroDAO livroDAO;

	@Inject
	public LivroService(ILivroDAO livroDAO) {
		super(livroDAO);
		this.livroDAO = livroDAO;
	}
	
	@Override
	public List<Livro> filtrarLivros(String query) {
		return livroDAO.filtrarLivros(query);
	}
}
