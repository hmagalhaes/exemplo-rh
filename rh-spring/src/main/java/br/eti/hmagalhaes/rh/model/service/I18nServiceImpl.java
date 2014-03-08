package br.eti.hmagalhaes.rh.model.service;

import java.util.*;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

/**
 * Serviços relacionados a internacionalização.
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 06-Mar-2014
 */
@Service("viewService")
public class I18nServiceImpl implements I18nService {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Locale> getLocalesSuportados() {
		Collection<Locale> l = new ArrayList<Locale>();
		l.add(new Locale("pt", "BR"));
		l.add(new Locale("en", "US"));
		return l;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Locale getLocaleAtual() {
		return LocaleContextHolder.getLocale();
	}
}