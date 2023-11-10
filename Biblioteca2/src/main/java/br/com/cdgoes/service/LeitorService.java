package br.com.cdgoes.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.cdgoes.dao.ILeitorDAO;
import br.com.cdgoes.domain.Leitor;
import br.com.cdgoes.exceptions.*;
import br.com.cdgoes.service.generic.GenericService;

@Stateless
public class LeitorService extends GenericService<Leitor, Long> implements ILeitorService {
	
	private ILeitorDAO leitorDAO;
	
	@Inject
	public LeitorService(ILeitorDAO leitorDAO) {
		super(leitorDAO);
		this.leitorDAO = leitorDAO;
	}

	@Override
	public Leitor buscarPorCpf(Long cpf) throws DAOException {
		try {
			return this.dao.consultar(cpf);
		} catch (MaisDeUmRegistroException | TableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Leitor> filtrarLeitor(String query) {
		return leitorDAO.filtrarLeitor(query);
	}
	
	
}
