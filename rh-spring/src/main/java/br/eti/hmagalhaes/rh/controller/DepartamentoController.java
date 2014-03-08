package br.eti.hmagalhaes.rh.controller;

import br.eti.hmagalhaes.rh.model.dto.*;
import br.eti.hmagalhaes.rh.model.entity.Departamento;
import br.eti.hmagalhaes.rh.model.service.*;
import br.eti.hmagalhaes.rh.model.service.exception.ColaboradoresVinculadosException;
import br.eti.hmagalhaes.rh.model.validator.DepartamentoFormRemoverDTOValidator;
import java.util.*;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


/**
 *
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 27-Feb-2014
 */
@Controller
@RequestMapping(value="/departamento")
public class DepartamentoController {
	private static final String FORM_INCLUIR = "formIncluirDepartamento";
	private static final String FORM_EDITAR = "formEditarDepartamento";
	private final Logger log = LoggerFactory.getLogger(DepartamentoController.class);
	private DepartamentoService departamentoService;
	private ColaboradorService colaboradorService;
	private ConversionService conversionService;
	private ResourceBundleMessageSource msg;
	private I18nService i18nService;
	
	
	@InitBinder(value="formRemover")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new DepartamentoFormRemoverDTOValidator());
	}
	
	@RequestMapping(value={"","/lista"})
	public String listar(Model model,
			@RequestParam(value="msg", required=false) String msg) {
		try {
			model.addAttribute("lista", departamentoService.buscar());
			model.addAttribute("msg", msg);
			loadLocaleData(model);
			return "departamento/listar";
		} catch (RuntimeException ex) {
			return mostrarErro(ex, model);
		}
	}
	
	@RequestMapping("/vazios")
	public String vazios(Model model,
			@RequestParam(value="msg", required=false) String msg) {
		try {
			model.addAttribute("lista", 
					departamentoService.departamentosVazios());
			model.addAttribute("msg", msg);
			loadLocaleData(model);
			return "departamento/vazios";
		} catch (RuntimeException ex) {
			return mostrarErro(ex, model);
		}
	}
	
	@RequestMapping(value="/{id}/editar", method=RequestMethod.GET)
	public String formEditar(Model model, @PathVariable(value="id") Long id) {
		try {
			Departamento depto = departamentoService.buscar(id);
			if (depto != null) {
				model.addAttribute(FORM_EDITAR, 
						conversionService.convert(depto, DepartamentoFormDTO.class));
				loadLocaleData(model);
				return "departamento/editar";
			} else {
				throw new RuntimeException(msg.getMessage("depto.msg.reg_nao_encontrado",
							new Long[]{id}, i18nService.getLocaleAtual()));
			}
		} catch (RuntimeException ex) {
			return mostrarErro(ex, model);
		}
	}
	
	@RequestMapping(value="/{id}/editar", method=RequestMethod.POST)
	public String salvarEditar(Model model, 
			@Valid @ModelAttribute(FORM_EDITAR) DepartamentoFormDTO form,
			BindingResult result) {
		try {
			if (!result.hasErrors()) {
				departamentoService.atualizar(form);
				listar(model, msg.getMessage("depto.msg.edicao",
						new String[]{form.getNome()}, LocaleContextHolder.getLocale()));
				return "redirect:/departamento/";
			} else {
				model.addAttribute(FORM_EDITAR, form);
				loadLocaleData(model);
				return "departamento/editar";
			}
		} catch (RuntimeException ex) {
			return mostrarErro(ex, model);
		}
	}

	@RequestMapping(value="/incluir", method=RequestMethod.GET)
	public String formIncluir(Model model) {
		model.addAttribute(FORM_INCLUIR, new DepartamentoFormDTO());
		loadLocaleData(model);
		return "departamento/incluir";
	}
	
	@RequestMapping(value="/incluir", method=RequestMethod.POST)
	public String salvarIncluir(Model model,
			@Valid @ModelAttribute(FORM_INCLUIR) DepartamentoFormDTO form,
			BindingResult result) {
		try {
			if (!result.hasErrors()) {
				departamentoService.inserir(form);
				model.addAttribute("msg", msg.getMessage("depto.msg.insercao", 
						new Object[]{form.getNome()}, LocaleContextHolder.getLocale()) );
				return "redirect:/departamento/";
			} else {
				model.addAttribute(FORM_INCLUIR, form);
				loadLocaleData(model);
				return "departamento/incluir";
			}
		} catch (RuntimeException ex) {
			return mostrarErro(ex, model);
		}
	}
	
	@RequestMapping(value="/{id}/remover", method=RequestMethod.GET)
	public String remover(Model model, @PathVariable("id") Long id) {
		try {
			try {
				departamentoService.remover(id);
				model.addAttribute("msg", msg.getMessage("depto.msg.remocao",
						new Long[]{id}, LocaleContextHolder.getLocale()) );
				return "redirect:/departamento/";
			} catch (ColaboradoresVinculadosException ex) {
				Departamento depto = departamentoService.buscar(id);
				if (depto != null) {
					DepartamentoFormRemoverDTO form = new DepartamentoFormRemoverDTO();
					form.setId(depto.getId());
					form.setNome(depto.getNome());
					
					Collection<Departamento> deptos = departamentoService.buscar();
					deptos.remove(depto);

					model.addAttribute("formRemoverDepartamento", form);
					model.addAttribute("departamentos", deptos);
					model.addAttribute("colaboradores", colaboradorService.buscarPorDepartamento(id));
					loadLocaleData(model);
					return "departamento/remover";
				} else {
					throw new RuntimeException(msg.getMessage("depto.msg.reg_nao_encontrado",
							new Long[]{id}, i18nService.getLocaleAtual()));
				}
			}
		} catch (RuntimeException ex) {
			return mostrarErro(ex, model);
		}
	}
	
	@RequestMapping(value="/{id}/remover", method=RequestMethod.POST)
	public String removerFinal(Model model,
			@Valid @ModelAttribute("formRemover") DepartamentoFormRemoverDTO form,
			BindingResult result) {
		try {
			if (!result.hasErrors()) {
				if (!form.isTransferirColaboradores()) {
					departamentoService.removerDepartamentoEColaboradores(form.getId());
				} else {
					if (form.getNovoDepartamento() != null) {
						departamentoService.removerDepartamentoTransfColaboradores(
								form.getId(), form.getNovoDepartamento().getId());
					} else {
						assert false : "Para transferência, deve definir o depto. destino. O validator deve trabalhar isso.";
					}
				}
				model.addAttribute("msg", msg.getMessage("depto.msg.remocao",
						new Long[]{form.getId()}, LocaleContextHolder.getLocale()) );
				return "redirect:/departamento/";
			} else {
				Collection<Departamento> deptos = departamentoService.buscar();
				deptos.remove(new Departamento(form.getId()));

				model.addAttribute("formRemover", form);
				model.addAttribute("departamentos", deptos);
				model.addAttribute("colaboradores", colaboradorService.
						buscarPorDepartamento(form.getId()));
				loadLocaleData(model);
				return "departamento/remover";
			}
		} catch (RuntimeException ex) {
			return mostrarErro(ex, model);
		}
	}
	
	private String mostrarErro(Throwable ex, Model model) {
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
	public void setDepartamentoService(DepartamentoService departamentoService) {
		this.departamentoService = departamentoService;
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
	public void setMsg(ResourceBundleMessageSource msg) {
		this.msg = msg;
	}

	@Autowired
	public void setI18nService(I18nService i18nService) {
		this.i18nService = i18nService;
	}
}