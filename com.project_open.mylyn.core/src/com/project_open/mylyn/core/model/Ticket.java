package com.project_open.mylyn.core.model;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.project_open.mylyn.core.util.ProjectOpenUtil;

public class Ticket extends Project {

	private static final long serialVersionUID = 1L;

	private ProjectOpenTracker tracker;
	private ProjectOpenStatus status;
	private ProjectOpenType type;
	private User assignee;
	private User customerContact;
	private String description = "";
	private Date customerDeadline;
	private Date creationDate;
	private Date lastModifiedDate;
	private User creationUser;
	private Date startDate;
	private Date resolutionDate;


	public ProjectOpenTracker getTracker() {
		return tracker;
	}

	public void setTracker(ProjectOpenTracker tracker) {
		this.tracker = tracker;
	}

	public ProjectOpenStatus getStatus() {
		return status;
	}

	public void setStatus(ProjectOpenStatus status) {
		this.status = status;
	}

	public ProjectOpenType getType() {
		return type;
	}

	public void setType(ProjectOpenType type) {
		this.type = type;
	}

	public User getAssignee() {
		return assignee;
	}

	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}

	public User getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(User customerContact) {
		this.customerContact = customerContact;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCustomerDeadline() {
		return customerDeadline;
	}

	public void setCustomerDeadline(Date customerDeadline) {
		this.customerDeadline = customerDeadline;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public User getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(User creationUser) {
		this.creationUser = creationUser;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getResolutionDate() {
		return resolutionDate;
	}

	public void setResolutionDate(Date resolutionDate) {
		this.resolutionDate = resolutionDate;
	}

	public void marshall(JSONObject jsonObject) {
		super.marshall(jsonObject);
		try {
            int trackerId = ProjectOpenUtil.marshallInt(jsonObject.getString("parent_id"));
            String trackerName = jsonObject.getString("parent_id_deref");
            tracker = new ProjectOpenTracker(trackerId, trackerName);
            
            int statusId = ProjectOpenUtil.marshallInt(jsonObject.getString("ticket_status_id"));
            String statusName = jsonObject.getString("ticket_status_id_deref");
            status = new ProjectOpenStatus(statusId, statusName);
            
            int typeId = ProjectOpenUtil.marshallInt(jsonObject.getString("ticket_type_id"));
            String typeName = jsonObject.getString("ticket_type_id_deref");
            type = new ProjectOpenType(typeId, typeName);
            
            int assigneeId = ProjectOpenUtil.marshallInt(jsonObject.getString("ticket_assignee_id"));
            String assigneeName = jsonObject.getString("ticket_assignee_id_deref");
            assignee = new User(assigneeId, assigneeName);
            
            int customerContactId = ProjectOpenUtil.marshallInt(jsonObject.getString("ticket_customer_contact_id"));
            String customerContactName = jsonObject.getString("ticket_customer_contact_id_deref");
            customerContact = new User(customerContactId, customerContactName);
            
            int creationUserId = ProjectOpenUtil.marshallInt(jsonObject.getString("creation_user"));
            String creationUserName = jsonObject.getString("creation_user_deref");
            creationUser = new User(creationUserId, creationUserName);
            
            description = jsonObject.getString("ticket_description");
            creationDate = ProjectOpenUtil.marshallDate(jsonObject.getString("creation_date"));
            customerDeadline = ProjectOpenUtil.marshallDate(jsonObject.getString("ticket_customer_deadline"));
            lastModifiedDate = ProjectOpenUtil.marshallDate(jsonObject.getString("last_modified"));
            startDate = ProjectOpenUtil.marshallDate(jsonObject.getString("start_date"));
            resolutionDate = ProjectOpenUtil.marshallDate(jsonObject.getString("ticket_resolution_date"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
	}
	
	public String toXml() {
		int ticketStatusId = getStatus() == null ? 30000 : getStatus().getId();
		int ticketTypeId = getType() == null ? 30110 : getType().getId();
		String xml = "<?xml version='1.0'?>"
				+ "<im_ticket><parent_id>"
				+ getTracker().getId()
				+ "</parent_id><ticket_type_id>"
				+ ticketStatusId
				+ "</ticket_type_id><ticket_status_id>"
				+ ticketTypeId
				+ "</ticket_status_id><project_name>"
				+ getName()
				+ "</project_name><ticket_description>"
				+ getDescription()
				+ "</ticket_description></im_ticket>";
		return xml;
	}

}
