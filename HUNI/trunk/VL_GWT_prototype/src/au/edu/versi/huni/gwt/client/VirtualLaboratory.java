package au.edu.versi.huni.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class VirtualLaboratory implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Laboratory service.
	 */
	private final VirtualLaboratoryServiceAsync laboratoryService = GWT
			.create(VirtualLaboratoryService.class);
	
	private HeaderComposite headerComposite;
	private ProjectsDatasetsComposite projectsDatasetsComposite;
	private ToolkitsToolsComposite toolkitsToolsComposite;
	private HistoryComposite historyComposite;
	private ToolComposite toolsDetailsComposite;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		//configureRootPanel();
		
		final Label errorLabel = configureErrorPanel();
		
		DockLayoutPanel mainLayoutPanel = pageBuilder();
		
		// Header
		
		headerComposite = new HeaderComposite();				
		headerComposite.setStylePrimaryName("headerLayoutPanel");
		mainLayoutPanel.addNorth(headerComposite, 15.2);
		headerComposite.setWidth("790px");
		
		// Left column and contents
				
		SplitLayoutPanel verticalSplitPanel = new SplitLayoutPanel();
		verticalSplitPanel.setSize("200px", "100%");		
		// Projects and data sets
		projectsDatasetsComposite = new ProjectsDatasetsComposite();;
		verticalSplitPanel.addNorth(projectsDatasetsComposite, 291.0);
		// Toolkits and tools
		toolkitsToolsComposite = new ToolkitsToolsComposite();		
		verticalSplitPanel.add(toolkitsToolsComposite);
		// Add to parent panel
		mainLayoutPanel.addWest(verticalSplitPanel, 16.5);
		
		// Right column and contents
		
		historyComposite = new HistoryComposite();
		mainLayoutPanel.addEast(historyComposite, 16.5);
		//mainLayoutPanel.setCellHorizontalAlignment(historyComposite, );
		
		// Middle column and contents

		toolsDetailsComposite = new ToolComposite();		
		//toolsDetailsPanel.addStyleName("columnLayoutPanel");
		mainLayoutPanel.add(toolsDetailsComposite);
	}

	protected void configureRootPanel() {
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel rootPanel = RootPanel.get("virtualLabContainer");
		rootPanel.setSize("800px", "900px");
		rootPanel.setStyleName("#gwtContainerBody");
		rootPanel.getElement().getStyle().setPosition(Position.RELATIVE);
	}

	protected Label configureErrorPanel() {
		final Label errorLabel = new Label();
		RootPanel rootPanel = RootPanel.get("errorLabelContainer");
		rootPanel.setSize("800px", "800px");
		rootPanel.add(errorLabel);
		return errorLabel;
	}

	protected DockLayoutPanel pageBuilder() {
		
		RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
		rootLayoutPanel.setSize("800px", "900px");
		
		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);
		dockLayoutPanel.setSize("800px", "800px");
		dockLayoutPanel.setStylePrimaryName("rootLayoutPanel");
		
		rootLayoutPanel.add(dockLayoutPanel);

		return dockLayoutPanel;
	}
}
