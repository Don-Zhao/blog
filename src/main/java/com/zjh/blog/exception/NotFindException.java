package com.zjh.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFindException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotFindException() {
		super();
		System.out.println("not found exception");
	}

	public NotFindException(String msg, Throwable e) {
		super(msg, e);
		System.out.println("not found exception");
	}

	public NotFindException(String msg) {
		super(msg);
		System.out.println("not found exception");
	}

	public NotFindException(Throwable msg) {
		super(msg);
		System.out.println("not found exception");
	}
	
}
