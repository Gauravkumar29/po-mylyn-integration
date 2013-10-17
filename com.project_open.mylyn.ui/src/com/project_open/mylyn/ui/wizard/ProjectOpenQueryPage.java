package com.project_open.mylyn.ui.wizard;

import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.ui.TasksUi;
import org.eclipse.mylyn.tasks.ui.wizards.AbstractRepositoryQueryPage;
import org.eclipse.swt.widgets.Composite;

import com.project_open.mylyn.core.ProjectOpenCorePlugin;
import com.project_open.mylyn.core.ProjectOpenRepositoryConnector;
import com.project_open.mylyn.core.client.ProjectOpenClient;

/**
 * @author Markus Knittig
 *
 */
public class ProjectOpenQueryPage extends AbstractRepositoryQueryPage {

    private static final String TITLE = "Enter query parameters";

    private static final String DESCRIPTION = "Select options to create a query";

    private static final String TITLE_QUERY_TITLE = "Query title";

    private ProjectOpenClient client;

    private IRepositoryQuery query;

    public ProjectOpenQueryPage(TaskRepository taskRepository, IRepositoryQuery query) {
        super(TITLE, taskRepository, query);

        this.query = query;

        ProjectOpenRepositoryConnector connector = (ProjectOpenRepositoryConnector) TasksUi
                .getRepositoryManager().getRepositoryConnector(
                        ProjectOpenCorePlugin.REPOSITORY_KIND);
        client = connector.getClientManager().getClient(getTaskRepository());

        setTitle(TITLE);
        setDescription(DESCRIPTION);
    }

    public ProjectOpenQueryPage(TaskRepository repository) {
        this(repository, null);
    }

	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getQueryTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void applyTo(IRepositoryQuery query) {
		// TODO Auto-generated method stub
		
	}

}
