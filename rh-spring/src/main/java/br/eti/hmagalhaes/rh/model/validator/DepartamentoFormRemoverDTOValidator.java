package br.eti.hmagalhaes.rh.model.validator;

import br.eti.hmagalhaes.rh.model.dto.DepartamentoFormRemoverDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 28-Feb-2014
 */
public class DepartamentoFormRemoverDTOValidator implements Validator {
	@Override
	public boolean supports(Class<?> type) {
		return type.isAssignableFrom(DepartamentoFormRemoverDTO.class);
	}

	@Override
	public void validate(Object o, Errors errors) {
		DepartamentoFormRemoverDTO form = (DepartamentoFormRemoverDTO) o;
		if (form.isTransferirColaboradores() && form.getNovoDepartamento() == null) {
			errors.reject("depto.erro.transf_colab_sem_destino");
		}
	}
}