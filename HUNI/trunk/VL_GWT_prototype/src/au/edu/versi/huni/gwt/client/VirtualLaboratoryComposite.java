package au.edu.versi.huni.gwt.client;

import java.beans.Beans;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public class VirtualLaboratoryComposite extends ResizeComposite implements
		ClickHandler {

	private DockLayoutPanel vlDockLayoutPanel;
	private HeaderComposite headerComposite;
	private HomeComposite homeComposite;
	private WorkspaceComposite workspaceComposite;
	private FooterComposite footerComposite;

	public VirtualLaboratoryComposite() {
		vlDockLayoutPanel = contentBuilder();
		// All composites must call initWidget() in their constructors.
		initWidget(vlDockLayoutPanel);

		if (!isDesignTime()) {
			vlDockLayoutPanel.setSize("100%", "100%");
		}
	}

	protected DockLayoutPanel contentBuilder() {
		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);
		dockLayoutPanel.setSize("1000px", "1200px");
		dockLayoutPanel.setStylePrimaryName("huni-vl-root-layout");

		// Header

		headerComposite = new HeaderComposite();
		dockLayoutPanel.addNorth(headerComposite, 15.2);
		headerComposite.setWidth("790px");

		// Footer

		footerComposite = new FooterComposite();
		dockLayoutPanel.addSouth(footerComposite, 20.0);
		footerComposite.setSize("790px", "140px");

		// Body

		TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(1.5, Unit.EM);
		dockLayoutPanel.add(tabLayoutPanel);
		tabLayoutPanel.setSize("790px", "650px");

		homeComposite = new HomeComposite();
		homeComposite.setSize("780px", "900px");
		tabLayoutPanel.add(homeComposite, "Home", false);

		workspaceComposite = new WorkspaceComposite();
		workspaceComposite.setSize("780px", "900px");
		tabLayoutPanel.add(workspaceComposite, "Workspace", false);

		return dockLayoutPanel;
	}

	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub

	}

	// Implement the following method exactly as-is
	private static final boolean isDesignTime() {
		return Beans.isDesignTime(); // GWT 2.4 and above
	}

}
