package au.edu.versi.huni.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.VerticalSplitPanel;

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
	
	private HeaderComposite2 headerComposite;
	private ProjectsDatasetsComposite projectsDatasetsComposite;
	private ToolkitsToolsComposite toolkitsToolsComposite;
	private HistoryComposite historyComposite;
	private ToolComposite toolsDetailsComposite;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		configureRootPanel();
		
//		final Label errorLabel = configureErrorPanel();
		
//		DockLayoutPanel mainLayoutPanel = pageBuilder();
		
		// Header
		
//		headerComposite = new HeaderComposite2();				
//		headerComposite.setStylePrimaryName("headerLayoutPanel");
//		mainLayoutPanel.addNorth(headerComposite, 15.2);
		
		// Left column and contents
				
//		VerticalSplitPanel verticalSplitPanel = new VerticalSplitPanel();
//		verticalSplitPanel.setSize("200px", "100%");		
//		mainLayoutPanel.addWest(verticalSplitPanel, 16.0);
		
//		projectsDatasetsComposite = new ProjectsDatasetsComposite();;
//		verticalSplitPanel.setTopWidget(projectsDatasetsComposite);

//		toolkitsToolsComposite = new ToolkitsToolsComposite();		
//		verticalSplitPanel.setBottomWidget(toolkitsToolsComposite);
		
		// Right column and contents
		
//		historyComposite = new HistoryComposite();
//		mainLayoutPanel.addEast(historyComposite, 16.0);
		
		// Middle column and contents

//		toolsDetailsComposite = new ToolComposite();		
//		//toolsDetailsPanel.addStyleName("columnLayoutPanel");
//		mainLayoutPanel.add(toolsDetailsComposite);
	}

	protected void configureRootPanel() {
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel rootPanel = RootPanel.get("nameFieldContainer");
		rootPanel.setSize("800px", "800px");
		rootPanel.setStyleName("#gwtContainerBody");
		rootPanel.getElement().getStyle().setPosition(Position.RELATIVE);
	}

	protected Label configureErrorPanel() {
		final Label errorLabel = new Label();
		RootPanel.get("errorLabelContainer").add(errorLabel);
		return errorLabel;
	}

	protected DockLayoutPanel pageBuilder() {
		
		RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
		//rootLayoutPanel.setStyleName((String) null);
		//rootLayoutPanel.setStylePrimaryName("rootLayoutPanel");
		
		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);
		dockLayoutPanel.setSize("800px", "800px");
		dockLayoutPanel.setStylePrimaryName("rootLayoutPanel");
		rootLayoutPanel.add(dockLayoutPanel);
		//rootPanel.add(dockLayoutPanel, 10, 10);

		return dockLayoutPanel;
	}
}
