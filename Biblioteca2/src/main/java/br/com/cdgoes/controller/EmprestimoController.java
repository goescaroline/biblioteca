package br.com.cdgoes.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import br.com.cdgoes.domain.Emprestimo;
import br.com.cdgoes.domain.Leitor;
import br.com.cdgoes.domain.Livro;
import br.com.cdgoes.domain.LivroQuantidade;
import br.com.cdgoes.service.IEmprestimoService;
import br.com.cdgoes.service.ILeitorService;
import br.com.cdgoes.service.ILivroService;

@Named
@ViewScoped
public class EmprestimoController implements Serializable {

	
	private static final long serialVersionUID = -1790165040379875344L;

	private Emprestimo emprestimo;
	
	private Collection<Emprestimo> emprestimos;
	
	@Inject
	private IEmprestimoService emprestimoService;
	
	@Inject
	private ILeitorService leitorService;
	
	@Inject
	private ILivroService livroService;
	
	private Boolean isUpdate;
	
	private LocalDate dataEmprestimo;
	
	private Integer quantidadeLivro;

	private Set<LivroQuantidade> livros;
	
	
	private Livro livroSelecionado;

	@PostConstruct
    public void init() {
		try {
			this.isUpdate = false;
			this.emprestimo = new Emprestimo();
			this.livros = new HashSet<>();
			this.emprestimos = emprestimoService.buscarTodos();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar listar emprestimos"));
		}
	}
	
	public void cancel() {
		try {
			this.isUpdate = false;
			this.emprestimo = new Emprestimo();
			this.livros = new HashSet<>();
			this.dataEmprestimo = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar cancelar ação"));
		}
		
    } 

	public void edit(Emprestimo emprestimo) {
		try {
			this.isUpdate = true;
			this.emprestimo = this.emprestimoService.consultarComCollection(emprestimo.getId());
			this.dataEmprestimo = LocalDate.ofInstant(this.emprestimo.getDataEmprestimo(), ZoneId.systemDefault());
			this.livros = this.emprestimo.getLivros();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar editar empréstimo"));
		}
		
    } 
	

	public void delete(Emprestimo emprestimo) {
		try {
			emprestimoService.cancelarEmprestimo(emprestimo);
			cancel();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar cancelar empréstimo"));
		}
		
    } 
	
	public void finalizar(Emprestimo emprestimo) {
		try {
			emprestimoService.finalizarEmprestimo(emprestimo);
			cancel();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar finalizar empréstimo"));
		}
		
    } 
	
	public void add() {
		try {
			emprestimo.setDataEmprestimo(dataEmprestimo.atStartOfDay(ZoneId.systemDefault()).toInstant());
			emprestimoService.cadastrar(emprestimo);
			this.emprestimos = emprestimoService.buscarTodos();
			cancel();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar cadastrar empréstimo"));
		}
    }
	
	public void update() {
    	try {
    		emprestimoService.alterar(this.emprestimo);
    		this.emprestimos = emprestimoService.buscarTodos();
			cancel();
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Empréstimo atualiado com sucesso"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar atualizar empréstimo"));
		}
        
    }
	
	public void adicionarLivro() {
		Optional<LivroQuantidade> prodOp = 
				this.emprestimo.getLivros().stream().filter(prodF -> prodF.getLivro().getId().equals(this.livroSelecionado.getId())).findFirst();

		if (prodOp.isPresent()) {
			LivroQuantidade prod = prodOp.get();
			prod.adicionar(this.quantidadeLivro);
		} else {
			LivroQuantidade prod = new LivroQuantidade();
			prod.setLivro(this.livroSelecionado);
			prod.adicionar(this.quantidadeLivro);
			prod.setEmprestimo(this.emprestimo);
			this.emprestimo.getLivros().add(prod);
		}
		this.livros = this.emprestimo.getLivros();
	}
	
	
	public void removerLivro() {
		Optional<LivroQuantidade> prodOp = 
				this.emprestimo.getLivros().stream().filter(prodF -> prodF.getLivro().getId().equals(this.livroSelecionado.getId())).findFirst();

		if (prodOp.isPresent()) {
			LivroQuantidade prod = prodOp.get();
			prod.remover(this.quantidadeLivro);
			if (prod.getQuantidade() == 0 || prod.getQuantidade() < 0) {
				this.emprestimo.getLivros().remove(prod);
			}
			this.livros = this.emprestimo.getLivros();
		}
		
	}
	
	public void onRowEdit(RowEditEvent<LivroQuantidade> event) {
		LivroQuantidade prod = (LivroQuantidade) event.getObject();
		adicionarOuRemoverLivro(prod);
    }
	
	  public void onRowCancel(RowEditEvent<LivroQuantidade> event) {
	        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getId()));
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    }
	
	  public void removerLivro(LivroQuantidade livro) {
		
		this.emprestimo.getLivros().remove(livro);
		this.livros = this.emprestimo.getLivros();
	}
	

	  
	   public void adicionarOuRemoverLivro(LivroQuantidade prod) {
	    	if (prod.getQuantidade() != this.quantidadeLivro) {
	    		int quantidade =  this.quantidadeLivro - prod.getQuantidade();
	    		if (quantidade > 0) {
	    			prod.adicionar(quantidade);
	    		} else {
	    			this.livros.remove(prod);
	    		}
	    		this.livros.forEach(pro -> {
	    		});
	    	}
	    }
    
	  public List<Leitor> filtrarLeitor(String query) {
		  return this.leitorService.filtrarLeitor(query);
	  }
	
	public List<Livro> filtrarLivros(String query) {
		return this.livroService.filtrarLivros(query);
	}
    
    public String voltarTelaInicial() {
		return "/index.xhtml"; 
	}

	public Emprestimo getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(Emprestimo emprestimo) {
		this.emprestimo = emprestimo;
	}

	public Collection<Emprestimo> getEmprestimos() {
		return emprestimos;
	}

	public void setEmprestimos(Collection<Emprestimo> emprestimos) {
		this.emprestimos = emprestimos;
	}

	public Boolean getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(Boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	public LocalDate getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(LocalDate dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public Integer getQuantidadeLivro() {
		return quantidadeLivro;
	}

	public void setQuantidadeLivro(Integer quantidadeLivro) {
		this.quantidadeLivro = quantidadeLivro;
	}

	public Set<LivroQuantidade> getLivros() {
		return livros;
	}

	public void setLivros(Set<LivroQuantidade> livros) {
		this.livros = livros;
	}

	public Livro getLivroSelecionado() {
		return livroSelecionado;
	}

	public void setLivroSelecionado(Livro livroSelecionado) {
		this.livroSelecionado = livroSelecionado;
	}
	


}
