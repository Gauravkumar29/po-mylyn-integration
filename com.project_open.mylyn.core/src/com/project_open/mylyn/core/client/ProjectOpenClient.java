package com.project_open.mylyn.core.client;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.TaskData;
import org.eclipse.mylyn.tasks.core.data.TaskDataCollector;

import com.project_open.mylyn.core.exception.ProjectOpenException;
import com.project_open.mylyn.core.model.Ticket;

public interface ProjectOpenClient {
	
	ProjectOpenClientData getClientData();

	void refreshRepositorySettings(TaskRepository repository);

    boolean validCredentials(String username, String password, IProgressMonitor monitor);

	void newTicket(Ticket ticket, NullProgressMonitor nullProgressMonitor) throws ProjectOpenException;

	void updateTicket(Ticket ticket, IProgressMonitor monitor) throws ProjectOpenException;

	Ticket getTicket(int intValue, IProgressMonitor monitor) throws ProjectOpenException;

	TaskData getTaskData(TaskRepository taskRepository, String taskId,
			IProgressMonitor monitor);

	void updateRepositoryData(boolean b, IProgressMonitor monitor);

	void performQuery(TaskRepository repository, IRepositoryQuery query,
			TaskDataCollector collector, IProgressMonitor monitor) throws CoreException;

}
