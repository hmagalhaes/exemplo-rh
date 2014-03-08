package br.eti.hmagalhaes.rh.controller;

import br.eti.hmagalhaes.rh.TestUtils;
import br.eti.hmagalhaes.rh.model.entity.Departamento;
import br.eti.hmagalhaes.rh.model.dto.*;
import br.eti.hmagalhaes.rh.model.service.*;
import br.eti.hmagalhaes.rh.model.service.exception.ColaboradoresVinculadosException;
import java.util.*;
import org.easymock.EasyMock;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.easymock.EasyMock.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

/**
 *
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 27-Feb-2014
 */
public class DepartamentoControllerTest {
	private final DepartamentoController controller = new DepartamentoController();
	private DepartamentoService deptoServiceMock;
	private ColaboradorService colaboradorServiceMock;
	private BindingResult bindingResultMock;
	private ConversionService conversionServiceMock;
	private Model model;
	
	
	public DepartamentoControllerTest() {
		// Mock de mensagens.
		// O conteúdo final das mensagens não fará diferença nos testes, portanto,
		// o mock será único e retornará "ok" para qualquer mensagem.
		final ResourceBundleMessageSource msg = new ResourceBundleMessageSource();
		msg.setParentMessageSource(TestUtils.createMessageSourceNiceMock("ok"));
		controller.setMsg(msg);
		
		// I18nService
		// Por enquanto este serviço não é tão importante, portanto iremos 
		// usar um só mock (instance).
		final I18nService i18n = EasyMock.createMock(I18nService.class);
		expect(i18n.getLocalesSuportados()).andReturn(
				Arrays.asList(Locale.ENGLISH, new Locale("pt")) ).anyTimes();
		expect(i18n.getLocaleAtual()).andReturn(new Locale("pt")).anyTimes();
		replay(i18n);
		controller.setI18nService(i18n);
	}

	@Before
	public void setup() {
		deptoServiceMock = EasyMock.createMock(DepartamentoService.class);
		colaboradorServiceMock = EasyMock.createMock(ColaboradorService.class);
		conversionServiceMock = EasyMock.createMock(ConversionService.class);
		
		controller.setDepartamentoService(deptoServiceMock);
		controller.setColaboradorService(colaboradorServiceMock);
		controller.setConversionService(conversionServiceMock);
		
		bindingResultMock = EasyMock.createMock(BindingResult.class);
		model = new ExtendedModelMap();
	}
	
	@Test
	public void test_listar_ok() {
		expect(deptoServiceMock.buscar()).andReturn(TestUtils.Depto.lista(5));
		replay(deptoServiceMock);
		
		//
		String view = controller.listar(model, null);
		
		//
		assertEquals("departamento/listar", view);
		assertTrue(model.asMap().get("lista") instanceof Collection);
		verify_localeData();
		verify(deptoServiceMock);
	}
	
	@Test
	public void test_listar_comMensagem() {
		expect(deptoServiceMock.buscar()).andReturn(TestUtils.Depto.lista(5));
		replay(deptoServiceMock);
		
		//
		String view = controller.listar(model, "Teste");
		
		//
		assertEquals("departamento/listar", view);
		assertEquals("Teste", model.asMap().get("msg"));
		assertTrue(model.asMap().get("lista") instanceof Collection);
		verify_localeData();
		verify(deptoServiceMock);
	}
	
	@Test
	public void test_listar_deptoServiceError() {
		expect(deptoServiceMock.buscar()).andThrow(new RuntimeException());
		replay(deptoServiceMock);
		
		//
		String view = controller.listar(model, null);
		
		//
		assertEquals("error", view);
		assertNotNull(model.asMap().get("error"));
		verify_localeData();
		verify(deptoServiceMock);
	}
	
	@Test
	public void test_formEditar_ok() {
		Departamento depto = TestUtils.Depto.departamento(10);
		DepartamentoFormDTO form = new DepartamentoFormDTO();
		form.setId(10L);
		
		expect(deptoServiceMock.buscar(10L)).andReturn(depto);
		replay(deptoServiceMock);
		
		expect(conversionServiceMock.convert(depto, DepartamentoFormDTO.class)).
				andReturn(form);
		replay(conversionServiceMock);
		
		//
		String view = controller.formEditar(model, 10L);

		//
		form = (DepartamentoFormDTO) model.asMap().get("formEditarDepartamento");
		
		assertEquals("departamento/editar", view);
		assertNotNull(form);
		assertEquals(new Long(10), form.getId());
		verify_localeData();
		verify(deptoServiceMock, conversionServiceMock);
	}
	
