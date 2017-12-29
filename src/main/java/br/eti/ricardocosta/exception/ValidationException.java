package br.eti.ricardocosta.exception;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	private final ErrorCode errorCode;

	public ValidationException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return this.errorCode;
	}
}
