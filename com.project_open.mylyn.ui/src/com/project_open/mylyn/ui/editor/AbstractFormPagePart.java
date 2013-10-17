package com.project_open.mylyn.ui.editor;

import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.AbstractFormPart;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author Markus Knittig
 *
 */
public abstract class AbstractFormPagePart extends AbstractFormPart {

    protected LocalResourceManager resources;

    protected Font titleFont;

    protected Color attributeNameColor;

    public AbstractFormPagePart() {
        super();
        resources = new LocalResourceManager(JFaceResources.getResources());
        titleFont = resources.createFont(FontDescriptor.createFrom("Sans", 13, SWT.NORMAL));
        attributeNameColor = resources.createColor(new RGB(87, 80, 18));
    }

    @Override
    public void dispose() {
        super.dispose();
        resources.dispose();
    }

    public abstract Control createControl(Composite parent, FormToolkit toolkit);

}
