package br.eti.hmagalhaes.rh.model.converter;

import br.eti.hmagalhaes.rh.model.entity.Departamento;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 28-Feb-2014
 */
public class DepartamentoStringParaEntity
		implements Converter<String, Departamento> {
	@Override
	public Departamento convert(String s) {
		Departamento d = new Departamento();
		d.setId(Long.parseLong(s.split("#")[1]));
		return d;
	}
}