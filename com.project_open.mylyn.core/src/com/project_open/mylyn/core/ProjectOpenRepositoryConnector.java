/**
 * 
 */
package com.project_open.mylyn.core;

import java.util.Date;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.mylyn.tasks.core.AbstractRepositoryConnector;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.core.ITaskMapping;
import org.eclipse.mylyn.tasks.core.RepositoryResponse;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.TaskRepositoryLocationFactory;
import org.eclipse.mylyn.tasks.core.data.AbstractTaskDataHandler;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskAttributeMapper;
import org.eclipse.mylyn.tasks.core.data.TaskData;
import org.eclipse.mylyn.tasks.core.data.TaskDataCollector;
import org.eclipse.mylyn.tasks.core.data.TaskMapper;
import org.eclipse.mylyn.tasks.core.sync.ISynchronizationSession;

import com.project_open.mylyn.core.client.ProjectOpenClient;

/**
 * @author mak
 *
 */
public class ProjectOpenRepositoryConnector extends AbstractRepositoryConnector {

    private static final String CLIENT_LABEL = "ProjectOpen";

    //private final static Pattern REVIEW_REQUEST_ID_FROM_TASK_URL = Pattern
    //        .compile(ProjectOpenConstants.REVIEW_REQUEST_URL + "(\\d+)");
    private final static Pattern REVIEW_REQUEST_ID_FROM_TASK_URL = null;

    private ProjectOpenClientManager clientManager;

    private TaskRepositoryLocationFactory taskRepositoryLocationFactory;

    public ProjectOpenRepositoryConnector() {
        super();

        if (ProjectOpenCorePlugin.getDefault() != null) {
            ProjectOpenCorePlugin.getDefault().setConnector(this);
        }
    }

    @Override
    public boolean canCreateNewTask(TaskRepository repository) {
        return repository.getConnectorKind().equals(getConnectorKind());
    }

    @Override
    public boolean canCreateTaskFromKey(TaskRepository repository) {
        return true;
    }

    @Override
    public String getConnectorKind() {
        return ProjectOpenCorePlugin.REPOSITORY_KIND;
    }

    @Override
    public String getLabel() {
        return CLIENT_LABEL;
    }

    @Override
    public String getRepositoryUrlFromTaskUrl(String taskFullUrl) {
    	/*
        int index = taskFullUrl.indexOf(ProjectOpenConstants.REVIEW_REQUEST_URL);

        if (index > 0) {
            return taskFullUrl.substring(0, index);
        } else {
            return null;
        }*/
        //TODO
        return taskFullUrl;
    }

    @Override
    public TaskData getTaskData(TaskRepository taskRepository, String taskId,
            IProgressMonitor monitor) throws CoreException {
        ProjectOpenClient client = getClientManager().getClient(taskRepository);
        return client.getTaskData(taskRepository, taskId, monitor);
    }

    @Override
    public String getTaskIdFromTaskUrl(String taskFullUrl) {
        Matcher matcher = REVIEW_REQUEST_ID_FROM_TASK_URL.matcher(taskFullUrl);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    @Override
    public String getTaskUrl(String repositoryUrl, String taskId) {
        return null;
        		/*ProjectOpenUtil.getReviewRequestUrl(repositoryUrl, taskId);*/
    }

    @Override
    public boolean hasTaskChanged(TaskRepository taskRepository, ITask task, TaskData taskData) {
        TaskMapper scheme = new TaskMapper(taskData);
        Date repositoryDate = scheme.getModificationDate();
        Date localeDate = task.getModificationDate();

        if (localeDate != null) {
            return !localeDate.equals(repositoryDate);
        }

        return true;
    }

    @Override
    public IStatus performQuery(TaskRepository repository, IRepositoryQuery query,
            TaskDataCollector collector, ISynchronizationSession session, IProgressMonitor monitor) {
        ProjectOpenClient client = getClientManager().getClient(repository);

        try {
            client.updateRepositoryData(false, monitor);
            client.performQuery(repository, query, collector, monitor);
        } catch (CoreException e) {
            return e.getStatus();
        }

        return Status.OK_STATUS;
    }

    @Override
    public void updateRepositoryConfiguration(TaskRepository taskRepository,
            IProgressMonitor monitor) throws CoreException {
        // ignore
    }

    @Override
    public void updateTaskFromTaskData(TaskRepository taskRepository, ITask task, TaskData taskData) {
        TaskMapper scheme = new TaskMapper(taskData);
        scheme.applyTo(task);
        task.setCompletionDate(scheme.getCompletionDate());
    }

    @Override
    public boolean canSynchronizeTask(TaskRepository taskRepository, ITask task) {
        return false;
    }

    public synchronized ProjectOpenClientManager getClientManager() {
        if (clientManager == null) {
            IPath path = ProjectOpenCorePlugin.getDefault().getRepostioryAttributeCachePath();
            clientManager = new ProjectOpenClientManager(path.toFile());
        }
        clientManager.setTaskRepositoryLocationFactory(taskRepositoryLocationFactory);

        return clientManager;
    }

    public void stop() {
        if (clientManager != null) {
            clientManager.writeCache();
        }
    }

    public void setTaskRepositoryLocationFactory(TaskRepositoryLocationFactory factory) {
        this.taskRepositoryLocationFactory = factory;
        if (clientManager != null) {
            clientManager.setTaskRepositoryLocationFactory(factory);
        }
    }

    @Override
    public AbstractTaskDataHandler getTaskDataHandler() {
        return new AbstractTaskDataHandler() {
            @Override
            public TaskAttributeMapper getAttributeMapper(TaskRepository taskRepository) {
                return new TaskAttributeMapper(taskRepository) {
                };
            }

            @Override
            public boolean initializeTaskData(TaskRepository repository, TaskData data,
                    ITaskMapping initializationData, IProgressMonitor monitor) throws CoreException {
                // ignore
                return false;
            }

              @Override
            public RepositoryResponse postTaskData(TaskRepository repository, TaskData taskData,
                    Set<TaskAttribute> oldAttributes, IProgressMonitor monitor)
                    throws CoreException {
                // ignore
                return null;
            }
        };

    }
}
