package br.eti.hmagalhaes.rh.model.service;

import br.eti.hmagalhaes.rh.model.dto.ColaboradorFormDTO;
import br.eti.hmagalhaes.rh.model.entity.Colaborador;
import br.eti.hmagalhaes.rh.model.repository.ColaboradoresRepository;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 28-Feb-2014
 */
@Service
public class ColaboradorServiceImpl implements ColaboradorService {
	private ColaboradoresRepository colaboradorRepository;
	private ConversionService conversionService;
	
	@Transactional
	@Override
	public Colaborador atualizar(ColaboradorFormDTO form) {
		return colaboradorRepository.atualizar(conversionService.
				convert(form, Colaborador.class));
	}

	@Transactional
	@Override
	public Colaborador inserir(ColaboradorFormDTO form) {
		return colaboradorRepository.inserir(conversionService.
				convert(form, Colaborador.class));
	}

	@Transactional
	@Override
	public Collection<Colaborador> buscar() {
		return colaboradorRepository.buscar();
	}

	@Transactional
	@Override
	public Colaborador buscar(Long id) {
		return colaboradorRepository.buscar(id);
	}

	@Transactional
	@Override
	public Collection<Colaborador> buscarPorDepartamento(long id) {
		return colaboradorRepository.buscarPorDepartamento(id);
	}	

	@Transactional
	@Override
	public Colaborador inserir(Colaborador entity) {
		return colaboradorRepository.inserir(entity);
	}

	@Transactional
	@Override
	public Colaborador atualizar(Colaborador entity) {
		return colaboradorRepository.atualizar(entity);
	}

	@Transactional
	@Override
	public void remover(Long id) {
		colaboradorRepository.remover(id);
	}

	@Autowired
	public void setColaboradorRepository(ColaboradoresRepository colaboradorRepository) {
		this.colaboradorRepository = colaboradorRepository;
	}

	@Autowired
	public void setConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}
}