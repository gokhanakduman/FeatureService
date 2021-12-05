package com.up42.featureService.exception;

public class ImageValidationException extends Exception{
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Requested image is not valid.";
	}

}
