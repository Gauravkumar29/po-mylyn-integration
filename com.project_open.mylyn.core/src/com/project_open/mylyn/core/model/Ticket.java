package com.project_open.mylyn.core.model;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.project_open.mylyn.core.util.ProjectOpenUtil;

public class Ticket extends Project {

	private static final long serialVersionUID = 1L;

	private int trackerId;
	private int statusId;
	private int typeId;
	private int assigneeId;
	private int customerContactId;
	private String description = "";
	private Date customerDeadline;
	private Date creationDate;
	private Date lastModifiedDate;
	private User creationUser;
	private Date startDate;
	private Date resolutionDate;

	public int getTrackerId() {
		return trackerId;
	}

	public void setTrackerId(int trackerId) {
		this.trackerId = trackerId;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(int assigneeId) {
		this.assigneeId = assigneeId;
	}

	public int getCustomerContactId() {
		return customerContactId;
	}

	public void setCustomerContactId(int customerContactId) {
		this.customerContactId = customerContactId;
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
            trackerId = ProjectOpenUtil.marshallInt(jsonObject.getString("parent_id"));
            statusId = ProjectOpenUtil.marshallInt(jsonObject.getString("ticket_status_id"));
            typeId = ProjectOpenUtil.marshallInt(jsonObject.getString("ticket_type_id"));
            assigneeId = ProjectOpenUtil.marshallInt(jsonObject.getString("ticket_assignee_id"));
            customerContactId = ProjectOpenUtil.marshallInt(jsonObject.getString("ticket_customer_contact_id"));
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
		int ticketStatusId = statusId == 0 ? 30000 : statusId;
		int ticketTypeId = typeId == 0 ? 30110 : typeId;
		String xml = "<?xml version='1.0'?>"
				+ "<im_ticket><parent_id>"
				+ getTrackerId()
				+ "</parent_id><ticket_type_id>"
				+ ticketTypeId
				+ "</ticket_type_id><ticket_status_id>"
				+ ticketStatusId
				+ "</ticket_status_id><project_name>"
				+ getName()
				+ "</project_name><ticket_description>"
				+ getDescription()
				+ "</ticket_description></im_ticket>";
		return xml;
	}

}
