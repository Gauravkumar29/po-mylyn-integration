package com.project_open.mylyn.core.model;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Marshallable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String email;

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

}
