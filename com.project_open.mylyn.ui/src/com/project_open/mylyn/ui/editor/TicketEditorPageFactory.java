package com.project_open.mylyn.ui.editor;

import org.eclipse.mylyn.tasks.ui.TasksUiUtil;
import org.eclipse.mylyn.tasks.ui.editors.AbstractTaskEditorPageFactory;
import org.eclipse.mylyn.tasks.ui.editors.TaskEditor;
import org.eclipse.mylyn.tasks.ui.editors.TaskEditorInput;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.forms.editor.IFormPage;

import com.project_open.mylyn.core.ProjectOpenCorePlugin;

/**
 * @author Markus Knittig
 *
 */
public class TicketEditorPageFactory extends AbstractTaskEditorPageFactory {

    private static final String TITLE = "Ticket";

    @Override
    public boolean canCreatePageFor(TaskEditorInput input) {
        if (input.getTask().getConnectorKind().equals(ProjectOpenCorePlugin.REPOSITORY_KIND)) {
            return true;
        } else if (TasksUiUtil.isOutgoingNewTask(input.getTask(),
                ProjectOpenCorePlugin.REPOSITORY_KIND)) {
            return true;
        }
        return false;
    }

    @Override
    public int getPriority() {
        return PRIORITY_TASK;
    }

    @Override
    public Image getPageImage() {
        return null;
    }

    @Override
    public String getPageText() {
        return TITLE;
    }

    @Override
    public IFormPage createPage(TaskEditor parentEditor) {
        return new TicketEditorPage(parentEditor, TITLE);
    }

}
