package com.project_open.mylyn.core.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Category implements Marshallable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;

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

	public void marshall(JSONObject jsonObject) {
		try {
            id = jsonObject.getInt("id");
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
