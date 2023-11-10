package br.com.cdgoes.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "TB_LIVRO")
@NamedQuery(name = "Livro.findByNome", query = "SELECT c FROM Livro c WHERE c.titulo LIKE :titulo") 
public class Livro implements Persistente {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="livro_seq")
	@SequenceGenerator(name="livro_seq", sequenceName="sq_livro", initialValue = 1, allocationSize = 1)
	private Long id; 
	
	@Column(name = "TITULO", nullable = false, length = 255)
	private String titulo;
	
	@Column(name = "AUTOR", nullable = false, length = 255)
	private String autor;
	

	@Column(name = "EDITORA", nullable = false, length = 255)
	private String editora;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	
	
}
