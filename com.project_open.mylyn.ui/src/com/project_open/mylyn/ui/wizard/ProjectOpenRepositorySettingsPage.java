package com.project_open.mylyn.ui.wizard;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.ui.TasksUi;
import org.eclipse.mylyn.tasks.ui.wizards.AbstractRepositorySettingsPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.AuthenticationEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.project_open.mylyn.core.ProjectOpenCorePlugin;
import com.project_open.mylyn.core.ProjectOpenRepositoryConnector;
import com.project_open.mylyn.core.client.ProjectOpenClient;
import com.project_open.mylyn.core.client.ProjectOpenClientFactory;
import com.project_open.mylyn.ui.ProjectOpenUiPlugin;

/**
 * @author Markus Knittig
 *
 */
public class ProjectOpenRepositorySettingsPage extends AbstractRepositorySettingsPage {

    private static final String TITLE = "]project-open[ Repository Settings";

    private static final String DESCRIPTION = "Example: project-open.your-domain.com";

    private final TaskRepository taskRepository;

    private String checkedUrl = null;

    private boolean authenticated;

    private String username = "";
    private String password = "";

    public ProjectOpenRepositorySettingsPage(TaskRepository taskRepository) {
        super(TITLE, DESCRIPTION, taskRepository);

        this.taskRepository = taskRepository;
        setNeedsAnonymousLogin(false);
        setNeedsEncoding(false);
        setNeedsTimeZone(false);
        setNeedsValidation(true);
        setNeedsHttpAuth(false);
    }

    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);
        checkedUrl = getRepositoryUrl();
    }

    @Override
    public boolean isPageComplete() {
        return super.isPageComplete() && checkedUrl != null
                && checkedUrl.equals(getRepositoryUrl())
                && username.equals(getUserName())
                && password.equals(getPassword())
                && authenticated;
    }

    @Override
    protected void createAdditionalControls(Composite parent) {
        final Button selfSignedSSLCheckbox = new Button(parent, SWT.CHECK);
        selfSignedSSLCheckbox.setText("Accept self-signed SSL certificates");
        selfSignedSSLCheckbox.addListener(SWT.Modify, new Listener() {
            public void handleEvent(Event event) {
                taskRepository.setProperty("selfSignedSSL",
                        String.valueOf(selfSignedSSLCheckbox.getSelection()));
            }
        });
    }

    @Override
    public String getConnectorKind() {
        return ProjectOpenCorePlugin.REPOSITORY_KIND;
    }

    @Override
    protected Validator getValidator(final TaskRepository repository) {
        username = getUserName();
        password = getPassword();

        return new Validator() {
            @Override
            public void run(IProgressMonitor monitor) throws CoreException {
                authenticated = false;

                ProjectOpenRepositorySettingsPage.this.checkedUrl = repository.getRepositoryUrl();

				try {
					ProjectOpenClient client = ProjectOpenClientFactory.createClient(repository);
	                if (!client.validCredentials(username, password, monitor)) {
	                    throw new CoreException(new Status(Status.ERROR, ProjectOpenUiPlugin.PLUGIN_ID,
	                            "Username or password wrong!"));
	                }
				} catch (MalformedURLException e) {
					throw new CoreException(new Status(Status.ERROR, ProjectOpenUiPlugin.PLUGIN_ID,
                            "URL wrong!"));
				}

                authenticated = true;
            }
        };
    }

    @Override
    protected boolean isValidUrl(String url) {
        if ((url.startsWith(URL_PREFIX_HTTPS) || url.startsWith(URL_PREFIX_HTTP))
                && !url.endsWith("/")) {
            try {
                new URL(url);
                return true;
            } catch (MalformedURLException e) {
                // ignore
            }
        }
        return false;
    }

}
