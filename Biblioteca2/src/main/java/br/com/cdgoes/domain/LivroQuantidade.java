package br.com.cdgoes.domain;


import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TB_LIVRO_QUANTIDADE")
public class LivroQuantidade {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="livr_qtd_seq")
	@SequenceGenerator(name="livr_qtd_seq", sequenceName="sq_livr_qtd", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private Livro livro;
	
	@Column(name = "quantidade", nullable = false)
	private Integer quantidade;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "id_emprestimo_fk", 
		foreignKey = @ForeignKey(name = "fk_livr_qtd_emprestimo"), 
		referencedColumnName = "id", nullable = false
	)
	private Emprestimo emprestimo;
	
	public LivroQuantidade() {
		this.quantidade = 0;
	}

	public void adicionar(Integer quantidade) {
		this.quantidade += quantidade;
	}
	
	public void remover(Integer quantidade) {
		this.quantidade -= quantidade;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Livro getLivro() {
		return livro;
	}


	public void setLivro(Livro livro) {
		this.livro = livro;
	}


	public Integer getQuantidade() {
		return quantidade;
	}


	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}


	public Emprestimo getEmprestimo() {
		return emprestimo;
	}


	public void setEmprestimo(Emprestimo emprestimo) {
		this.emprestimo = emprestimo;
	}
	

	
}
