package au.edu.versi.huni.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

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
	
	private VirtualLaboratoryComposite vlComposite;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		final Label errorLabel = configureErrorPanel();
		
		RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
		rootLayoutPanel.setSize("800px", "900px");
		
		vlComposite = new VirtualLaboratoryComposite();
		
		rootLayoutPanel.add(vlComposite);
		
	}

	protected Label configureErrorPanel() {
		final Label errorLabel = new Label();
		RootPanel rootPanel = RootPanel.get("errorLabelContainer");
		rootPanel.setSize("800px", "800px");
		rootPanel.add(errorLabel);
		return errorLabel;
	}
}
