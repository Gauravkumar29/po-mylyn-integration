package com.project_open.mylyn.core.model;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Marshallable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String email;

	public User() {
		// default constructor
	}
	
	public User(int id, String name) {
		setId(id);
		setName(name);
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void marshall(JSONObject jsonObject) {
		try {
            id = jsonObject.getInt("id");
            email = jsonObject.getString("email");
            name = jsonObject.getString("object_name");
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
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}	
	
}
