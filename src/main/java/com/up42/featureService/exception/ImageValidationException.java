package com.up42.featureService.exception;

import com.up42.featureService.util.Constants;

public class ImageValidationException extends Exception{
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return Constants.ERROR_MESSAGE_REQUESTED_IMAGE_IS_NOT_VALID;
	}

}
