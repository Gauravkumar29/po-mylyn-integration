package com.project_open.mylyn.core.client;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.mylyn.commons.net.AbstractWebLocation;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.TaskData;
import org.eclipse.mylyn.tasks.core.data.TaskDataCollector;

import com.project_open.mylyn.core.exception.ProjectOpenException;
import com.project_open.mylyn.core.model.Ticket;

public class RestfulProjectOpenClient implements ProjectOpenClient {

    private final AbstractWebLocation location;

    private final RestfulProjectOpenReader reviewboardReader;

    private ProjectOpenClientData clientData;

    private ProjectOpenHttpClient httpClient;
	
	public RestfulProjectOpenClient(AbstractWebLocation location,
			ProjectOpenClientData clientData, TaskRepository repository) {
        this.location = location;
        this.clientData = clientData;

        reviewboardReader = new RestfulProjectOpenReader();

        httpClient = new ProjectOpenHttpClient(location, repository.getCharacterEncoding(),
                Boolean.valueOf(repository.getProperty("selfSignedSSL")));

        refreshRepositorySettings(repository);
	}

	public void refreshRepositorySettings(TaskRepository repository) {
		// TODO Auto-generated method stub
		
	}

	public ProjectOpenClientData getClientData() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean validCredentials(String username, String password,
			IProgressMonitor monitor) {
		try {
            httpClient.validate(monitor);
            return true;
        } catch (Exception e) {
            return false;
        }
	}

	public void newTicket(Ticket ticket, NullProgressMonitor nullProgressMonitor)
			throws ProjectOpenException {
		// TODO Auto-generated method stub
		
	}

	public ProjectOpenClient getClient(TaskRepository repository) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateTicket(Ticket ticket, IProgressMonitor monitor)
			throws ProjectOpenException {
		// TODO Auto-generated method stub
		
	}

	public Ticket getTicket(int intValue, IProgressMonitor monitor)
			throws ProjectOpenException {
		// TODO Auto-generated method stub
		return null;
	}

	public TaskData getTaskData(TaskRepository taskRepository, String taskId,
			IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateRepositoryData(boolean b, IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
	}

	public void performQuery(TaskRepository repository, IRepositoryQuery query,
			TaskDataCollector collector, IProgressMonitor monitor)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

}
