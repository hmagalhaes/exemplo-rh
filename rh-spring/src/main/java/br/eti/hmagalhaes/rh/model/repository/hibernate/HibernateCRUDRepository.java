package br.eti.hmagalhaes.rh.model.repository.hibernate;

import br.eti.hmagalhaes.rh.model.repository.CRUDRepository;
import java.io.Serializable;
import java.util.Collection;
import org.hibernate.Query;

/**
 *
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 28-Feb-2014
 * @param <T> Tipo da entidade trabalhada.
 * @param <Y> Tipo do ID da entidade trabalhada.
 */
public abstract class HibernateCRUDRepository<T, Y extends Serializable>
		extends HibernateRepository
		implements CRUDRepository<T, Y> {

	private final Class<T> entityType;
	
	public HibernateCRUDRepository(Class<T> entityType) {
		this.entityType = entityType;
	}
	
	@Override
	public T inserir(T entidade) {
		getSession().save(entidade);
		return entidade;
	}

	@Override
	public T atualizar(T entidade) {
		getSession().merge(entidade);
		return entidade;
	}

	@Override
	public void remover(Y id) {
		Query q = getSession().createQuery("delete from " + entityType.getSimpleName() +
				" where id = :id");
		q.setParameter("id", id);
		q.executeUpdate();
	}

	@Override
	public Collection<T> buscar() {
		return getSession().createCriteria(entityType).list();
	}

	@Override
	public T buscar(Y id) {
		return (T) getSession().get(entityType, id);
	}
}