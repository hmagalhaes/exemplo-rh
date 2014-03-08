package br.eti.hmagalhaes.rh.controller;

import br.eti.hmagalhaes.rh.model.service.I18nService;
import java.util.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Responsável pela troca de locale do usuário.
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 06-Mar-2014
 */
@Controller
@RequestMapping("/locale")
public class LocaleController {
	private final Logger log = LoggerFactory.getLogger(LocaleController.class);
	private ResourceBundleMessageSource msg;
	private I18nService viewService;
	
	@RequestMapping("/trocar")
	@ResponseBody
	public String trocar(HttpServletRequest req, HttpServletResponse resp,
			@RequestParam Map<String, String> param) {
		Boolean ok = false;
		String error = "";
		try {
			Locale l = StringUtils.parseLocaleString(param.get("locale"));
			if (!viewService.getLocalesSuportados().contains(l)) {
				throw new IllegalArgumentException();
			} else {
				log.info("Mudando session locale: " + l);
				LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(req);
				localeResolver.setLocale(req, resp, l);
				ok = true;
			}
		} catch (IllegalArgumentException ex) {
			error = msg.getMessage("locale.msg.invalido", null, LocaleContextHolder.getLocale());
		} catch (RuntimeException ex) {
			error = msg.getMessage("locale.msg.error", null, LocaleContextHolder.getLocale());
		}
		return "({ok:" + ok.toString() + ",msg:'" + error.replaceAll("'", "\'") + "'})";
	}

	@Autowired
	public void setMsg(ResourceBundleMessageSource msg) {
		this.msg = msg;
	}	

	@Autowired
	public void setViewService(I18nService viewService) {
		this.viewService = viewService;
	}
}