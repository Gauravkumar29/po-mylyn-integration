package com.project_open.mylyn.ui.wizard;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.ui.wizards.NewTaskWizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;

import com.project_open.mylyn.core.client.ProjectOpenClient;
import com.project_open.mylyn.core.exception.ProjectOpenException;

/**
 * @author Markus Knittig
 *
 */
public class NewProjectOpenTicketWizard extends NewTaskWizard implements INewWizard {

    private ProjectOpenClient client;

    private NewTicketPage newTicketWizardPage;

    public NewProjectOpenTicketWizard(TaskRepository taskRepository, ProjectOpenClient client) {
        super(taskRepository, null);
        this.client = client;
    }

    @Override
    public void addPages() {
        newTicketWizardPage = new NewTicketPage(client.getClientData());
        addPage(newTicketWizardPage);
    }

    @Override
    public void createPageControls(Composite pageContainer) {
        super.createPageControls(pageContainer);
    }

    @Override
    public boolean performFinish() {
        try {
            client.newTicket(newTicketWizardPage.getTicket(), new NullProgressMonitor());
        } catch (ProjectOpenException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

}
