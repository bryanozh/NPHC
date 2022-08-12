package com.NPHC.exception;

@SuppressWarnings("serial")
public class SalaryLessThanZeroException extends Exception {

	public SalaryLessThanZeroException(String message) {
		super(message);
	}

}
