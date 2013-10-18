package com.project_open.mylyn.ui.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.mylyn.tasks.ui.editors.AbstractTaskEditorPage;
import org.eclipse.mylyn.tasks.ui.editors.TaskEditor;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.project_open.mylyn.core.ProjectOpenCorePlugin;
import com.project_open.mylyn.core.ProjectOpenRepositoryConnector;
import com.project_open.mylyn.core.client.ProjectOpenClient;
import com.project_open.mylyn.core.model.Ticket;
import com.project_open.mylyn.ui.ProjectOpenUiPlugin;

/**
 * @author Markus Knittig
 *
 */
public class TicketEditorPage extends AbstractTaskEditorPage {

    private TaskEditor editor;
    private ScrolledForm form;
    private FormToolkit toolkit;
    private Composite editorComposite;

    private List<AbstractFormPagePart> parts;

    private Ticket ticket;
    private ProjectOpenClient client;

    public TicketEditorPage(TaskEditor editor, String title) {
        super(editor, ProjectOpenCorePlugin.REPOSITORY_KIND);
        this.editor = editor;
        parts = new ArrayList<AbstractFormPagePart>();

        ProjectOpenRepositoryConnector connector =
            ProjectOpenCorePlugin.getDefault().getConnector();
        client = connector.getClientManager().getClient(getTaskRepository());
    }

    @Override
    public void createFormContent(IManagedForm managedForm) {
        super.createFormContent(managedForm);
        form = managedForm.getForm();
        toolkit = managedForm.getToolkit();
        editorComposite = form.getBody();

        // TODO consider using TableWrapLayout, it makes resizing much faster
        GridLayout editorLayout = new GridLayout();
        editorComposite.setLayout(editorLayout);
        editorComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

        Display.getCurrent().asyncExec(new Runnable() {
            public void run() {
                downloadTicketAndRefresh();
            }
        });
    }

    private void createFormParts() {
        TicketEditorHeaderPart headerPart = new TicketEditorHeaderPart(editor);
        parts.add(headerPart);
        if (ticket != null) {
            parts.add(new TicketEditorAttributesPart(ticket, client, headerPart));
        }
    }

    private void clearFormContent() {
        for (AbstractFormPagePart part : parts) {
            getManagedForm().removePart(part);
        }
        parts.clear();

        // remove all of the old widgets so that we can redraw the editor
        for (Control child : editorComposite.getChildren()) {
            child.dispose();
        }
    }

    private void setBusy(boolean busy) {
        getEditor().showBusy(busy);
    }

    private void downloadTicketAndRefresh() {
        Job job = new Job(NLS.bind("Retrieving ticket {0}...", getTask().getTaskId())) {
            @Override
            protected IStatus run(IProgressMonitor monitor) {
                monitor.subTask("Retrieving ticket");
                try {
                    ticket = client.getTicket(Integer.valueOf(getTask().getTaskId()).intValue(), monitor);
                } catch (Exception e) {
                    return new Status(IStatus.ERROR, ProjectOpenUiPlugin.PLUGIN_ID, e.getMessage());
                }
                return new Status(IStatus.OK, ProjectOpenUiPlugin.PLUGIN_ID, null);
            }
        };
        job.addJobChangeListener(new JobChangeAdapter() {
            @Override
            public void done(final IJobChangeEvent event) {
                Display.getDefault().asyncExec(new Runnable() {
                    public void run() {
                        ticketUpdateCompleted();
                    }
                });
            }
        });
        job.schedule();
        setBusy(true);
    }

    private void ticketUpdateCompleted() {
        setBusy(false);
        createInitialFormContent();
    }

    private void createInitialFormContent() {
        clearFormContent();
        createFormParts();

        for (AbstractFormPagePart part : parts) {
            getManagedForm().addPart(part);
            part.initialize(getManagedForm());
            part.createControl(editorComposite, toolkit);
        }

        form.layout(true, true);
        form.reflow(true);
        form.setRedraw(true);
    }

}
