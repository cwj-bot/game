package com.weijian.game.poker.spot21.exception;


public class SystemException extends RuntimeException {


	private String message;

	public SystemException(String message) {
		super(message);
		this.message = message;
	}


	@Override
	public String getMessage() {
		return message;
	}
}
