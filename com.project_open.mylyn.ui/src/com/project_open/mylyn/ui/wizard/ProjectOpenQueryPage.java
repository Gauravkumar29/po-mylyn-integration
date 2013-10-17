package com.project_open.mylyn.ui.wizard;

import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.ui.TasksUi;
import org.eclipse.mylyn.tasks.ui.wizards.AbstractRepositoryQueryPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.project_open.mylyn.core.ProjectOpenCorePlugin;
import com.project_open.mylyn.core.ProjectOpenRepositoryConnector;
import com.project_open.mylyn.core.client.ProjectOpenClient;

/**
 * @author Markus Knittig
 *
 */
public class ProjectOpenQueryPage extends AbstractRepositoryQueryPage {

    private static final String TITLE = "Queries";

    private ProjectOpenClient client;

    public ProjectOpenQueryPage(TaskRepository taskRepository, IRepositoryQuery query) {
        super(TITLE, taskRepository, query);

        ProjectOpenRepositoryConnector connector = (ProjectOpenRepositoryConnector) TasksUi
                .getRepositoryManager().getRepositoryConnector(
                        ProjectOpenCorePlugin.REPOSITORY_KIND);
        client = connector.getClientManager().getClient(getTaskRepository());

        setTitle(TITLE);
    }

    public ProjectOpenQueryPage(TaskRepository repository) {
        this(repository, null);
    }

	public void createControl(Composite parent) {
        Composite control = new Composite(parent, SWT.NONE);
        setControl(control);
	}

	@Override
	public String getQueryTitle() {
		return "Default";
	}

	@Override
	public void applyTo(IRepositoryQuery query) {
		// TODO Auto-generated method stub
		
	}

}
