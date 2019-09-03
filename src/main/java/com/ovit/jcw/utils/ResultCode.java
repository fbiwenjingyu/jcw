package com.ovit.jcw.utils;

public enum ResultCode {
	SUCCESS(200), FAIL(400), UNAUTHORIZED(401), NOT_FOUND(404), INTERNAL_SERVER_ERROR(500);

	public int code;

	private ResultCode(int code) {
		this.code = code;
	}
}