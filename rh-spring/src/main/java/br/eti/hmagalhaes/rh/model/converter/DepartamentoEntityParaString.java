package br.eti.hmagalhaes.rh.model.converter;

import br.eti.hmagalhaes.rh.model.entity.Departamento;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 28-Feb-2014
 */
public class DepartamentoEntityParaString
		implements Converter<Departamento, String> {
	@Override
	public String convert(Departamento d) {
		return "depto#" + d.getId();
	}
}