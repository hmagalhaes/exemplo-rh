package br.eti.hmagalhaes.rh.model.converter;

import br.eti.hmagalhaes.rh.model.dto.ColaboradorFormDTO;
import br.eti.hmagalhaes.rh.model.entity.Colaborador;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 28-Feb-2014
 */
public class ColaboradorFormDTOParaEntity
		implements Converter<ColaboradorFormDTO, Colaborador> {
	@Override
	public Colaborador convert(ColaboradorFormDTO form) {
		Colaborador c = new Colaborador();
		c.setId(form.getId());
		c.setNome(form.getNome());
		c.setEmail(form.getEmail());
		c.setDepartamento(form.getDepartamento());
		c.setDataAdmissao(form.getDataAdmissao());
		return c;
	}
}