package com.project_open.mylyn.ui;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.core.ITaskMapping;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.ui.AbstractRepositoryConnectorUi;
import org.eclipse.mylyn.tasks.ui.TasksUi;
import org.eclipse.mylyn.tasks.ui.wizards.ITaskRepositoryPage;
import org.eclipse.mylyn.tasks.ui.wizards.RepositoryQueryWizard;

import com.project_open.mylyn.core.ProjectOpenCorePlugin;
import com.project_open.mylyn.core.ProjectOpenRepositoryConnector;
import com.project_open.mylyn.core.client.ProjectOpenClient;
import com.project_open.mylyn.ui.wizard.NewProjectOpenTicketWizard;
import com.project_open.mylyn.ui.wizard.ProjectOpenQueryPage;
import com.project_open.mylyn.ui.wizard.ProjectOpenRepositorySettingsPage;

/**
 * @author Markus Knittig
 *
 */
public class ProjectOpenConnectorUi extends AbstractRepositoryConnectorUi {

    @Override
    public String getConnectorKind() {
        return ProjectOpenCorePlugin.REPOSITORY_KIND;
    }

    @Override
    public ITaskRepositoryPage getSettingsPage(TaskRepository taskRepository) {
        return new ProjectOpenRepositorySettingsPage(taskRepository);
    }

    @Override
    public boolean hasSearchPage() {
        return false;
    }

    @Override
    public IWizard getNewTaskWizard(TaskRepository taskRepository, ITaskMapping taskSelection) {
        ProjectOpenRepositoryConnector connector = (ProjectOpenRepositoryConnector) TasksUi
                .getRepositoryManager().getRepositoryConnector(
                        ProjectOpenCorePlugin.REPOSITORY_KIND);
        final ProjectOpenClient client = connector.getClientManager().getClient(taskRepository);

        return new NewProjectOpenTicketWizard(taskRepository, client);
    }

    @Override
    public IWizard getQueryWizard(TaskRepository taskRepository, IRepositoryQuery queryToEdit) {
        RepositoryQueryWizard wizard = new RepositoryQueryWizard(taskRepository);
        wizard.addPage(new ProjectOpenQueryPage(taskRepository, queryToEdit));
        return wizard;
    }

    @Override
    public String getTaskKindLabel(ITask task) {
        return "Ticket";
    }
/*
    @Override
    public String getAccountCreationUrl(TaskRepository taskRepository) {
        return taskRepository.getRepositoryUrl() + "/account/login/"; //$NON-NLS-1$
    }

    @Override
    public String getAccountManagementUrl(TaskRepository taskRepository) {
        return taskRepository.getRepositoryUrl() + "/account/preferences/"; //$NON-NLS-1$
    }
*/
}
