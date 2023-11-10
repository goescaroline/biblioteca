package br.com.cdgoes.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.cdgoes.domain.Leitor;
import br.com.cdgoes.service.ILeitorService;
import utils.ReplaceUtils;

@Named
@ViewScoped
public class LeitorController implements Serializable {

	
	private static final long serialVersionUID = -2410908805324560763L;

	private Leitor leitor;
	
	private Collection<Leitor> leitores;
	
	@Inject
	private ILeitorService leitorService;
	
	private Boolean isUpdate;
	
	private String cpfMask;
	
	private String telMask;

	public Leitor getLeitor() {
		return leitor;
	}

	public void setLeitor(Leitor leitor) {
		this.leitor = leitor;
	}

	public Collection<Leitor> getLeitores() {
		return leitores;
	}

	public void setLeitoress(Collection<Leitor> leitores) {
		this.leitores = leitores;
	}

	public ILeitorService getLeitorService() {
		return leitorService;
	}

	public void setLeitoreService(ILeitorService leitorService) {
		this.leitorService = leitorService;
	}

	public Boolean getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(Boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	public String getCpfMask() {
		return cpfMask;
	}

	public void setCpfMask(String cpfMask) {
		this.cpfMask = cpfMask;
	}

	public String getTelMask() {
		return telMask;
	}

	public void setTelMask(String telMask) {
		this.telMask = telMask;
	}

	@PostConstruct
    public void init() {
		try {
			this.isUpdate = false;
			this.leitor = new Leitor();
			this.leitores = leitorService.buscarTodos();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar listar os leitores"));
		}
	}
	
	public void cancel() {
		try {
			this.isUpdate = false;
			this.leitor = new Leitor();
			limparCampos();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar cancelar ação"));
		}
		
    } 
	
	public void edit(Leitor leitor) {
		try {
			this.isUpdate = true;
			this.leitor = leitor;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar excluir o leitor"));
		}
		
    } 
	
	public void delete(Leitor leitor) {
		try {
			leitorService.excluir(leitor);
			leitores.remove(leitor);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar excluir o leitor"));
		}
		
    } 
	
	public void add() {
		try {
			removerCaracteresInvalidos();
			limparCampos();
			leitorService.cadastrar(leitor);
			this.leitores = leitorService.buscarTodos();
			this.leitor = new Leitor();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar criar novo leitor"));
		}
		
        
    }
	
	  private void removerCaracteresInvalidos() {
	    	Long cpf = Long.valueOf(ReplaceUtils.replace(getCpfMask(), ".", "-"));
	    	this.leitor.setCpf(cpf);
	    	
	    	Long tel = Long.valueOf(ReplaceUtils.replace(getTelMask(), "(", ")", " ", "-"));
	    	this.leitor.setTel(tel);
		}
	    
	    private void limparCampos() {
	    	setCpfMask(null);
	    	setTelMask(null);
	    }
	
		public void update() {
	    	try {
	    		removerCaracteresInvalidos();
				leitorService.alterar(this.leitor);
				cancel();
				limparCampos();
				FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Leitor Atualiado com sucesso"));
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar atualizar o leitor"));
			}
	        
	    }
		public String voltarTelaInicial() {
			return "/index.xhtml"; 
		}
}

