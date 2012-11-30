package au.edu.versi.huni.gwt.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public class VirtualLaboratoryComposite extends ResizeComposite {
	
	public static String COMPOSITE_INITIAL_DESIGN_WIDTH = "1000px";
	public static String COMPOSITE_DEPTH = "1200px";

	private DockLayoutPanel wrapperPanel;
	private HeaderComposite headerComposite;
	private HomeComposite homeComposite;
	private WorkspaceComposite workspaceComposite;
	private FooterComposite footerComposite;

	public VirtualLaboratoryComposite() {
		wrapperPanel = contentBuilder();
		initWidget(wrapperPanel);
		setStyleName("huni-vl-root-layout");
		setSize(COMPOSITE_INITIAL_DESIGN_WIDTH, COMPOSITE_DEPTH);
	}

	protected DockLayoutPanel contentBuilder() {
		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);

		// Header

		headerComposite = new HeaderComposite();
		headerComposite.setWidth("100%");
		dockLayoutPanel.addNorth(headerComposite, 15.2);

		// Footer

		footerComposite = new FooterComposite();
		footerComposite.setWidth("100%");
		dockLayoutPanel.addSouth(footerComposite, 10.0);

		// Body

		TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(2.0, Unit.EM);
		tabLayoutPanel.setWidth("100%");
		tabLayoutPanel.setHeight("100%");

		homeComposite = new HomeComposite();
		homeComposite.setWidth("100%");
		tabLayoutPanel.setHeight("100%");
		tabLayoutPanel.add(homeComposite, "Home", false);

		workspaceComposite = new WorkspaceComposite();
		workspaceComposite.setWidth("100%");
		tabLayoutPanel.setHeight("100%");
		tabLayoutPanel.add(workspaceComposite, "Workspace", false);

		dockLayoutPanel.add(tabLayoutPanel);

		return dockLayoutPanel;
	}
}
