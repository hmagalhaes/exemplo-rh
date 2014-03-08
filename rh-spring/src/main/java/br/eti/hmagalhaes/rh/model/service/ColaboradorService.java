package br.eti.hmagalhaes.rh.model.service;

import br.eti.hmagalhaes.rh.model.dto.ColaboradorFormDTO;
import br.eti.hmagalhaes.rh.model.entity.Colaborador;
import java.util.Collection;

/**
 * Serviço de manipulação de {@link Colaborador colaboradores}.
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 27-Feb-2014
 */
public interface ColaboradorService extends CRUDService<Colaborador, Long> {
	Colaborador atualizar(ColaboradorFormDTO form);
	Colaborador inserir(ColaboradorFormDTO form);
	Collection<Colaborador> buscarPorDepartamento(long id);
}
