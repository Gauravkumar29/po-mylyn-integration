package com.project_open.mylyn.core;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class ProjectOpenCorePlugin extends Plugin {

	public static final String PLUGIN_ID = "com.project_open.mylyn.core";

	public static final String REPOSITORY_KIND = "projectopen";

	private static ProjectOpenCorePlugin plugin;

	private ProjectOpenRepositoryConnector connector;

	public static ProjectOpenCorePlugin getDefault() {
		return plugin;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		if (connector != null) {
			connector.stop();
			connector = null;
		}

		plugin = null;
		super.stop(context);
	}

	public ProjectOpenRepositoryConnector getConnector() {
		if (connector == null) {
			setConnector(new ProjectOpenRepositoryConnector());
		}

		return connector;
	}

	void setConnector(ProjectOpenRepositoryConnector connector) {
		this.connector = connector;
	}

	public IPath getRepostioryAttributeCachePath() {
		IPath stateLocation = Platform.getStateLocation(getBundle());
		IPath cacheFile = stateLocation.append("repositoryClientDataCache");
        return cacheFile;
    }

}
