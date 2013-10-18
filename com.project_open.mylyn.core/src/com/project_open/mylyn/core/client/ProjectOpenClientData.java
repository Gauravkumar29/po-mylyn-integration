package com.project_open.mylyn.core.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.project_open.mylyn.core.model.Company;
import com.project_open.mylyn.core.model.ProjectOpenStatus;
import com.project_open.mylyn.core.model.ProjectOpenTracker;
import com.project_open.mylyn.core.model.ProjectOpenType;
import com.project_open.mylyn.core.model.User;

public class ProjectOpenClientData implements Serializable {

    private static final long serialVersionUID = 1L;
	
    private List<ProjectOpenTracker> trackers = new ArrayList<ProjectOpenTracker>();
	private List<User> users = new ArrayList<User>();
    private List<Company> companies = new ArrayList<Company>();
    private List<ProjectOpenStatus> states = new ArrayList<ProjectOpenStatus>();
    private List<ProjectOpenType> types = new ArrayList<ProjectOpenType>();

    long lastupdate = 0;

    public List<ProjectOpenTracker> getTrackers() {
		return trackers;
	}

	public void setTrackers(List<ProjectOpenTracker> trackers) {
		this.trackers = trackers;
	}

	public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public List<ProjectOpenStatus> getStates() {
        return states;
    }

    public void setStates(List<ProjectOpenStatus> states) {
        this.states = states;
    }

    public List<ProjectOpenType> getTypes() {
        return types;
    }

    public void setTypes(List<ProjectOpenType> types) {
        this.types = types;
    }

}
