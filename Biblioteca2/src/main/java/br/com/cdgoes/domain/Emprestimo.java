package br.com.cdgoes.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TB_EMPRESTIMO")
public class Emprestimo implements Persistente {
	
	public enum Status {
		INICIADA, CONCLUIDA, CANCELADA;

		public static Status getByName(String value) {
			for (Status status : Status.values()) {
	            if (status.name().equals(value)) {
	                return status;
	            }
	        }
			return null;
		}
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="emprestimo_seq")
	@SequenceGenerator(name="emprestimo_seq", sequenceName="sq_emprestimo", initialValue = 1, allocationSize = 1)
    private Long id;
	
	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cd_emprestimo_seq")
	@SequenceGenerator(name="cd_emprestimo_seq", sequenceName="sq_emprestimo_cd", initialValue = 1, allocationSize = 1)
	private String codigo;
	
	@ManyToOne
	@JoinColumn(name = "id_leitor_fk", 
		foreignKey = @ForeignKey(name = "fk_emprestimo_leitor"), 
		referencedColumnName = "id", nullable = false
	)
	private Leitor leitor;
	
	
	@OneToMany(mappedBy = "emprestimo", cascade = CascadeType.ALL/*, fetch = FetchType.EAGER*/, orphanRemoval = true)
	private Set<LivroQuantidade> livros;
	
	@Column(name = "DATA_EMPRESTIMO", nullable = false)
	private Instant dataEmprestimo;
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS_EMPRESTIMO", nullable = false)
	private Status status;

	public Emprestimo() {
		livros = new HashSet<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Leitor getLeitor() {
		return leitor;
	}

	public void setLeitor(Leitor leitor) {
		this.leitor = leitor;
	}

	public Set<LivroQuantidade> getLivros() {
		return livros;
	}

	public void setLivros(Set<LivroQuantidade> livros) {
		this.livros = livros;
	}

	public Instant getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(Instant dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	

	public void adicionarLivro(Livro livro, Integer quantidade) {
		validarStatus();
		Optional<LivroQuantidade> op = 
				livros.stream().filter(filter -> filter.getLivro().getId().equals(livro.getId())).findAny();
		if (op.isPresent()) {
			LivroQuantidade produtpQtd = op.get();
			produtpQtd.adicionar(quantidade);
		} else {
			LivroQuantidade prod = new LivroQuantidade();
			prod.setEmprestimo(this);
			prod.setLivro(livro);
			prod.adicionar(quantidade);
			livros.add(prod);
		}
		
	}
	
	private void validarStatus() {
		if (this.status == Status.CONCLUIDA) {
			throw new UnsupportedOperationException("IMPOSSÍVEL ALTERAR VENDA FINALIZADA");
		}
	}
	
	public void removerLivro(Livro livro, Integer quantidade) {
		validarStatus();
		Optional<LivroQuantidade> op = 
				livros.stream().filter(filter -> filter.getLivro().getId().equals(livro.getId())).findAny();
		
		if (op.isPresent()) {
			LivroQuantidade produtpQtd = op.get();
			if (produtpQtd.getQuantidade()>quantidade) {
				produtpQtd.remover(quantidade);
			} else {
				livros.remove(op.get());
			}
			
		}
	}
	
	public void removerTodosLivros() {
		validarStatus();
		livros.clear();
	}
	
	public Integer getQuantidadeTotalLivros() {
		// Soma a quantidade getQuantidade() de todos os objetos ProdutoQuantidade
		int result = livros.stream()
		  .reduce(0, (partialCountResult, prod) -> partialCountResult + prod.getQuantidade(), Integer::sum);
		return result;
	}
	

}
