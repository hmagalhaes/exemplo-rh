package br.eti.hmagalhaes.rh.model.repository;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 28-Feb-2014
 * @param <T> Tipo da entidade trabalhada.
 * @param <Y> Tipo do ID da entidade trabalhada.
 */
public interface CRUDRepository<T, Y extends Serializable> {
	T inserir(T entidade);
	T atualizar(T entidade);
	void remover(Y id);
	Collection<T> buscar();
	T buscar(Y id);
}
