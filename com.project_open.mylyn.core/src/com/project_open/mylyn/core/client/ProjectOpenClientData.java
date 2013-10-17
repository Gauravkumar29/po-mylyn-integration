package com.project_open.mylyn.core.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.project_open.mylyn.core.model.Company;
import com.project_open.mylyn.core.model.Status;
import com.project_open.mylyn.core.model.Type;
import com.project_open.mylyn.core.model.User;

public class ProjectOpenClientData implements Serializable {

    private static final long serialVersionUID = 1L;
	
	private List<User> users = new ArrayList<User>();
    private List<Company> companies = new ArrayList<Company>();
    private List<Status> states = new ArrayList<Status>();
    private List<Type> types = new ArrayList<Type>();

    long lastupdate = 0;

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

    public List<Status> getStates() {
        return states;
    }

    public void setStates(List<Status> states) {
        this.states = states;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

}
