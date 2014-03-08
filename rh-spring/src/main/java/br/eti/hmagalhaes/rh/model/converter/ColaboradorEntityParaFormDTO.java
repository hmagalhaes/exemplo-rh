package br.eti.hmagalhaes.rh.model.converter;

import br.eti.hmagalhaes.rh.model.dto.ColaboradorFormDTO;
import br.eti.hmagalhaes.rh.model.entity.Colaborador;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 28-Feb-2014
 */
public class ColaboradorEntityParaFormDTO
		implements Converter<Colaborador, ColaboradorFormDTO> {
	@Override
	public ColaboradorFormDTO convert(Colaborador c) {
		ColaboradorFormDTO form = new ColaboradorFormDTO();
		form.setId(c.getId());
		form.setNome(c.getNome());
		form.setEmail(c.getEmail());
		form.setDepartamento(c.getDepartamento());
		form.setDataAdmissao(c.getDataAdmissao());
		return form;
	}
}