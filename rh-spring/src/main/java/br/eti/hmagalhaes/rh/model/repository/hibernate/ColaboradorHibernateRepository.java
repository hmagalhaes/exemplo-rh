package br.eti.hmagalhaes.rh.model.repository.hibernate;

import br.eti.hmagalhaes.rh.model.entity.Departamento;
import br.eti.hmagalhaes.rh.model.entity.Colaborador;
import br.eti.hmagalhaes.rh.model.repository.ColaboradoresRepository;
import java.util.Collection;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 28-Feb-2014
 */
@Repository
public class ColaboradorHibernateRepository
		extends HibernateCRUDRepository<Colaborador, Long>
		implements ColaboradoresRepository {
	private final Logger log = LoggerFactory.getLogger(ColaboradorHibernateRepository.class);

	public ColaboradorHibernateRepository() {
		super(Colaborador.class);
	}

	@Override
	public Collection<Colaborador> buscarPorDepartamento(long id) {
		Query q = getSession().createQuery("select c from Colaborador as c inner join c.departamento as d where d.id = :id");
		q.setParameter("id", id);
		return q.list();
	}

	@Override
	public void transferirDepartamento(long oridemID, long destinoID) {
		Query q = getSession().createQuery("update Colaborador set departamento = :destino where departamento = :origem");
		q.setParameter("origem", (Departamento) getSession().get(Departamento.class, oridemID));
		q.setParameter("destino", (Departamento) getSession().get(Departamento.class, destinoID));
		q.executeUpdate();
	}

	@Override
	public void removerPorDepartamento(long departamentoID) {
		Query q = getSession().createQuery("delete Colaborador where departamento = :depto");
		q.setParameter("depto", getSession().get(Departamento.class, departamentoID));
		q.executeUpdate();
	}
}