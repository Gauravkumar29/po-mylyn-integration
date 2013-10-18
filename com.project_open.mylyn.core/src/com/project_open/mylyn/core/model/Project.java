package com.project_open.mylyn.core.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Project implements Marshallable {

	private static final long serialVersionUID = 1L;

	int id;
	private String name;
	private String nummer;
	private Company company;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNummer() {
		return nummer;
	}

	public void setNummer(String nummer) {
		this.nummer = nummer;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void marshall(JSONObject jsonObject) {
		try {
            id = jsonObject.getInt("id");
            name = jsonObject.getString("object_name");
            nummer = jsonObject.getString("project_nr");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
	}

	public JSONObject unmarshall() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
