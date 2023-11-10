package br.com.cdgoes.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.ForeignKey;

@Entity
@Table(name = "TB_LEITOR")
@NamedQuery(name = "Leitor.findByNome", query = "SELECT c FROM Leitor c WHERE c.nome LIKE :nome") 
public class Leitor implements Persistente {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="leitor_seq")
	@SequenceGenerator(name="leitor_seq", sequenceName="sq_leitor", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(name = "NOME", nullable = false, length = 50)
	private String nome;
	
	@Column(name = "CPF", nullable = false, unique=true)
	private Long cpf;
	
	@Column(name = "TEL", nullable = false, unique=true)
	private Long tel;


	public Long getTel() {
		return tel;
	}

	public void setTel(Long tel) {
		this.tel = tel;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


}

