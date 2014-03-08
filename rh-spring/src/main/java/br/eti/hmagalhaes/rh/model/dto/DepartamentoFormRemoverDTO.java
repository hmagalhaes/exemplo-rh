package br.eti.hmagalhaes.rh.model.dto;

import br.eti.hmagalhaes.rh.model.entity.Departamento;
import java.io.Serializable;

/**
 * Define o formulário de remoção de departamento.
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 28-Feb-2014
 */
public class DepartamentoFormRemoverDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String nome;
	private Departamento novoDepartamento;
	private boolean transferirColaboradores = true;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Departamento getNovoDepartamento() {
		return novoDepartamento;
	}

	public void setNovoDepartamento(Departamento novoDepartamento) {
		this.novoDepartamento = novoDepartamento;
	}

	public boolean isTransferirColaboradores() {
		return transferirColaboradores;
	}

	public void setTransferirColaboradores(boolean transferirColaboradores) {
		this.transferirColaboradores = transferirColaboradores;
	}
}