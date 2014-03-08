package br.eti.hmagalhaes.rh.model.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 *
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 08-Mar-2014
 */
public class StringParaDateConverter implements Converter<String, Date> {
	private final Logger log = LoggerFactory.getLogger(StringParaDateConverter.class);

	@Override
	public Date convert(String date) {
		if (StringUtils.hasText(date)) {
			try {
				DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
						LocaleContextHolder.getLocale());
				df.setLenient(false);
				return df.parse(date);
			} catch (ParseException ex) {
				log.error("Conversão falhou com a string \"" + date
						+ "\" no locale \"" + LocaleContextHolder.getLocale() + "\". "
						+ ex.toString());
				throw new ConversionFailedException(null, null, date, ex);
			}
		} else {
			return null;
		}
	}
}