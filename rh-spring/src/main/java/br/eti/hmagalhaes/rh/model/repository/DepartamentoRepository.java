package br.eti.hmagalhaes.rh.model.repository;

import br.eti.hmagalhaes.rh.model.entity.Departamento;
import java.util.Collection;

/**
 *
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 28-Feb-2014
 */
public interface DepartamentoRepository extends CRUDRepository<Departamento, Long> {
	int qtdDepartamentosVazios();
	int qtdColaboradoresPorDepto(long id);
	Collection<Departamento> departamentosVazios();
}