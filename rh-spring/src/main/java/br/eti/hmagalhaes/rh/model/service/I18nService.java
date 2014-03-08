package br.eti.hmagalhaes.rh.model.service;

import java.util.*;

/**
 * Serviços relacionados a internacionalização.
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 06-Mar-2014
 */
public interface I18nService {
	/**
	 * Locales suportados pela aplicação.
	 * @return 
	 */
	Collection<Locale> getLocalesSuportados();
	
	/**
	 * Locale atual da sessão.
	 * @return 
	 */
	Locale getLocaleAtual();
}