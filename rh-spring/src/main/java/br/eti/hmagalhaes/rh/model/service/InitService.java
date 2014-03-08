package br.eti.hmagalhaes.rh.model.service;

import br.eti.hmagalhaes.rh.model.entity.*;
import java.util.Date;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço de inicialização da aplicação.
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 28-Feb-2014
 */
@Service
public class InitService implements InitializingBean {
	private ColaboradorService colaboradorService;
	private DepartamentoService departamentoService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		/**
		 * Carga inicial da base de dados para testes
		 */
		for (int i = 1; i <= 3; i++) {
			Departamento d = new Departamento();
			d.setId(new Long(i));
			d.setNome(String.format("Depto. %04d", i));
			departamentoService.inserir(d);
			
			for (int j = 1; j <= 3; j++) {
				Colaborador c = new Colaborador();
				c.setId(new Long(j));
				c.setNome(String.format("Colaborador %04d", j));
				c.setEmail(String.format("colaborador@%03d.com", j));
				c.setDataAdmissao(new Date());
				c.setDepartamento(d);
				colaboradorService.inserir(c);
			}
		}
		
		for (int i = 1; i <= 3; i++) {
			Departamento d = new Departamento();
			d.setId(new Long(i));
			d.setNome(String.format("Depto. vazio %04d", i));
			departamentoService.inserir(d);
		}
	}

	@Autowired
	public void setColaboradorService(ColaboradorService colaboradorService) {
		this.colaboradorService = colaboradorService;
	}

	@Autowired
	public void setDepartamentoService(DepartamentoService departamentoService) {
		this.departamentoService = departamentoService;
	}
}