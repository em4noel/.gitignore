package com.confapp.error;

public class SenhaInvalidaException extends RuntimeException {
	 public SenhaInvalidaException() {
	        super("Senha inválida");
	    }
}