	@Test
	public void test_formEditar_deptoServiceError() {
		expect(deptoServiceMock.buscar(10L)).andThrow(new RuntimeException());
		replay(deptoServiceMock);
		
		//
		String view = controller.formEditar(model, 10L);
				
		//
		assertEquals("error", view);
		assertNotNull(model.asMap().get("error"));
		verify_localeData();
		verify(deptoServiceMock);
	}
	
	@Test
	public void test_formEditar_regInexistente() {
		expect(deptoServiceMock.buscar(10L)).andReturn(null);
		replay(deptoServiceMock);
		
		//
		String view = controller.formEditar(model, 10L);
		
		//
		assertEquals("error", view);
		assertNotNull(model.asMap().get("error"));
		verify_localeData();
		verify(deptoServiceMock);
	}
	
	@Test
	public void test_salvarEditar_ok() {
		DepartamentoFormDTO form = new DepartamentoFormDTO();
		
		expect(deptoServiceMock.atualizar(form)).andReturn(new Departamento());
		expect(deptoServiceMock.buscar()).andReturn(TestUtils.Depto.lista(2));
		replay(deptoServiceMock);

		//
		String view = controller.salvarEditar(model, form, bindingResultMock);
		
		//
		assertEquals("redirect:/departamento/", view);
		assertNotNull(model.asMap().get("msg"));
		verify(deptoServiceMock);
	}
	
	@Test
	public void test_salvarEditar_deptoServiceError() {
		DepartamentoFormDTO form = new DepartamentoFormDTO();
		
		expect(deptoServiceMock.atualizar(form)).andThrow(new RuntimeException());
		replay(deptoServiceMock);
		
		//
		String view = controller.salvarEditar(model, form, bindingResultMock);
		
		//
		assertEquals("error", view);
		assertNotNull(model.asMap().get("error"));
		verify_localeData();
		verify(deptoServiceMock);
	}
	
	@Test
	public void test_salvarEditar_validacaoFalha() {
		DepartamentoFormDTO form = new DepartamentoFormDTO();
		form.setId(10l);
		
		expect(bindingResultMock.hasErrors()).andReturn(true);
		replay(bindingResultMock);
		
		//
		String view = controller.salvarEditar(model, form, bindingResultMock);

		//
		form = (DepartamentoFormDTO) model.asMap().get("formEditarDepartamento");
		
		assertEquals("departamento/editar", view);
		assertNotNull(form);
		assertEquals(new Long(10), form.getId());
		verify_localeData();
		verify(bindingResultMock);
	}
	
	@Test
	public void test_formIncluir_ok() {
		String view = controller.formIncluir(model);
		
		//
		assertEquals("departamento/incluir", view);
		assertTrue(model.asMap().get("formIncluirDepartamento") instanceof DepartamentoFormDTO);
		verify_localeData();
	}

	@Test
	public void test_formIncluir_serviceError() {
		// acessa somente o i18nService que está sendo ignorado
	}

	@Test
	public void test_salvarIncluir_ok() {
		DepartamentoFormDTO form = new DepartamentoFormDTO();
		
		expect(deptoServiceMock.inserir(form)).andReturn(new Departamento());
		replay(deptoServiceMock);
		
		//
		String view = controller.salvarIncluir(model, form, bindingResultMock);
		
		//
		assertEquals("redirect:/departamento/", view);
		assertNotNull(model.asMap().get("msg"));
		verify(deptoServiceMock);
	}
	
	@Test
	public void test_salvarIncluir_deptoServiceError() {
		DepartamentoFormDTO form = new DepartamentoFormDTO();
		
		expect(deptoServiceMock.inserir(form)).andThrow(new RuntimeException());
		replay(deptoServiceMock);
		
		//
		String view = controller.salvarIncluir(model, form, bindingResultMock);
		
		//
		assertEquals("error", view);
		assertNotNull(model.asMap().get("error"));
		verify_localeData();
		verify(deptoServiceMock);
	}
	
	@Test
	public void test_salvarIncluir_validacaoFalha() {
		expect(bindingResultMock.hasErrors()).andReturn(true);
		replay(bindingResultMock);
		
		//
		String view = controller.salvarIncluir(model, new DepartamentoFormDTO(), 
				bindingResultMock);
		
		//
		assertEquals("departamento/incluir", view);
		assertTrue(model.asMap().get("formIncluirDepartamento") instanceof DepartamentoFormDTO);
		verify_localeData();
		verify(bindingResultMock);
	}
	
