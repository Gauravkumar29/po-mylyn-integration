package com.project_open.mylyn.ui.editor;

import java.util.Arrays;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.progress.UIJob;

import com.project_open.mylyn.core.client.ProjectOpenClient;
import com.project_open.mylyn.core.exception.ProjectOpenException;
import com.project_open.mylyn.core.model.Ticket;
import com.project_open.mylyn.core.util.ProjectOpenUtil;

/**
 * @author Markus Knittig
 * 
 */
public class TicketEditorAttributesPart extends AbstractFormPagePart {

	private Composite parentComposite;
	private FormToolkit toolkit;

	private Text trackerText;
	private Text descriptionText;
    private Combo statusCombo;

	private Ticket ticket;
	private ProjectOpenClient client;
	private TicketEditorHeaderPart headerPart;

	public TicketEditorAttributesPart(Ticket ticket, ProjectOpenClient client,
			TicketEditorHeaderPart headerPart) {
		this.ticket = ticket;
		this.client = client;
		this.headerPart = headerPart;
	}

	@Override
	public Control createControl(Composite parent, FormToolkit toolkit) {
		this.toolkit = toolkit;

		ExpandableComposite expandableComposite = toolkit
				.createExpandableComposite(parent, ExpandableComposite.TWISTIE);
		expandableComposite.setText("Attributes");
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER)
				.applyTo(expandableComposite);

		parentComposite = new Composite(expandableComposite, SWT.NONE);
		toolkit.adapt(parentComposite);
		toolkit.paintBordersFor(parentComposite);
		GridLayoutFactory.fillDefaults().numColumns(4).applyTo(parentComposite);

		expandableComposite.setClient(parentComposite);
		expandableComposite.setExpanded(true);
		
        createAttributeName("Tracker:");
        trackerText = createTextAttribute();
        
        createAttributeName("Status:");

        final ComboViewer comboViewer = new ComboViewer(parentComposite, SWT.BORDER );
        comboViewer.setContentProvider(new ArrayContentProvider());
        statusCombo = comboViewer.getCombo();
        GridDataFactory.fillDefaults().grab(true, false).applyTo(statusCombo);
        comboViewer.setInput(ProjectOpenUtil.toStringList(client.getClientData().getStates()));

		descriptionText = createMultiTextAttribute("Description:");

		Button updateAttributesButton = toolkit.createButton(parentComposite,
				"Update attributes", SWT.NONE);
		updateAttributesButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateTicket();
			}
		});
		new Label(expandableComposite, SWT.NONE);
		new Label(expandableComposite, SWT.NONE);
		new Label(expandableComposite, SWT.NONE);

		setInput(ticket);

		return expandableComposite;
	}

	private void updateTicket() {
		new UIJob("Update Ticket") {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				try {
					Ticket ticket = getInput();
					client.updateTicket(ticket, monitor);
					setInput(ticket);
				} catch (ProjectOpenException e) {
					throw new RuntimeException(e);
				}

				return Status.OK_STATUS;
			}
		}.schedule();
	}

	private Text createMultiTextAttribute(String name) {
		Label descriptionLabel = createAttributeName(name);
		GridDataFactory.fillDefaults().span(4, 1).applyTo(descriptionLabel);

		Text text = toolkit.createText(parentComposite, "", SWT.MULTI
				| SWT.WRAP | SWT.V_SCROLL);
		GridDataFactory.swtDefaults().span(4, 1).hint(700, 100).applyTo(text);

		return text;
	}

	private Label createAttributeName(String name) {
		Label label = toolkit.createLabel(parentComposite, name);
		GridDataFactory.fillDefaults().align(SWT.RIGHT, SWT.CENTER)
				.applyTo(label);
		label.setForeground(attributeNameColor);
		return label;
	}

	private Label createLabelAttribute() {
		return toolkit.createLabel(parentComposite, "");
	}

	private Text createTextAttribute() {
		Text text = toolkit.createText(parentComposite, "");
		GridDataFactory.fillDefaults().grab(true, false).applyTo(text);
		return text;
	}

	public synchronized void setInput(Ticket ticket) {
		trackerText.setText(ticket.getTracker().getName());
		descriptionText.setText(ticket.getDescription());
		headerPart.setSummary(ticket.getName());
	}

	public synchronized Ticket getInput() {
		Ticket ticket = ProjectOpenUtil.cloneEntity(this.ticket);

		ticket.setDescription(descriptionText.getText());
		ticket.setName(headerPart.getSummary());

		return ticket;
	}

}
