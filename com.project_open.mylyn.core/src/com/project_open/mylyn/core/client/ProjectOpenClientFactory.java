package com.project_open.mylyn.core.client;

import java.net.MalformedURLException;

import org.eclipse.mylyn.commons.net.AbstractWebLocation;
import org.eclipse.mylyn.commons.net.AuthenticationCredentials;
import org.eclipse.mylyn.commons.net.AuthenticationType;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.TaskRepositoryLocationFactory;

public class ProjectOpenClientFactory {

	protected static TaskRepositoryLocationFactory taskRepositoryLocationFactory = new TaskRepositoryLocationFactory();

	public static ProjectOpenClient createClient(TaskRepository taskRepository)
			throws MalformedURLException {
		taskRepository.setCredentials(AuthenticationType.HTTP, taskRepository.getCredentials(AuthenticationType.REPOSITORY), taskRepository.getSavePassword(AuthenticationType.REPOSITORY));
		AbstractWebLocation location = taskRepositoryLocationFactory.createWebLocation(taskRepository);
		ProjectOpenClient client = new RestfulProjectOpenClient(location, new ProjectOpenClientData(), taskRepository);
		return client;
	}

}