	@Test
	public void test_remover_ok() {
		deptoServiceMock.remover(10L);
		expectLastCall();
		replay(deptoServiceMock);
		
		//
		String view = controller.remover(model, 10L);
		
		//
		assertEquals("redirect:/departamento/", view);
		assertNotNull(model.asMap().get("msg"));
		verify(deptoServiceMock);
	}
	
	@Test
	public void test_remover_deptoServiceError() {
		deptoServiceMock.remover(10L);
		expectLastCall().andThrow(new RuntimeException());
		replay(deptoServiceMock);
		
		//
		String view = controller.remover(model, 10L);
		
		//
		assertEquals("error", view);
		assertNotNull(model.asMap().get("error"));
		verify_localeData();
		verify(deptoServiceMock);
	}
	
	@Test
	public void test_remover_colaboradoresVinculados() {
		deptoServiceMock.remover(10L);
		expectLastCall().andThrow(new ColaboradoresVinculadosException());
		expect(deptoServiceMock.buscar(10L)).andReturn(TestUtils.Depto.departamento(10));
		expect(deptoServiceMock.buscar()).andReturn(TestUtils.Depto.lista(20));
		replay(deptoServiceMock);
		
		expect(colaboradorServiceMock.buscarPorDepartamento(10L)).
				andReturn(TestUtils.Colab.lista(2));
		replay(colaboradorServiceMock);
		
		//
		String view = controller.remover(model, 10L);
		
		//
		assertEquals("departamento/remover", view);
		
		DepartamentoFormRemoverDTO form = (DepartamentoFormRemoverDTO)
				model.asMap().get("formRemoverDepartamento");
		assertNotNull(form);
		assertEquals(10L, form.getId());
		
		Collection<Departamento> deptos = (Collection) model.asMap().get("departamentos");
		assertNotNull(deptos);
		
		// Depto. a remover não pode ser opção de transferência
		Departamento depto = new Departamento();
		depto.setId(10L);
		assertFalse(deptos.contains(depto));
		
		assertTrue(model.asMap().get("colaboradores") instanceof Collection);

		verify_localeData();
		verify(deptoServiceMock, colaboradorServiceMock);
	}
	
	/**
	 * DepartamentoService já foi testado, aqui basta o ColaboradoresService.
	 */
	@Test
	public void test_remover_colaboradoresVinculados_deptoServiceError() {
		deptoServiceMock.remover(10L);
		expectLastCall().andThrow(new ColaboradoresVinculadosException());
		expect(deptoServiceMock.buscar(10L)).andReturn(TestUtils.Depto.departamento(10));
		expect(deptoServiceMock.buscar()).andReturn(TestUtils.Depto.lista(20));
		replay(deptoServiceMock);
		
		expect(colaboradorServiceMock.buscarPorDepartamento(10L)).andThrow(new RuntimeException());
		replay(colaboradorServiceMock);
		
		//
		String view = controller.remover(model, 10L);
		
		//
		assertEquals("error", view);
		assertNotNull(model.asMap().get("error"));
		verify_localeData();
		verify(deptoServiceMock, colaboradorServiceMock);
	}
	
	@Test
	public void test_removerFinal_validacaoFalha() {
		DepartamentoFormRemoverDTO form = new DepartamentoFormRemoverDTO();
		form.setId(10L);
		
		expect(deptoServiceMock.buscar()).andReturn(TestUtils.Depto.lista(20));
		replay(deptoServiceMock);
		
		expect(colaboradorServiceMock.buscarPorDepartamento(10L)).
				andReturn(TestUtils.Colab.lista(2));
		replay(colaboradorServiceMock);		
		
		expect(bindingResultMock.hasErrors()).andReturn(true);
		replay(bindingResultMock);
		
		//
		String view = controller.removerFinal(model, form, bindingResultMock);
		
		//
		assertEquals("departamento/remover", view);
		
		form = (DepartamentoFormRemoverDTO) model.asMap().get("formRemover");
		assertNotNull(form);
		assertEquals(10, form.getId());
		
		Collection<Departamento> deptos = (Collection) model.asMap().get("departamentos");
		assertNotNull(deptos);
		
		// Depto. a remover não pode ser opção de transferência
		Departamento depto = new Departamento();
		depto.setId(10L);
		assertFalse(deptos.contains(depto));
		
		assertTrue(model.asMap().get("colaboradores") instanceof Collection);
		
		verify_localeData();
		verify(bindingResultMock);
	}
	
