package com.project_open.mylyn.core.model;

public class ProjectOpenStatus extends Category {

	public ProjectOpenStatus() {
		// default constructor
	}
	
	public ProjectOpenStatus(int statusId, String statusName) {
		setId(statusId);
		setName(statusName);
	}

	private static final long serialVersionUID = 1L;

}
