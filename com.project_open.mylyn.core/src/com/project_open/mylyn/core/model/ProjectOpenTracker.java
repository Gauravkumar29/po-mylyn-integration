package com.project_open.mylyn.core.model;

import org.json.JSONException;
import org.json.JSONObject;

public class ProjectOpenTracker extends Project {

	private static final long serialVersionUID = 1L;
	
	private int companyId;

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	@Override
	public void marshall(JSONObject jsonObject) {
		super.marshall(jsonObject);
		try {
			companyId = jsonObject.getInt("company_id");
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
	
}
