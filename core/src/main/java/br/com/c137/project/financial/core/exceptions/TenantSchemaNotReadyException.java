package br.com.c137.project.financial.core.exceptions;

public class TenantSchemaNotReadyException extends RuntimeException {
	public TenantSchemaNotReadyException(String message) {
        super(message);
    }
}
