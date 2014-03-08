package br.eti.hmagalhaes.rh.model.service;

import br.eti.hmagalhaes.rh.model.dto.DepartamentoFormDTO;
import br.eti.hmagalhaes.rh.model.entity.Departamento;
import br.eti.hmagalhaes.rh.model.repository.*;
import br.eti.hmagalhaes.rh.model.service.exception.ColaboradoresVinculadosException;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço de manipulação de {@link Departamento departamentos}.
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 27-Feb-2014
 */
@Service
@Transactional
public class DepartamentoServiceImpl implements DepartamentoService {
	private DepartamentoRepository departamentoRepository;
	private ConversionService conversionService;
	private ColaboradoresRepository colaboradorRepository;
	
	@Override
	public int qtdDepartamentosVazios() {
		return departamentoRepository.qtdDepartamentosVazios();
	}

	@Override
	public Collection<Departamento> departamentosVazios() {
		return departamentoRepository.departamentosVazios();
	}

	@Override
	public int qtdColaboradoresPorDepto(long id) {
		return departamentoRepository.qtdColaboradoresPorDepto(id);
	}

	@Override
	public Departamento inserir(DepartamentoFormDTO form) {
		return departamentoRepository.inserir(conversionService.
				convert(form, Departamento.class));
	}

	@Override
	public Departamento atualizar(DepartamentoFormDTO form) {
		return departamentoRepository.atualizar(conversionService.
				convert(form, Departamento.class));
	}

	@Override
	public Collection<Departamento> buscar() {
		return departamentoRepository.buscar();
	}

	@Override
	public Departamento buscar(Long id) {
		return departamentoRepository.buscar(id);
	}

	@Override
	public Departamento inserir(Departamento entity) {
		return departamentoRepository.inserir(entity);
	}

	@Override
	public Departamento atualizar(Departamento entity) {
		return departamentoRepository.atualizar(entity);
	}

	/**
	 * 
	 * @param id
	 * @throws ColaboradoresVinculadosException quando há colaboradores vinculados
	 * ao departamento e a remoção não pode ocorrer diretamente.
	 */	
	@Override
	public void remover(Long id) throws ColaboradoresVinculadosException {
		int qtd = departamentoRepository.qtdColaboradoresPorDepto(id);
		if (qtd == 0) {
			departamentoRepository.remover(id);
		} else {
			throw new ColaboradoresVinculadosException(
					String.format("Há %d colaboradores(s) vinculado(s) ao departamento.", qtd));
		}
	}
	
	@Override
	public void removerDepartamentoEColaboradores(long id) {
		colaboradorRepository.removerPorDepartamento(id);
		departamentoRepository.remover(id);
	}

	@Override
	public void removerDepartamentoTransfColaboradores(long idRemover, long idTransf) {
		colaboradorRepository.transferirDepartamento(idRemover, idTransf);
		departamentoRepository.remover(idRemover);
	}

	@Autowired
	public void setDepartamentoRepository(DepartamentoRepository departamentoRepository) {
		this.departamentoRepository = departamentoRepository;
	}

	@Autowired
	public void setConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	@Autowired
	public void setColaboradorRepository(ColaboradoresRepository colaboradorRepository) {
		this.colaboradorRepository = colaboradorRepository;
	}
}