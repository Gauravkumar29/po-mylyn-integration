package com.project_open.mylyn.core.client;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.mylyn.commons.net.AbstractWebLocation;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.TaskAttributeMapper;
import org.eclipse.mylyn.tasks.core.data.TaskData;
import org.eclipse.mylyn.tasks.core.data.TaskDataCollector;

import com.project_open.mylyn.core.ProjectOpenCorePlugin;
import com.project_open.mylyn.core.ProjectOpenTaskMapper;
import com.project_open.mylyn.core.exception.ProjectOpenException;
import com.project_open.mylyn.core.model.Company;
import com.project_open.mylyn.core.model.ProjectOpenStatus;
import com.project_open.mylyn.core.model.ProjectOpenTracker;
import com.project_open.mylyn.core.model.ProjectOpenType;
import com.project_open.mylyn.core.model.Ticket;
import com.project_open.mylyn.core.model.User;
import com.project_open.mylyn.core.util.ProjectOpenUtil;

public class RestfulProjectOpenClient implements ProjectOpenClient {

    private final AbstractWebLocation location;

    private final RestfulProjectOpenReader projectOpenReader;

    private ProjectOpenClientData clientData;

    private ProjectOpenHttpClient httpClient;
	
	public RestfulProjectOpenClient(AbstractWebLocation location,
			ProjectOpenClientData clientData, TaskRepository repository) {
        this.location = location;
        this.clientData = clientData;

        projectOpenReader = new RestfulProjectOpenReader();

        httpClient = new ProjectOpenHttpClient(location, repository.getCharacterEncoding(),
                Boolean.valueOf(repository.getProperty("selfSignedSSL")));

        refreshRepositorySettings(repository);
	}

	public void refreshRepositorySettings(TaskRepository repository) {
		// Nothing to do yet
	}

