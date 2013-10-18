package com.project_open.mylyn.core.exception;

public class ProjectOpenException extends Exception {

	private static final long serialVersionUID = 1L;

	public ProjectOpenException(String message, Exception e) {
		super(message, e);
	}

}
