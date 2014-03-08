package br.eti.hmagalhaes.rh.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Define a entidade colaborador (de uma empresa).
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 27-Feb-2014
 */
@Entity
@Table(name="colaborador")
public class Colaborador implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	private String email;
	private Departamento departamento;
	private Date dataAdmissao;

	public Colaborador() { }
	
	public Colaborador(Long id) {
		this.id = id;
	}
	
	@Id
	@GeneratedValue(generator="colaborador_gen")
	@SequenceGenerator(name="colaborador_gen", sequenceName="colaborador_seq")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable=false, length=120)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(nullable=false, length=120)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ManyToOne
	@JoinColumn(name="departamentoID", nullable=false, updatable=true)
	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Colaborador) {
			Colaborador e = (Colaborador) o;
			return e.getId() != null && e.getId().equals(this.id);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	public Date getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

}