package br.com.cdgoes.service;

import java.util.List;

import br.com.cdgoes.domain.Livro;
import br.com.cdgoes.service.generic.IGenericService;

public interface ILivroService extends IGenericService<Livro, String> {
	
	List<Livro> filtrarLivros(String query);

}
