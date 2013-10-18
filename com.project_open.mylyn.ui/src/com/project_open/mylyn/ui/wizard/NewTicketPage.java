package com.project_open.mylyn.ui.wizard;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.project_open.mylyn.core.client.ProjectOpenClientData;
import com.project_open.mylyn.core.model.ProjectOpenTracker;
import com.project_open.mylyn.core.model.Ticket;
import com.project_open.mylyn.core.util.ProjectOpenUtil;

/**
 * @author Markus Knittig
 *
 */
public class NewTicketPage extends WizardPage {

    private static final String TITLE = "New Ticket";

    private static final String DESCRIPTION = "Enter data for new ticket.";

    private Combo trackerCombo;
    private Text titleText;

    private ProjectOpenClientData clientData;

    public NewTicketPage(ProjectOpenClientData clientData) {
        super(TITLE);
        this.clientData = clientData;

        setTitle(TITLE);
        setDescription(DESCRIPTION);
    }

    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NULL);
        GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);
        setControl(composite);

        GridDataFactory gridDataFactory = GridDataFactory.fillDefaults()
                .align(SWT.FILL, SWT.CENTER).grab(true, false).copy();

        final Label label = new Label(composite, SWT.NONE);
        label.setText("Tracker:");

        final ComboViewer comboViewer = new ComboViewer(composite, SWT.BORDER | SWT.READ_ONLY);
        comboViewer.setContentProvider(new ArrayContentProvider());
        trackerCombo = comboViewer.getCombo();
        gridDataFactory.applyTo(trackerCombo);
        comboViewer.setInput(ProjectOpenUtil.toStringList(clientData.getTrackers()));
        trackerCombo.addListener(SWT.Modify, new Listener() {
            public void handleEvent(Event event) {
                getContainer().updateButtons();
            }
        });

        final Label titleLabel = new Label(composite, SWT.NONE);
        titleLabel.setText("Title:");

        titleText = new Text(composite, SWT.BORDER);
        gridDataFactory.applyTo(titleText);
    }

    @Override
    public boolean isPageComplete() {
        return trackerCombo.getSelectionIndex() >= 0;
    }
	
    public Ticket getTicket() {
        Ticket ticket = new Ticket();
        ticket.setTrackerId(getSelectedTracker().getId());
        ticket.setName(titleText.getText());
        return ticket;    
    }
    
    private ProjectOpenTracker getSelectedTracker() {
        if (trackerCombo.getSelectionIndex() == -1) {
            return null;
        }
        return clientData.getTrackers().get(trackerCombo.getSelectionIndex());
    }

}
