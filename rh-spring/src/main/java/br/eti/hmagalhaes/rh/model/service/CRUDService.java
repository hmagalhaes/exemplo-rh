package br.eti.hmagalhaes.rh.model.service;

import java.io.Serializable;
import java.util.Collection;

/**
 * Define um serviço que disponibiliza operações básicas de manutenção de entidades.
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 27-Feb-2014
 * @param <T> Tipo da entidade trabalhada.
 * @param <Y> Tipo do ID da entidade trabalhada.
 */
public interface CRUDService<T, Y extends Serializable> {
	/**
	 * Retorna todas as entidades cadastradas.
	 * @return 
	 */
	Collection<T> buscar();
	
	/**
	 * Retorna a entidade indicada pelo ID.
	 * @param id
	 * @return Entidade encontrada ou {@code null} caso seja inexistente.
	 */
	T buscar(Y id);
	
	/**
	 * Persiste a entidade especificada.
	 * @param entity
	 * @return 
	 */
	T inserir(T entity);
	
	/**
	 * Persiste a entidade especificada.
	 * @param entity
	 * @return 
	 */
	T atualizar(T entity);

	/**
	 * Remove a entidade indicada pelo ID.
	 * @param id 
	 */
	void remover(Y id);
}
