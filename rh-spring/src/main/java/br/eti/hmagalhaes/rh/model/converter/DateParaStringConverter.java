package br.eti.hmagalhaes.rh.model.converter;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Date;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 08-Mar-2014
 */
public class DateParaStringConverter implements Converter<Date, String> {
	@Override
	public String convert(Date date) {
		if (date != null) {
			DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
					LocaleContextHolder.getLocale());
			
			// Vamos forçar que meses tenham 2 dígitos e anos tenham 4 dígitos,
			// mesmo usando o formato SHORT
			df.setNumberFormat(new NumberFormat() {
					@Override
					public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
						if (number < 9) {
							toAppendTo.append("0");
						}
						return toAppendTo.append(Long.toString(number));
					}
					@Override
					public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
						throw new UnsupportedOperationException("Not supported yet.");
					}
					@Override
					public Number parse(String source, ParsePosition parsePosition) {
						throw new UnsupportedOperationException("Not supported yet.");
					}
				});
			
			return df.format(date);
		} else {
			return null;
		}
	}
}