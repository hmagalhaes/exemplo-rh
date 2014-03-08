package br.eti.hmagalhaes.rh.controller;

import br.eti.hmagalhaes.rh.TestUtils;
import br.eti.hmagalhaes.rh.model.dto.ColaboradorFormDTO;
import br.eti.hmagalhaes.rh.model.entity.*;
import br.eti.hmagalhaes.rh.model.service.*;
import java.util.*;
import org.easymock.EasyMock;
import org.junit.Before;
import org.springframework.validation.BindingResult;
import static org.easymock.EasyMock.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

/**
 *
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 27-Feb-2014
 */
public class ColaboradorControllerTest {
	private final ColaboradorController controller = new ColaboradorController();
	private ColaboradorService colaboradorServiceMock;
	private DepartamentoService deptoServiceMock;
	private ConversionService conversionServiceMock;
	private BindingResult bindingResultMock;
	private Model model;

	
	public ColaboradorControllerTest() {
		// Mock de mensagens.
		// O conteúdo final das mensagens não fará diferença nos testes, portanto,
		// o mock será único e retornará "ok" para qualquer mensagem.
		final ResourceBundleMessageSource msg = new ResourceBundleMessageSource();
		msg.setParentMessageSource(TestUtils.createMessageSourceNiceMock("ok"));
		controller.setMsgBundle(msg);
		
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
		colaboradorServiceMock = createMock(ColaboradorService.class);
		deptoServiceMock = createMock(DepartamentoService.class);
		conversionServiceMock = createMock(ConversionService.class);
		
		controller.setColaboradorService(colaboradorServiceMock);
		controller.setDepartamentoService(deptoServiceMock);
		controller.setConversionService(conversionServiceMock);
		
		bindingResultMock = createNiceMock(BindingResult.class);
		model = new ExtendedModelMap();
	}
	
	@Test
	public void test_listar_ok() {
		expect(colaboradorServiceMock.buscar()).andReturn(TestUtils.Colab.lista(2));
		replay(colaboradorServiceMock);
		
		//
		String view = controller.listar(model, null);
		
		//
		assertEquals("colaborador/listar", view);
		assertTrue(model.asMap().get("lista") instanceof Collection);
		verify_localeData();
		verify(colaboradorServiceMock);
	}
	
	@Test
	public void test_listar_comMensagem() {
		expect(colaboradorServiceMock.buscar()).andReturn(TestUtils.Colab.lista(2));
		replay(colaboradorServiceMock);
		
		//
		String view = controller.listar(model, "Teste");
		
		//
		assertEquals("colaborador/listar", view);
		assertEquals("Teste", model.asMap().get("msg"));
		assertTrue(model.asMap().get("lista") instanceof Collection);
		verify_localeData();
		verify(colaboradorServiceMock);
	}
	
	@Test
	public void test_listar_colabServiceError() {
		expect(colaboradorServiceMock.buscar()).andThrow(new RuntimeException());
		replay(colaboradorServiceMock);
		
		//
		String view = controller.listar(model, null);
		
		//
		assertEquals("error", view);
		assertNotNull(model.asMap().get("error"));
		verify_localeData();
		verify(colaboradorServiceMock);
	}
	
	@Test
	public void test_formEditar_ok() {
		Colaborador colab = new Colaborador();
		colab.setId(10L);
		ColaboradorFormDTO form = new ColaboradorFormDTO();
		form.setId(10l);
		
		expect(colaboradorServiceMock.buscar(10l)).andReturn(colab);
		replay(colaboradorServiceMock);
		
		expect(conversionServiceMock.convert(colab, ColaboradorFormDTO.class)).
				andReturn(form);
		replay(conversionServiceMock);
		
		expect(deptoServiceMock.buscar()).andReturn(TestUtils.Depto.lista(3));
		replay(deptoServiceMock);
		
		//		
		String view = controller.formEditar(model, 10l);
		
		//
		form = (ColaboradorFormDTO) model.asMap().get("formEditarColaborador");
		
		assertEquals("colaborador/editar", view);
		assertNotNull(form);
		assertEquals(new Long(10), form.getId());
		assertTrue(model.asMap().get("departamentos") instanceof Collection);
		
		verify_localeData();
		verify(colaboradorServiceMock, conversionServiceMock, deptoServiceMock);
	}
	
	@Test
	public void test_formEditar_colabServiceError() {
		expect(colaboradorServiceMock.buscar(10L)).andThrow(new RuntimeException());
		replay(colaboradorServiceMock);
		
		//
		String view = controller.formEditar(model, 10L);
		
		//
		assertEquals("error", view);
		assertNotNull(model.asMap().get("error"));
		verify_localeData();
		verify(colaboradorServiceMock);
	}
	
	@Test
	public void test_formEditar_deptoServiceError() {
		Colaborador colab = new Colaborador();
		colab.setId(10L);
		ColaboradorFormDTO form = new ColaboradorFormDTO();
		form.setId(10l);
		
		expect(colaboradorServiceMock.buscar(10l)).andReturn(colab);
		replay(colaboradorServiceMock);
		
		expect(conversionServiceMock.convert(colab, ColaboradorFormDTO.class)).
				andReturn(form);
		replay(conversionServiceMock);
		
		expect(deptoServiceMock.buscar()).andThrow(new RuntimeException());
		replay(deptoServiceMock);
		
		//		
		String view = controller.formEditar(model, 10l);
		
		//
		assertEquals("error", view);
		assertNotNull(model.asMap().get("error"));
		verify_localeData();
		verify(colaboradorServiceMock, conversionServiceMock, deptoServiceMock);
	}
	
