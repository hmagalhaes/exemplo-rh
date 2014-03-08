package br.eti.hmagalhaes.rh.model.repository;

import br.eti.hmagalhaes.rh.model.entity.Colaborador;
import java.util.Collection;

/**
 * Repositório de {@link Colaborador colaboradores}.
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 28-Feb-2014
 */
public interface ColaboradoresRepository extends CRUDRepository<Colaborador, Long> {
	/**
	 * Lista de colaboradores em um departamento.
	 * @param id ID do departamento.
	 * @return 
	 */
	Collection<Colaborador> buscarPorDepartamento(long id);
	
	/**
	 * Transfere os colaboradores de um departamento para outro.
	 * @param origemID Depto. origem
	 * @param destinoID  Depto. destino
	 */
	void transferirDepartamento(long origemID, long destinoID);
	
	/**
	 * Remove os colaboradores de um departamento.
	 * @param departamentoID
	 */
	void removerPorDepartamento(long departamentoID);
}