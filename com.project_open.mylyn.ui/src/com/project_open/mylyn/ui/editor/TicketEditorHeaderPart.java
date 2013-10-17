package com.project_open.mylyn.ui.editor;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.ui.editors.TaskEditor;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author Markus Knittig
 *
 */
public class TicketEditorHeaderPart extends AbstractFormPagePart {

    private TaskEditor editor;
    private Composite parentComposite;
    private FormToolkit toolkit;

    private Text summaryText;
    private Label lblUpdatedWeek;

    public TicketEditorHeaderPart(TaskEditor editor) {
        this.editor = editor;
    }

    private ITask getTask() {
        return editor.getTaskEditorInput().getTask();
    }

    @Override
    public Control createControl(Composite parent, FormToolkit toolkit) {
        this.toolkit = toolkit;

        parentComposite = toolkit.createComposite(parent);
        toolkit.paintBordersFor(parentComposite);
        GridLayoutFactory.fillDefaults().numColumns(2).applyTo(parentComposite);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(parentComposite);

        createSummaryLabel();
        createSummaryText();
        createLastUpdatedLabel();

        Label label = toolkit.createSeparator(parent, SWT.HORIZONTAL);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(label);

        return parentComposite;
    }

    private void createSummaryLabel() {
        Label lblSummary = toolkit.createLabel(parentComposite, "Summary:");
        GridDataFactory.fillDefaults().align(SWT.RIGHT, SWT.CENTER).applyTo(lblSummary);
        lblSummary.setFont(titleFont);
        lblSummary.setForeground(attributeNameColor);
    }

    private void createSummaryText() {
        summaryText = toolkit.createText(parentComposite, getTask().getSummary());
        GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.CENTER).hint(600, 15).applyTo(
                summaryText);
        summaryText.setFont(titleFont);
    }

    private void createLastUpdatedLabel() {
        lblUpdatedWeek = toolkit.createLabel(parentComposite, NLS.bind("Updated on {0}", getTask()
                .getModificationDate()));
        GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.CENTER).span(2, 1).applyTo(
                lblUpdatedWeek);
        lblUpdatedWeek.setForeground(attributeNameColor);
    }

    public String getSummary() {
        return summaryText.getText();
    }

    public void setSummary(String summary) {
        summaryText.setText(summary);
    }

}
