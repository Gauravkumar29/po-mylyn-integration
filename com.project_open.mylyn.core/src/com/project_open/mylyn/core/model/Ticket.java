package com.project_open.mylyn.core.model;

import java.util.Date;

public class Ticket extends Project {

	private Status status;
	private Type type;
	private User assignee;
	private User customerContact;
	private String description;
	private Date customerDeadline;
	private Date creationDate;
	private User creationUser;
	private Date startDate;

}
