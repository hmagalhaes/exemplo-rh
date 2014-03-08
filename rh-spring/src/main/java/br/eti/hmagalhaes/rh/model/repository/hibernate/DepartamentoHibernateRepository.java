package br.eti.hmagalhaes.rh.model.repository.hibernate;

import br.eti.hmagalhaes.rh.model.entity.Departamento;
import br.eti.hmagalhaes.rh.model.repository.DepartamentoRepository;
import java.util.Collection;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 28-Feb-2014
 */
@Repository
public class DepartamentoHibernateRepository 
		extends HibernateCRUDRepository<Departamento, Long> 
		implements DepartamentoRepository {

	public DepartamentoHibernateRepository() {
		super(Departamento.class);
	}
	
	@Override
	public int qtdDepartamentosVazios() {
		return (Integer) getSession().
				createQuery("select count(*) from Departamento as d left join d.colaboradoress as c where c = null").
				uniqueResult();
	}

	@Override
	public int qtdColaboradoresPorDepto(long id) {
		Query q = getSession().createQuery("select count(*) from Departamento as d inner join d.colaboradores where d.id = :id");
		q.setParameter("id", id);
		return ((Number) q.uniqueResult()).intValue();
	}

	@Override
	public Collection<Departamento> departamentosVazios() {
		Query q = getSession().createQuery("select d from Departamento as d left join d.colaboradores as c where c = null");
		return q.list();
	}
}