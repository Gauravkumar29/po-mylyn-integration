package com.project_open.mylyn.ui.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.project_open.mylyn.core.client.ProjectOpenClientData;
import com.project_open.mylyn.core.model.Ticket;

/**
 * @author Markus Knittig
 *
 */
public class NewTicketPage extends WizardPage {

    private static final String TITLE = "New Review Request";

    private static final String DESCRIPTION = "Enter data for new review request.";

    private Combo repositoryCombo;
    private Text changeNumText;

    private ProjectOpenClientData clientData;

    public NewTicketPage(ProjectOpenClientData clientData) {
        super(TITLE);
        this.clientData = clientData;

        setTitle(TITLE);
        setDescription(DESCRIPTION);
    }

	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		
	}
	
    public Ticket getTicket() {
    	// TODO Auto-generated method stub
    	return null;
    }

}
