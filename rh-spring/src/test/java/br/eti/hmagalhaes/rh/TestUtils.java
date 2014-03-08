package br.eti.hmagalhaes.rh;

import br.eti.hmagalhaes.rh.model.entity.*;
import java.util.*;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 *
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 05-Mar-2014
 */
public class TestUtils {
	/**
	 * Cria o ResourceBundleMessageSource e configura o mock gerado pelos seguintes
	 * métodos: {@link TestUtils#createMessageSourceNiceMock(String)},
	 * {@link TestUtils#createMessageSourceMock(String)} e
	 * {@link TestUtils#createMessageSourceStrictMock(String)}.
	 * @param msgPadrao
	 * @param msgMock
	 * @return 
	 */
	private static ResourceBundleMessageSource createResourceBundle(
			String msgPadrao, MessageSource msgMock) {
		msgMock.getMessage(anyObject(MessageSourceResolvable.class), anyObject(Locale.class));
		expectLastCall().andReturn(msgPadrao);
		msgMock.getMessage(anyObject(String.class), anyObject(Object[].class),
				anyObject(Locale.class));
		expectLastCall().andReturn(msgPadrao);
		msgMock.getMessage(anyObject(String.class), anyObject(Object[].class),
				anyObject(String.class), anyObject(Locale.class));
		expectLastCall().andReturn(msgPadrao);
		replay(msgMock);
		
		//
		ResourceBundleMessageSource res = new ResourceBundleMessageSource();
		res.setParentMessageSource(msgMock);
		return res;
	}
	
	/**
	 * Cria um {@link EasyMock#createNiceMock(Class) nice mock} de um {@link MessageSource}
	 * e o configura como {@code parentMessageSource} de um 
	 * {@link ResourceBundleMessageSource} que poderá ser utilizado em casos
	 * de teste.<br/>
	 * O mock retorna para qualquer message code, a string indicada por parâmetro,
	 * portanto o ResourceBundleMessageSource poderá ser utilizado facilmente
	 * em casos de teste.
	 * @param msgPadrao Mensagem padrão a ser retornada pelo MessageSource.
	 * @return Novo ResourceBundleMessageSource.
	 */
	public static ResourceBundleMessageSource createMessageSourceNiceMock(String msgPadrao) {
		return createResourceBundle(msgPadrao, EasyMock.createNiceMock(MessageSource.class));
	}

	/**
	 * Cria um {@link EasyMock#createMock(Class) mock} de um {@link MessageSource}
	 * e o configura como {@code parentMessageSource} de um 
	 * {@link ResourceBundleMessageSource} que poderá ser utilizado em casos
	 * de teste.<br/>
	 * O mock retorna para qualquer message code, a string indicada por parâmetro,
	 * portanto o ResourceBundleMessageSource poderá ser utilizado facilmente
	 * em casos de teste.
	 * @param msgPadrao Mensagem padrão a ser retornada pelo MessageSource.
	 * @return Novo ResourceBundleMessageSource.
	 */
	public static ResourceBundleMessageSource createMessageSourceMock(String msgPadrao) {
		return createResourceBundle(msgPadrao, EasyMock.createMock(MessageSource.class));
	}
	
	/**
	 * Cria um {@link EasyMock#createStrictMock(Class) strict mock} de um {@link MessageSource}
	 * e o configura como {@code parentMessageSource} de um 
	 * {@link ResourceBundleMessageSource} que poderá ser utilizado em casos
	 * de teste.<br/>
	 * O mock retorna para qualquer message code, a string indicada por parâmetro,
	 * portanto o ResourceBundleMessageSource poderá ser utilizado facilmente
	 * em casos de teste.
	 * @param msgPadrao Mensagem padrão a ser retornada pelo MessageSource.
	 * @return Novo ResourceBundleMessageSource.
	 */	
	public static ResourceBundleMessageSource createMessageSourceStrictMock(String msgPadrao) {
		return createResourceBundle(msgPadrao, EasyMock.createStrictMock(MessageSource.class));
	}
	
	public static class Depto {
		public static Collection<Departamento> lista(int qtd) {
			Collection<Departamento> l = new ArrayList<Departamento>();
			for (int i = 1; i <= qtd; i++) {
				l.add(departamento(i));
			}
			return l;
		}

		public static Departamento departamento(int id) {
			Departamento d = new Departamento();
			d.setId(new Long(id));
			d.setNome(String.format("Departamento #%02d", id));
			return d;
		}
	}
	
	public static class Colab {
		public static Collection<Colaborador> lista(int qtd) {
			Collection<Colaborador> l = new ArrayList<Colaborador>();
			for (int i = 1; i <= qtd; i++) {
				l.add(colaborador(i));
			}
			return l;
		}
		
		public static Colaborador colaborador(long id) {
			Colaborador e = new Colaborador();
			e.setId(id);
			e.setNome(String.format("Colaborador %02d", id));
			return e;
		}		
	}
}