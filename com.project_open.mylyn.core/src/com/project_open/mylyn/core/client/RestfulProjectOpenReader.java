package com.project_open.mylyn.core.client;

import java.util.List;

import org.json.JSONObject;

import com.project_open.mylyn.core.exception.ProjectOpenException;
import com.project_open.mylyn.core.model.Company;
import com.project_open.mylyn.core.model.ProjectOpenStatus;
import com.project_open.mylyn.core.model.ProjectOpenTracker;
import com.project_open.mylyn.core.model.ProjectOpenType;
import com.project_open.mylyn.core.model.Ticket;
import com.project_open.mylyn.core.model.User;
import com.project_open.mylyn.core.util.ProjectOpenUtil;

public class RestfulProjectOpenReader {

	public boolean isSuccessTrue(String source) throws ProjectOpenException {
		try {
			JSONObject jsonStat = new JSONObject(source);
			return jsonStat.getString("success").equals("true");
		} catch (Exception e) {
			throw new ProjectOpenException(e.getMessage(), e);
		}
	}

	public List<ProjectOpenTracker> readTrackers(String source) throws ProjectOpenException {
		try {
			JSONObject jsonTrackers = new JSONObject(source);
			return ProjectOpenUtil.parseEntities(ProjectOpenTracker.class,
					jsonTrackers.getJSONArray("data"));
		} catch (Exception e) {
			throw new ProjectOpenException(e.getMessage(), e);
		}
	}

	public List<User> readUsers(String source) throws ProjectOpenException {
		try {
			JSONObject jsonUsers = new JSONObject(source);
			return ProjectOpenUtil.parseEntities(User.class,
					jsonUsers.getJSONArray("data"));
		} catch (Exception e) {
			throw new ProjectOpenException(e.getMessage(), e);
		}
	}

	public List<Company> readCompanies(String source) throws ProjectOpenException {
		try {
			JSONObject jsonCompanies = new JSONObject(source);
			return ProjectOpenUtil.parseEntities(Company.class,
					jsonCompanies.getJSONArray("data"));
		} catch (Exception e) {
			throw new ProjectOpenException(e.getMessage(), e);
		}
	}

	public List<ProjectOpenStatus> readStates(String source) throws ProjectOpenException {
		try {
			JSONObject jsonStates = new JSONObject(source);
			return ProjectOpenUtil.parseEntities(ProjectOpenStatus.class,
					jsonStates.getJSONArray("data"));
		} catch (Exception e) {
			throw new ProjectOpenException(e.getMessage(), e);
		}
	}

	public List<ProjectOpenType> readTypes(String source) throws ProjectOpenException {
		try {
			JSONObject jsonTypes = new JSONObject(source);
			return ProjectOpenUtil.parseEntities(ProjectOpenType.class,
					jsonTypes.getJSONArray("data"));
		} catch (Exception e) {
			throw new ProjectOpenException(e.getMessage(), e);
		}
	}

	public List<Ticket> readTickets(String source) throws ProjectOpenException {
        try {
            JSONObject jsonTickets = new JSONObject(source);
            return ProjectOpenUtil.parseEntities(Ticket.class,
            		jsonTickets.getJSONArray("data"));
        } catch (Exception e) {
            throw new ProjectOpenException(e.getMessage(), e);
        }
	}

	public Ticket readTicket(String source) throws ProjectOpenException {
		return readTickets(source).get(0);
	}

}