	@Test
	public void test_formEditar_regInexistente() {
		expect(colaboradorServiceMock.buscar(10l)).andReturn(null);
		replay(colaboradorServiceMock);
		
		//
		String view = controller.formEditar(model, 10L);
		
		//
		assertEquals("error", view);
		assertNotNull(model.asMap().get("error"));
		verify_localeData();
		verify(colaboradorServiceMock);
	}
	
	@Test
	public void test_formIncluir_ok() {
		expect(deptoServiceMock.buscar()).andReturn(TestUtils.Depto.lista(3));
		replay(deptoServiceMock);
		
		//
		String view = controller.formIncluir(model);
		
		//
		assertEquals("colaborador/incluir", view);
		assertTrue(model.asMap().get("formIncluirColaborador") instanceof ColaboradorFormDTO);
		assertTrue(model.asMap().get("departamentos") instanceof Collection);
		verify_localeData();
		verify(deptoServiceMock);
	}
	
	@Test
	public void test_formIncluir_deptoServiceError() {
		expect(deptoServiceMock.buscar()).andThrow(new RuntimeException());
		replay(deptoServiceMock);
		
		//
		String view = controller.formIncluir(model);
		
		//
		assertEquals("error", view);
		assertNotNull(model.asMap().get("error"));
		verify_localeData();
		verify(deptoServiceMock);
	}
	
	@Test
	public void test_salvarEditar_ok() {
		ColaboradorFormDTO form = new ColaboradorFormDTO();
		
		expect(colaboradorServiceMock.atualizar(form)).andReturn(new Colaborador());
		replay(colaboradorServiceMock);
		
		//
		String view = controller.salvarEditar(model, form, bindingResultMock);
		
		//
		assertEquals("redirect:/colaborador/", view);
		assertNotNull(model.asMap().get("msg"));
		verify(colaboradorServiceMock);
	}
	
	@Test
	public void test_salvarEditar_colabServiceError() {
		ColaboradorFormDTO form = new ColaboradorFormDTO();
		
		expect(colaboradorServiceMock.atualizar(form)).andThrow(new RuntimeException());
		replay(colaboradorServiceMock);
		
		//
		String view = controller.salvarEditar(model, form, bindingResultMock);
		
		//
		assertEquals("error", view);
		assertNotNull(model.asMap().get("error"));
		verify_localeData();
		verify(colaboradorServiceMock);
	}
	
	@Test
	public void test_salvarEditar_validacaoFalha() {
		ColaboradorFormDTO form = new ColaboradorFormDTO();
		form.setId(10L);
		
		expect(bindingResultMock.hasErrors()).andReturn(true);
		replay(bindingResultMock);
		
		expect(deptoServiceMock.buscar()).andReturn(TestUtils.Depto.lista(3));
		replay(deptoServiceMock);
		
		//
		String view = controller.salvarEditar(model, form, bindingResultMock);
		
		//
		form = (ColaboradorFormDTO) model.asMap().get("formEditarColaborador");
		
		assertEquals("colaborador/editar", view);
		assertNotNull(form);
		assertEquals(new Long(10), form.getId());
		assertTrue(model.asMap().get("departamentos") instanceof Collection);
		
		verify_localeData();
		verify(bindingResultMock, deptoServiceMock);
	}
	
	@Test
	public void test_salvarIncluir_ok() {
		ColaboradorFormDTO form = new ColaboradorFormDTO();
		
		expect(colaboradorServiceMock.inserir(form)).andReturn(new Colaborador());
		replay(colaboradorServiceMock);
			
		//
		String view = controller.salvarIncluir(model, form, bindingResultMock);
		
		//
		assertEquals("redirect:/colaborador/", view);
		assertNotNull(model.asMap().get("msg"));
		
		verify(colaboradorServiceMock);
	}
	
	@Test
	public void test_salvarIncluir_colabServiceError() {
		ColaboradorFormDTO form = new ColaboradorFormDTO();
		
		expect(colaboradorServiceMock.inserir(form)).andThrow(new RuntimeException());
		replay(colaboradorServiceMock);
			
		//
		String view = controller.salvarIncluir(model, form, bindingResultMock);
		
		//
		assertEquals("error", view);
		assertNotNull(model.asMap().get("error"));
		verify_localeData();
		verify(colaboradorServiceMock);
	}
	
	@Test
	public void test_salvarIncluir_validacaoFalha() {
		expect(bindingResultMock.hasErrors()).andReturn(true);
		replay(bindingResultMock);
		
		expect(deptoServiceMock.buscar()).andReturn(TestUtils.Depto.lista(3));
		replay(deptoServiceMock);

		//
		String view = controller.salvarIncluir(model, new ColaboradorFormDTO(), 
				bindingResultMock);
		
		//
		assertEquals("colaborador/incluir", view);
		assertTrue(model.asMap().get("formIncluirColaborador") instanceof ColaboradorFormDTO);
		assertTrue(model.asMap().get("departamentos") instanceof Collection);
		verify_localeData();
		verify(bindingResultMock, deptoServiceMock);
	}
	
	@Test
	public void test_remover_ok() {
		colaboradorServiceMock.remover(10L);
		expectLastCall();
		replay(colaboradorServiceMock);
		
		//
		String view = controller.remover(model, 10L);
		
		// 
		assertEquals("redirect:/colaborador/", view);
		assertNotNull(model.asMap().get("msg"));
		verify(colaboradorServiceMock);
	}
	
	@Test
	public void test_remover_colabServiceError() {
		colaboradorServiceMock.remover(10L);
		expectLastCall().andThrow(new RuntimeException());
		replay(colaboradorServiceMock);
		
		//
		String view = controller.remover(model, 10L);
		
		// 
		assertEquals("error", view);
		assertNotNull(model.asMap().get("error"));
		verify_localeData();
		verify(colaboradorServiceMock);
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