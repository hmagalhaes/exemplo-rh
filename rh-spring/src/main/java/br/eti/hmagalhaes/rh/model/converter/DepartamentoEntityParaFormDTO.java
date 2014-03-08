package br.eti.hmagalhaes.rh.model.converter;

import br.eti.hmagalhaes.rh.model.dto.DepartamentoFormDTO;
import br.eti.hmagalhaes.rh.model.entity.Departamento;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 27-Feb-2014
 */
public class DepartamentoEntityParaFormDTO 
		implements Converter<Departamento, DepartamentoFormDTO> {
	@Override
	public DepartamentoFormDTO convert(Departamento depto) {
		DepartamentoFormDTO dto = new DepartamentoFormDTO();
		dto.setId(depto.getId());
		dto.setNome(depto.getNome());
		return dto;
	}
}