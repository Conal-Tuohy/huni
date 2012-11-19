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
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
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

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		configureRootPanel();
		
		final Label errorLabel = configureErrorPanel();
		
		DockLayoutPanel mainLayoutPanel = pageBuilder();
		
		DockLayoutPanel headerPanel = headerBuilder();		
		mainLayoutPanel.addNorth(headerPanel, 10.0);
				
		VerticalSplitPanel verticalSplitPanel = new VerticalSplitPanel();
		verticalSplitPanel.setSize("200px", "100%");		
		mainLayoutPanel.addWest(verticalSplitPanel, 16.0);
		
		DockLayoutPanel projectsDatasourcesDockLayoutPanel = projectsDatasetsPanelBuilder();
		verticalSplitPanel.setTopWidget(projectsDatasourcesDockLayoutPanel);

		DockLayoutPanel toolKitsToolsDockLayoutPanel = toolkitsToolsPanelBuilder();		
		verticalSplitPanel.setBottomWidget(toolKitsToolsDockLayoutPanel);
		
		DockLayoutPanel historyPanel = historyPanelBuilder();
		mainLayoutPanel.addEast(historyPanel, 16.0);
		
		DockLayoutPanel toolsDetailsPanel = toolDetailsBuilder();		
		mainLayoutPanel.add(toolsDetailsPanel);

		buildDialogBox(errorLabel);
	}

	protected void configureRootPanel() {
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel rootPanel = RootPanel.get("nameFieldContainer");
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
		rootLayoutPanel.setStyleName((String) null);
		
		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);
		dockLayoutPanel.setSize("767px", "699px");
		rootLayoutPanel.add(dockLayoutPanel);
		//rootPanel.add(dockLayoutPanel, 10, 10);

		return dockLayoutPanel;
	}

	protected DockLayoutPanel headerBuilder() {
		
		DockLayoutPanel headerDockLayoutPanel = new DockLayoutPanel(Unit.EM);
		headerDockLayoutPanel.setSize("100%", "100%");
		
		LayoutPanel layoutPanel = new LayoutPanel();
		layoutPanel.setSize("767px", "36px");
		headerDockLayoutPanel.addNorth(layoutPanel, 2.2);
		
		InlineLabel searchInlineLabel = new InlineLabel("Search:");
		searchInlineLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		searchInlineLabel.setSize("200", "12");
		layoutPanel.add(searchInlineLabel);

		layoutPanel.setWidgetLeftWidth(searchInlineLabel, 12.0, Unit.PX, 45.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(searchInlineLabel, 3.0, Unit.PX, 18.0, Unit.PX);

		TextBox searchTextBox = new TextBox();
		searchTextBox.setTextAlignment(TextBoxBase.ALIGN_RIGHT);
		searchTextBox.setText("for dataset by name");
		searchTextBox.setName("searchForDataset");
		searchTextBox.setAlignment(TextAlignment.LEFT);
		searchTextBox.setSize("100", "12px");
		layoutPanel.add(searchTextBox);

		layoutPanel.setWidgetLeftWidth(searchTextBox, 57.0, Unit.PX, 173.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(searchTextBox, 0.0, Unit.PX, 24.0, Unit.PX);

		InlineHyperlink advanceSearchHyperlink = new InlineHyperlink("Advanced search", false, "newHistoryToken");
		layoutPanel.add(advanceSearchHyperlink);

		layoutPanel.setWidgetLeftWidth(advanceSearchHyperlink, 236.0, Unit.PX, 101.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(advanceSearchHyperlink, 3.0, Unit.PX, 18.0, Unit.PX);

		InlineHyperlink registerHyperlink = new InlineHyperlink("Register", false, "newHistoryToken");
		layoutPanel.add(registerHyperlink);
		
		layoutPanel.setWidgetLeftWidth(registerHyperlink, 599.0, Unit.PX, 48.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(registerHyperlink, 3.0, Unit.PX, 18.0, Unit.PX);

		InlineHyperlink loginHyperlink = new InlineHyperlink("Login/AAF login", false, "newHistoryToken");
		layoutPanel.add(loginHyperlink);

		layoutPanel.setWidgetLeftWidth(loginHyperlink, 664.0, Unit.PX, 92.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(loginHyperlink, 3.0, Unit.PX, 18.0, Unit.PX);

		InlineLabel virtualLaboratoryInlineLabel = new InlineLabel("Humanities Virtual Laboratory");
		virtualLaboratoryInlineLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		virtualLaboratoryInlineLabel.setStyleName("headerName");
		virtualLaboratoryInlineLabel.setSize("200", "50");
		
		headerDockLayoutPanel.addWest(virtualLaboratoryInlineLabel, 30.7);
		
		LayoutPanel logoContainerPanel = new LayoutPanel();		
		Image logoImage = new Image("resources/images/HuNI_Logo_ICON.jpg");
		logoImage.setAltText("HuNI Logo");
		logoImage.setSize("150px", "91px");
		//image.getElement().getStyle().setPosition(Position.RELATIVE);
		logoContainerPanel.add(logoImage);

		logoContainerPanel.setWidgetLeftWidth(logoImage, 12.0, Unit.PX, 150.0, Unit.PX);
		logoContainerPanel.setWidgetTopHeight(logoImage, 0.0, Unit.PX, 91.0, Unit.PX);

		headerDockLayoutPanel.addEast(logoContainerPanel, 12.5);

		return headerDockLayoutPanel;
	}

	protected DockLayoutPanel projectsDatasetsPanelBuilder() {
		
		// The outermost container
		DockLayoutPanel projectsDatasourcesDockLayoutPanel = new DockLayoutPanel(Unit.EM);
		projectsDatasourcesDockLayoutPanel.setSize("100%", "100%");

		// The container label
		InlineLabel projectsDatasetsInlinelabel = new InlineLabel("Projects/Datasets");
		projectsDatasetsInlinelabel.setSize("100%", "20px");
		projectsDatasourcesDockLayoutPanel.addNorth(projectsDatasetsInlinelabel, 1.7);

		// The button container populated with buttons
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setWidth("75%");		
		Button newButton = new Button("New");
		horizontalPanel.add(newButton);		
		Button editButton = new Button("Edit");
		horizontalPanel.add(editButton);		
		Button deleteButton = new Button("Delete");
		horizontalPanel.add(deleteButton);		
		projectsDatasourcesDockLayoutPanel.addSouth(horizontalPanel, 2.8);		
		
		// The tree widget container. Decorate outline plus tree widget.
		DecoratorPanel projectsDatasourcesDecoratorPanel = new DecoratorPanel();
		projectsDatasourcesDecoratorPanel.setSize("95%", "95%");		
		Tree projectsDatasourcesTree = new Tree();
		projectsDatasourcesDecoratorPanel.setWidget(projectsDatasourcesTree);
		projectsDatasourcesTree.setSize("100%", "190px");
		projectsDatasourcesDockLayoutPanel.add(projectsDatasourcesDecoratorPanel);
		
		TreeItem rootItem = new TreeItem();
		rootItem.addTextItem("Project 0");
		rootItem.addTextItem("Project 1");
		rootItem.addTextItem("Project 2");

		projectsDatasourcesTree.addItem(rootItem);
		
		return projectsDatasourcesDockLayoutPanel;
	}

	protected DockLayoutPanel toolkitsToolsPanelBuilder() {
		
		DockLayoutPanel toolKitsToolsDockLayoutPanel = new DockLayoutPanel(Unit.EM);
		toolKitsToolsDockLayoutPanel.setSize("100%", "100%");
		
		// The container label
		InlineLabel toolkitsToolsInlineLabel = new InlineLabel("Toolkits/Tools");
		toolkitsToolsInlineLabel.setSize("100%", "20px");
		toolKitsToolsDockLayoutPanel.addNorth(toolkitsToolsInlineLabel, 1.9);
		
		// The button container populated with buttons
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setWidth("75%");		
		Button newButton = new Button("New");
		horizontalPanel.add(newButton);		
		Button editButton = new Button("Edit");
		horizontalPanel.add(editButton);		
		Button deleteButton = new Button("Delete");
		horizontalPanel.add(deleteButton);
		toolKitsToolsDockLayoutPanel.addSouth(horizontalPanel, 3.1);

		// The tree widget container. Decorate outline plus tree widget.
		DecoratorPanel toolKitsToolsDecoratorPanel = new DecoratorPanel();
		toolKitsToolsDecoratorPanel.setSize("95%", "95%");		
		Tree toolKitsToolsTree = new Tree();
		toolKitsToolsDecoratorPanel.setWidget(toolKitsToolsTree);
		toolKitsToolsTree.setSize("100%", "190px");
		toolKitsToolsDockLayoutPanel.add(toolKitsToolsDecoratorPanel);

		return toolKitsToolsDockLayoutPanel;
	}

	protected DockLayoutPanel historyPanelBuilder() {
		
		DockLayoutPanel historyDockLayoutPanel = new DockLayoutPanel(Unit.EM);
		historyDockLayoutPanel.setSize("200px", "100%");
		
		InlineLabel historyInlineLable = new InlineLabel("History");
		historyInlineLable.setSize("100%", "20px");
		historyDockLayoutPanel.addNorth(historyInlineLable, 1.9);
		
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.setWidth("75%");		
		Button newButton = new Button("New");
		buttonPanel.add(newButton);		
		Button editButton = new Button("Edit");
		buttonPanel.add(editButton);		
		Button deleteButton = new Button("Delete");
		buttonPanel.add(deleteButton);
		historyDockLayoutPanel.addSouth(buttonPanel, 2.9);
		
		DecoratorPanel decoratorPanel = new DecoratorPanel();
		decoratorPanel.setSize("100%", "98%");		
		Tree historytTree = new Tree();
		decoratorPanel.setWidget(historytTree);
		historytTree.setSize("100%", "485px");
		historyDockLayoutPanel.add(decoratorPanel);
		
		return historyDockLayoutPanel;
	}

	protected DockLayoutPanel toolDetailsBuilder() {
		DockLayoutPanel toolDetailsPanel = new DockLayoutPanel(Unit.EM);
		toolDetailsPanel.setSize("98%", "100%");
		
		InlineLabel toolDetailInlineLabel = new InlineLabel("Tool");
		toolDetailInlineLabel.setSize("100%", "20px");
		toolDetailsPanel.addNorth(toolDetailInlineLabel, 1.8);
		
		Frame frame = new Frame("http://www.google.com");
		frame.setSize("98%", "98%");
		toolDetailsPanel.add(frame);
		
		return toolDetailsPanel;
	}

	protected void buildDialogBox(final Label errorLabel) {
		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
//		closeButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				dialogBox.hide();
//				sendButton.setEnabled(true);
//				sendButton.setFocus(true);
//			}
//		});

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
//				String textToServer = nameField.getText();
//				if (!FieldVerifier.isValidName(textToServer)) {
//					errorLabel.setText("Please enter at least four characters");
//					return;
//				}

				// Then, we send the input to the server.
//				sendButton.setEnabled(false);
//				textToServerLabel.setText(textToServer);
//				serverResponseLabel.setText("");
//				laboratoryService.greetServer(textToServer,
//						new AsyncCallback<String>() {
//							public void onFailure(Throwable caught) {
//								// Show the RPC error message to the user
//								dialogBox
//										.setText("Remote Procedure Call - Failure");
//								serverResponseLabel
//										.addStyleName("serverResponseLabelError");
//								serverResponseLabel.setHTML(SERVER_ERROR);
//								dialogBox.center();
//								closeButton.setFocus(true);
//							}
//
//							public void onSuccess(String result) {
//								dialogBox.setText("Remote Procedure Call");
//								serverResponseLabel
//										.removeStyleName("serverResponseLabelError");
//								serverResponseLabel.setHTML(result);
//								dialogBox.center();
//								closeButton.setFocus(true);
//							}
//						});
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
	}
}
