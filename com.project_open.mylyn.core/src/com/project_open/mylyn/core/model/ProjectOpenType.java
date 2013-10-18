package com.project_open.mylyn.core.model;

public class ProjectOpenType extends Category {

	public ProjectOpenType() {
		// default constructor
	}
	
	public ProjectOpenType(int typeId, String typeName) {
		setId(typeId);
		setName(typeName);
	}

	private static final long serialVersionUID = 1L;

}
