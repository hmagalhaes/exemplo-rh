package br.eti.hmagalhaes.rh.model.service.exception;

/**
 * Define uma situação onde um departamento tem colaboradores vinculados.
 * @author Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
 * @since 28-Feb-2014
 */
public class ColaboradoresVinculadosException extends RuntimeException {
	public ColaboradoresVinculadosException() { }
	
	public ColaboradoresVinculadosException(String msg) {
		super(msg);
	}
}