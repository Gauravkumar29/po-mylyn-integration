package com.project_open.mylyn.ui;

import org.eclipse.mylyn.tasks.ui.TaskRepositoryLocationUiFactory;
import org.eclipse.mylyn.tasks.ui.TasksUi;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.project_open.mylyn.core.ProjectOpenCorePlugin;

/**
 * The activator class controls the plug-in life cycle
 */
public class ProjectOpenUiPlugin extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "com.project_open.mylyn.ui"; //$NON-NLS-1$

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        ProjectOpenCorePlugin corePlugin = ProjectOpenCorePlugin.getDefault();

        assert corePlugin != null;
        corePlugin.getConnector().setTaskRepositoryLocationFactory(
                new TaskRepositoryLocationUiFactory());
        TasksUi.getRepositoryManager().addListener(corePlugin.getConnector().getClientManager());
    }


}