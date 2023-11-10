package br.com.cdgoes.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class IndexController implements Serializable {



	private static final long serialVersionUID = -6054158367156175642L;

	public String redirectLeitor() {
		return "/leitor/list.xhtml";
	}
	
	public String redirectLivro() {
		return "/livro/list.xhtml";
	}
	
	public String redirectEmprestimo() {
		return "/emprestimo/list.xhtml";
	}
}
