package br.eti.hmagalhaes.rh.model.converter;

import br.eti.hmagalhaes.rh.model.dto.DepartamentoFormDTO;
import br.eti.hmagalhaes.rh.model.entity.Departamento;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 27-Feb-2014
 */
public class DepartamentoFormDTOParaEntity 
		implements Converter<DepartamentoFormDTO, Departamento> {
	@Override
	public Departamento convert(DepartamentoFormDTO form) {
		Departamento depto = new Departamento();
		depto.setId(form.getId());
		depto.setNome(form.getNome());
		return depto;
	}
}