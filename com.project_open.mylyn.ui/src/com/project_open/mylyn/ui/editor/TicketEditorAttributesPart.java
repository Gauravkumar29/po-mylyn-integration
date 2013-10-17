package com.project_open.mylyn.ui.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.project_open.mylyn.core.client.ProjectOpenClient;
import com.project_open.mylyn.core.exception.ProjectOpenException;
import com.project_open.mylyn.core.model.Ticket;

/**
 * @author Markus Knittig
 *
 */
public class TicketEditorAttributesPart extends AbstractFormPagePart {

    private Composite parentComposite;
    private FormToolkit toolkit;

    private Label sumbitterLabel;
    private Label reviewersLabel;
    private Text branchText;
    private Text groupsText;
    private Text bugsText;
    private Text peopleText;
    private Label changeNumLabel;
    private Label repositoryLabel;
    private Text descriptionText;
    private Text testingDoneText;

    private Ticket reviewRequest;
    private ProjectOpenClient client;
    private TicketEditorHeaderPart headerPart;

    public TicketEditorAttributesPart(Ticket reviewRequest, ProjectOpenClient client,
            TicketEditorHeaderPart headerPart) {
        this.reviewRequest = reviewRequest;
        this.client = client;
        this.headerPart = headerPart;
    }

    @Override
    public Control createControl(Composite parent, FormToolkit toolkit) {
        this.toolkit = toolkit;

        ExpandableComposite expandableComposite = toolkit.createExpandableComposite(parent,
                ExpandableComposite.TWISTIE);
        expandableComposite.setText("Attributes");
        GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).applyTo(expandableComposite);

        parentComposite = new Composite(expandableComposite, SWT.NONE);
        toolkit.adapt(parentComposite);
        toolkit.paintBordersFor(parentComposite);
        GridLayoutFactory.fillDefaults().numColumns(4).applyTo(parentComposite);

        expandableComposite.setClient(parentComposite);
        expandableComposite.setExpanded(true);

        createAttributeName("Submitter:");
        sumbitterLabel = createLabelAttribute();

        createAttributeName("Reviewers").setForeground(
                parentComposite.getDisplay().getSystemColor(SWT.COLOR_BLACK));
        reviewersLabel = createLabelAttribute();

        createAttributeName("Branch:");
        branchText = createTextAttribute();

        createAttributeName("Groups");
        groupsText = createTextAttribute();

        createAttributeName("Bugs closed:");
        bugsText = createTextAttribute();

        createAttributeName("People:");
        peopleText = createTextAttribute();

        createAttributeName("Change number:");
        changeNumLabel = createLabelAttribute();

        createAttributeName("Repository:");
        repositoryLabel = createLabelAttribute();

        descriptionText = createMultiTextAttribute("Description:");
        testingDoneText = createMultiTextAttribute("Testing done:");

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

        setInput(reviewRequest);

        return expandableComposite;
    }

    private void updateTicket() {
        new Job("Update Review Request") {
            @Override
            protected IStatus run(IProgressMonitor monitor) {
                try {
                    Ticket reviewRequest = getInput();
                    client.updateTicket(reviewRequest, monitor);
                    setInput(reviewRequest);
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

        Text text = toolkit.createText(parentComposite, "", SWT.MULTI | SWT.WRAP
                | SWT.V_SCROLL);
        GridDataFactory.swtDefaults().span(4, 1).hint(700, 100).applyTo(text);

        return text;
    }

    private Label createAttributeName(String name) {
        Label label = toolkit.createLabel(parentComposite, name);
        GridDataFactory.fillDefaults().align(SWT.RIGHT, SWT.CENTER).applyTo(label);
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

    public void setInput(Ticket reviewRequest) {
        /*sumbitterLabel.setText(reviewRequest.getSubmitter().getUsername());
        branchText.setText(reviewRequest.getBranch());

        bugsText.setText(reviewRequest.getBugsClosedText());
        groupsText.setText(reviewRequest.getTargetGroupsText());
        peopleText.setText(reviewRequest.getTargetPeopleText());
        changeNumLabel.setText(reviewRequest.getChangeNumberText());
        repositoryLabel.setText(reviewRequest.getRepository().getName());
        descriptionText.setText(reviewRequest.getDescription());
        testingDoneText.setText(reviewRequest.getTestingDone());

        headerPart.setSummary(reviewRequest.getSummary());*/
    }

    public Ticket getInput() {
        /*Ticket reviewRequest = ProjectOpenUtil.cloneEntity(this.reviewRequest);

        reviewRequest.setBugsClosedText(bugsText.getText());
        reviewRequest.setTargetGroups(client.getClientData().marshallTargetGroups(
                groupsText.getText()));
        reviewRequest.setTargetPeople(client.getClientData().marshallTargetPeople(
                peopleText.getText()));

        reviewRequest.setBranch(branchText.getText());
        reviewRequest.setDescription(descriptionText.getText());
        reviewRequest.setTestingDone(testingDoneText.getText());

        reviewRequest.setSummary(headerPart.getSummary());

        return reviewRequest;*/
    	return null;
    }

}
