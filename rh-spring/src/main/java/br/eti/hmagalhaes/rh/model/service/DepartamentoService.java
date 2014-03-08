package br.eti.hmagalhaes.rh.model.service;

import br.eti.hmagalhaes.rh.model.dto.DepartamentoFormDTO;
import br.eti.hmagalhaes.rh.model.entity.Departamento;
import java.util.Collection;

/**
 * Serviço de manipulação de {@link Departamento departamentos}.
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 27-Feb-2014
 */
public interface DepartamentoService extends CRUDService<Departamento, Long> {
	/**
	 * Quantidade geral de departamentos sem colaboradores.
	 * @return 
	 */
	int qtdDepartamentosVazios();
	
	/**
	 * Listagem de departamentos sem colaboradores.
	 * @return 
	 */
	Collection<Departamento> departamentosVazios();
	
	/**
	 * Quantidade de colaboradores no departamento indicado pelo ID.
	 * @param id
	 * @return 
	 */
	int qtdColaboradoresPorDepto(long id);
	
	Departamento inserir(DepartamentoFormDTO form);
	
	Departamento atualizar(DepartamentoFormDTO form);
	
	/**
	 * Remove o departamento indicado e todos os colaboradores vinculados.
	 * @param id 
	 */
	void removerDepartamentoEColaboradores(long id);
	
	/**
	 * Remove o departamento indicado, mas antes transfere os colaboradores
	 * vinculados para outro departamento.
	 * @param idRemover ID do depto. a remover.
	 * @param idTransf ID do depto. a transferir os colaboradores
	 */
	void removerDepartamentoTransfColaboradores(long idRemover, long idTransf);
}