	public ProjectOpenClientData getClientData() {
		return clientData;
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

	public Ticket newTicket(Ticket ticket, IProgressMonitor monitor)
			throws ProjectOpenException {
		//XXX Should be asynchronously
		String response = httpClient.executePost(
				"/intranet-rest/im_ticket", ticket.toXml(), monitor);
		
		Pattern regex = Pattern.compile("<object_id.*?>(.*?)</object_id>", Pattern.DOTALL);
		Matcher matcher = regex.matcher(response);
		if (matcher.find()) {
			int ticketId = Integer.valueOf(matcher.group(1));
			Ticket newTicket = getTicket(ticketId, monitor);
			ticket.setId(newTicket.getId());
			ticket.setName(newTicket.getName());
			ticket.setNummer(newTicket.getNummer());
			ticket.setCustomerContact(newTicket.getCustomerContact());
			ticket.setDescription(newTicket.getDescription());
			ticket.setStatus(newTicket.getStatus());
			ticket.setType(newTicket.getType());
			ticket.setCreationDate(newTicket.getCreationDate());
			ticket.setLastModifiedDate(newTicket.getLastModifiedDate());
		}
		
        return ticket;
	}

	public void updateTicket(Ticket ticket, IProgressMonitor monitor)
			throws ProjectOpenException {
		newTicket(ticket, monitor);
	}

	public Ticket getTicket(int id, IProgressMonitor monitor)
			throws ProjectOpenException {
		return projectOpenReader.readTicket(httpClient.executeGet("/intranet-rest/im_ticket?format=json&deref_p=1&ticket_id="
                + id, monitor));
	}

	public TaskData getTaskData(TaskRepository taskRepository, String taskId,
			IProgressMonitor monitor) {
        TaskData taskData = new TaskData(new TaskAttributeMapper(taskRepository),
                ProjectOpenCorePlugin.REPOSITORY_KIND, location.getUrl(), taskId);
        
        return taskData;
	}
	
	private List<ProjectOpenTracker> getTrackers(IProgressMonitor monitor) throws ProjectOpenException {
        return projectOpenReader.readTrackers(httpClient.executeGet("/intranet-rest/im_project?format=json&project_type_id=2502", monitor));
	}

    public List<User> getUsers(IProgressMonitor monitor) throws ProjectOpenException {
        return projectOpenReader.readUsers(httpClient.executeGet("/intranet-rest/user?format=json", monitor));
    }
    
    public List<Company> getCompanies(IProgressMonitor monitor) throws ProjectOpenException {
        return projectOpenReader.readCompanies(httpClient.executeGet("/intranet-rest/im_company?format=json", monitor));
    }

    public List<ProjectOpenStatus> getStates(IProgressMonitor monitor) throws ProjectOpenException {
        return projectOpenReader.readStates(httpClient.executeGet("/intranet-rest/im_category?format=json&category_type='Intranet%20Project%20Status'", monitor));
    }
    
    public List<ProjectOpenType> getTypes(IProgressMonitor monitor) throws ProjectOpenException {
        return projectOpenReader.readTypes(httpClient.executeGet("/intranet-rest/im_category?format=json&category_type='Intranet%20Project%20Type'", monitor));
    }

    public List<Ticket> getTickets(IProgressMonitor monitor) throws ProjectOpenException {
        return projectOpenReader.readTickets(
                httpClient.executeGet("/intranet-rest/im_ticket?format=json&deref_p=1", monitor));
    }

	public void updateRepositoryData(boolean force, IProgressMonitor monitor) {
        if (hasRepositoryData() && !force) {
            return;
        }

        try {
        	monitor.subTask("Retrieving trackers");
            clientData.setTrackers(getTrackers(monitor));
            monitorWorked(monitor);
        	
        	monitor.subTask("Retrieving companies");
            clientData.setCompanies(getCompanies(monitor));
            monitorWorked(monitor);

            monitor.subTask("Retrieving users");
            clientData.setUsers(getUsers(monitor));
            monitorWorked(monitor);

            monitor.subTask("Retrieving states");
            clientData.setStates(getStates(monitor));
            monitorWorked(monitor);
            
            monitor.subTask("Retrieving types");
            clientData.setTypes(getTypes(monitor));
            monitorWorked(monitor);

            clientData.lastupdate = new Date().getTime();
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException(e);
        }

	}

	private void monitorWorked(IProgressMonitor monitor) {
		monitor.worked(1);
        if (monitor.isCanceled()) {
            throw new OperationCanceledException();
        }
	}

	private boolean hasRepositoryData() {
        return (clientData.lastupdate != 0);
	}

	public void performQuery(TaskRepository repository, IRepositoryQuery query,
			TaskDataCollector collector, IProgressMonitor monitor)
			throws CoreException {
		try {
			//TODO integrate query
            List<Ticket> tickets = getTickets(monitor);
            for (Ticket ticket : tickets) {
                TaskData taskData = getTaskDataForTicket(repository, ticket);
                collector.accept(taskData);
            }
        } catch (ProjectOpenException e) {
        	ProjectOpenCorePlugin.getDefault().getLog().log(new Status(Status.INFO, ProjectOpenCorePlugin.PLUGIN_ID, Status.CANCEL, e.getMessage(), e));
            throw new CoreException(Status.CANCEL_STATUS);
        }
	}

	private TaskData getTaskDataForTicket(TaskRepository repository,
			Ticket ticket) {
        String id = String.valueOf(ticket.getId());
        String name = ticket.getName();
        String description = ticket.getDescription();
        String owner = String.valueOf(ticket.getCreationUser());
        Date creationDate = ticket.getCreationDate();
        Date dateModified = ticket.getLastModifiedDate();
        Date customerDeadline = ticket.getCustomerDeadline();
        Date resolutionDate = ticket.getResolutionDate();
        
        TaskData taskData = new TaskData(new TaskAttributeMapper(repository),
        		ProjectOpenCorePlugin.REPOSITORY_KIND, location.getUrl(), id);
        taskData.setPartial(true);

        ProjectOpenTaskMapper mapper = new ProjectOpenTaskMapper(taskData, true);
        mapper.setTaskKey(id);
        mapper.setCreationDate(creationDate);
        mapper.setModificationDate(dateModified);
        mapper.setSummary(name);
        mapper.setOwner(owner);
        mapper.setDescription(description);
        mapper.setDueDate(customerDeadline);
		mapper.setCompletionDate(resolutionDate);
        mapper.setTaskUrl(ProjectOpenUtil.getTicketUrl(repository.getUrl(), id));

        return taskData;
	}

}