	@Test
	public void test_removerFinal_removerColaboradores() {
		DepartamentoFormRemoverDTO form = new DepartamentoFormRemoverDTO();
		form.setId(10L);
		form.setTransferirColaboradores(false);
		
		deptoServiceMock.removerDepartamentoEColaboradores(10L);
		expectLastCall();
		replay(deptoServiceMock);
		
		//
		String view = controller.removerFinal(model, form, bindingResultMock);
		
		//
		assertEquals("redirect:/departamento/", view);
		assertNotNull(model.asMap().get("msg"));		
		verify(deptoServiceMock);
	}
	
	@Test
	public void test_removerFinal_transfColaboradores() {
		DepartamentoFormRemoverDTO form = new DepartamentoFormRemoverDTO();
		form.setId(10L);
		form.setTransferirColaboradores(true);
		form.setNovoDepartamento(new Departamento());
		form.getNovoDepartamento().setId(20L);
		
		deptoServiceMock.removerDepartamentoTransfColaboradores(10L, 20L);
		expectLastCall();
		replay(deptoServiceMock);
		
		//
		String view = controller.removerFinal(model, form, bindingResultMock);
		
		//
		assertEquals("redirect:/departamento/", view);
		assertNotNull(model.asMap().get("msg"));
		verify(deptoServiceMock);
	}
	
	@Test
	public void test_removerFinal_removerColaboradores_deptoServiceError() {
		DepartamentoFormRemoverDTO form = new DepartamentoFormRemoverDTO();
		form.setId(10L);
		form.setTransferirColaboradores(false);
		
		deptoServiceMock.removerDepartamentoEColaboradores(10L);
		expectLastCall().andThrow(new RuntimeException());
		replay(deptoServiceMock);
		
		//
		String view = controller.removerFinal(model, form, bindingResultMock);
		
		//
		assertEquals("error", view);
		assertNotNull(model.asMap().get("error"));
		verify_localeData();
		verify(deptoServiceMock);
	}
	
	@Test
	public void test_removerFinal_transfColaboradores_deptoServiceError() {
		DepartamentoFormRemoverDTO form = new DepartamentoFormRemoverDTO();
		form.setId(10L);
		form.setTransferirColaboradores(true);
		form.setNovoDepartamento(new Departamento());
		form.getNovoDepartamento().setId(20L);
		
		deptoServiceMock.removerDepartamentoTransfColaboradores(10L, 20L);
		expectLastCall().andThrow(new RuntimeException());
		replay(deptoServiceMock);
		
		//
		String view = controller.removerFinal(model, form, bindingResultMock);
		
		//
		assertEquals("error", view);
		assertNotNull(model.asMap().get("error"));
		verify_localeData();
		verify(deptoServiceMock);
	}
	
	@Test
	public void test_vazios_ok() {
		expect(deptoServiceMock.departamentosVazios()).andReturn(TestUtils.Depto.lista(2));
		replay(deptoServiceMock);
		
		//
		String v = controller.vazios(model, null);
		
		//
		assertEquals("departamento/vazios", v);
		assertTrue(model.asMap().get("lista") instanceof Collection);
		verify_localeData();
		verify(deptoServiceMock);
	}
	
	@Test
	public void test_vazios_comMensagem() {
		expect(deptoServiceMock.departamentosVazios()).andReturn(TestUtils.Depto.lista(2));
		replay(deptoServiceMock);
		
		//
		String v = controller.vazios(model, "Teste");
		
		//
		assertEquals("departamento/vazios", v);
		assertEquals("Teste", model.asMap().get("msg"));
		assertTrue(model.asMap().get("lista") instanceof Collection);
		verify_localeData();
		verify(deptoServiceMock);
	}
	
	@Test
	public void test_vazios_deptoServiceError() {
		expect(deptoServiceMock.departamentosVazios()).andThrow(new RuntimeException());
		replay(deptoServiceMock);
		
		//
		String v = controller.vazios(model, null);
		
		//
		assertEquals("error", v);
		assertNotNull(model.asMap().get("error"));
		verify_localeData();
		verify(deptoServiceMock);
	}
	
	/**
	 * Verifica se estão no modelo os dados referentes ao form de locale,
	 * presente em todas views.
	 */
	private void verify_localeData() {
		assertTrue(model.asMap().get("locales") instanceof Collection);
		assertTrue(model.asMap().get("localeAtual") instanceof Locale);
	}
}