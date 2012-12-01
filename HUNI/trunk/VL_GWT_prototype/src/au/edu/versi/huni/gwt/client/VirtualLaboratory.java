package au.edu.versi.huni.gwt.client;

import au.edu.versi.huni.gwt.client.view.VirtualLaboratoryComposite;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class VirtualLaboratory implements EntryPoint {

	private final VirtualLaboratoryServiceAsync laboratoryService = GWT
			.create(VirtualLaboratoryService.class);

	private VirtualLaboratoryComposite vlComposite;

	public void onModuleLoad() {
		
//	    ContactsServiceAsync rpcService = GWT.create(ContactsService.class);
//	    HandlerManager eventBus = new HandlerManager(null);
//	    AppController appViewer = new AppController(rpcService, eventBus);
//	    appViewer.go(RootPanel.get());


		RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
		
		// Style sets page area to match what is set for VirtualLaboratoryComposite.
		// The page area is quite large with a fixed vertical extent and vertical scrolling 
		// and a 100% page fill for the width.
		rootLayoutPanel.setStyleName("huni-vl-application-container");

		vlComposite = new VirtualLaboratoryComposite();
		vlComposite.setWidth("100%");
		vlComposite.setHeight("1000px");
		rootLayoutPanel.add(vlComposite);
	}

	protected Label configureErrorPanel() {
		final Label errorLabel = new Label();
		RootPanel rootPanel = RootPanel.get("errorLabelContainer");
		rootPanel.add(errorLabel);
		return errorLabel;
	}
}
