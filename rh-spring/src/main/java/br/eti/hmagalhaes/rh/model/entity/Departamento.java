package br.eti.hmagalhaes.rh.model.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;

/**
 *
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 27-Feb-2014
 */
@Entity
@Table(name="departamento")
public class Departamento implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	private Collection<Colaborador> colaboradores;

	public Departamento() { }
	
	public Departamento(Long id) {
		this.id = id;
	}
	
	@Id
	@GeneratedValue(generator="depto_gen")
	@SequenceGenerator(name="depto_gen", sequenceName="depto_seq")
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

	@OneToMany(mappedBy="departamento", cascade=CascadeType.REMOVE)
	public Collection<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public void setColaboradores(Collection<Colaborador> colaboradores) {
		this.colaboradores = colaboradores;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Departamento) {
			Departamento d = (Departamento)o;
			return d.getId() != null && d.getId().equals(this.id);
		}
		return super.equals(o);
	}
	
	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}
}