package br.eti.hmagalhaes.rh.controller;

import br.eti.hmagalhaes.rh.model.dto.ColaboradorFormDTO;
import br.eti.hmagalhaes.rh.model.entity.Colaborador;
import br.eti.hmagalhaes.rh.model.service.*;
import javax.validation.Valid;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 27-Feb-2014
 */
@Controller
@RequestMapping("/colaborador")
public class ColaboradorController {
	private static final String FORM_INCLUIR = "formIncluirColaborador";
	private static final String FORM_EDITAR = "formEditarColaborador";
	private final Logger log = LoggerFactory.getLogger(ColaboradorController.class);
	private ColaboradorService colaboradorService;
	private DepartamentoService departamentoService;
	private ConversionService conversionService;
	private ResourceBundleMessageSource msgBundle;
	private I18nService i18nService;
	
	@RequestMapping("")
	public String listar(Model model,
			@RequestParam(value="msg", required=false) String mensagem) {
		try {
			model.addAttribute("lista", colaboradorService.buscar());
			model.addAttribute("msg", mensagem);
			loadLocaleData(model);
			return "colaborador/listar";
		} catch (RuntimeException ex) {
			return mostrarErro(model, ex);
		}
	}
	
	@RequestMapping(value="/{id}/editar", method=RequestMethod.GET)
	public String formEditar(Model model, @PathVariable("id") Long id) {
		try {
			Colaborador colaborador = colaboradorService.buscar(id);
			if (colaborador != null) {
				model.addAttribute(FORM_EDITAR,
						conversionService.convert(colaborador, ColaboradorFormDTO.class));
				model.addAttribute("departamentos", departamentoService.buscar());
				loadLocaleData(model);
				return "colaborador/editar";
			} else {
				throw new RuntimeException(msgBundle.getMessage("colab.msg.reg_nao_encontrado", 
						new Long[]{id}, i18nService.getLocaleAtual()) );
			}
		} catch (RuntimeException ex) {
			return mostrarErro(model, ex);
		}
	}
	
	@RequestMapping(value="/{id}/editar", method=RequestMethod.POST)
	public String salvarEditar(Model model,
			@Valid @ModelAttribute(FORM_EDITAR) ColaboradorFormDTO form,
			BindingResult result) {
		try {
			if (!result.hasErrors()) {
				colaboradorService.atualizar(form);
				model.addAttribute("msg", msgBundle.getMessage("colab.msg.atualizado",
						new String[]{form.getNome()}, i18nService.getLocaleAtual()));
				return "redirect:/colaborador/";
			} else {
				model.addAttribute(FORM_EDITAR, form);
				model.addAttribute("departamentos", departamentoService.buscar());
				loadLocaleData(model);
				return "colaborador/editar";
			}
		} catch (RuntimeException ex) {
			return mostrarErro(model, ex);
		}
	}
	
	@RequestMapping(value="/incluir", method=RequestMethod.GET)
	public String formIncluir(Model model) {
		try {
			model.addAttribute(FORM_INCLUIR, new ColaboradorFormDTO());
			model.addAttribute("departamentos", departamentoService.buscar());
			loadLocaleData(model);
			return "colaborador/incluir";
		} catch (RuntimeException ex) {
			return mostrarErro(model, ex);
		}
	}
	
	@RequestMapping(value="/incluir", method=RequestMethod.POST)
	public String salvarIncluir(Model model,
			@Valid @ModelAttribute(FORM_INCLUIR) ColaboradorFormDTO form,
			BindingResult result) {
		try {
			if (!result.hasErrors()) {
				colaboradorService.inserir(form);
				model.addAttribute("msg", msgBundle.getMessage("colab.msg.inserido",
						new String[]{form.getNome()}, i18nService.getLocaleAtual()));
				return "redirect:/colaborador/";
			} else {
				model.addAttribute(FORM_INCLUIR, form);
				model.addAttribute("departamentos", departamentoService.buscar());
				loadLocaleData(model);
				return "colaborador/incluir";
			}
		} catch (RuntimeException ex) {
			return mostrarErro(model, ex);
		}
	}
	
	@RequestMapping(value="/{id}/remover")
	public String remover(Model model, @PathVariable("id") Long id) {
		try {
			colaboradorService.remover(id);
			model.addAttribute("msg", msgBundle.getMessage("colab.msg.removido",
					new Long[]{id}, i18nService.getLocaleAtual()));
			return "redirect:/colaborador/";
		} catch (RuntimeException ex) {
			return mostrarErro(model, ex);
		}
	}
	
	private String mostrarErro(Model model, Throwable ex) {
		log.error(ex.getMessage(), ex);
		model.addAttribute("error", ex.toString() + " " + 
				(ex.getMessage() != null ? ex.getMessage() : "") );
		loadLocaleData(model);
		return "error";
	}
	
	/**
	 * Carrega no modelo indicado os dados de locale necessários às views
	 * da aplicação.
	 * @param model 
	 */
	private void loadLocaleData(Model model) {
		model.addAttribute("localeAtual", i18nService.getLocaleAtual());
		model.addAttribute("locales", i18nService.getLocalesSuportados());
	}

	@Autowired
	public void setColaboradorService(ColaboradorService colaboradorService) {
		this.colaboradorService = colaboradorService;
	}

	@Autowired
	public void setConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	@Autowired
	public void setDepartamentoService(DepartamentoService departamentoService) {
		this.departamentoService = departamentoService;
	}

	@Autowired
	public void setMsgBundle(ResourceBundleMessageSource msgBundle) {
		this.msgBundle = msgBundle;
	}

	@Autowired
	public void setI18nService(I18nService i18nService) {
		this.i18nService = i18nService;
	}
}