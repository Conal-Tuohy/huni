package au.edu.versi.huni.gwt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DialogComposite extends Composite implements ClickHandler {

	private DialogBox dialogBox;
	private FocusWidget parentPanel;

	public DialogComposite(final Label errorLabel)
	{
		dialogBox = buildDialogBox(errorLabel);
	      // All composites must call initWidget() in their constructors.
	      initWidget(dialogBox);

	      // Give the overall composite a style name.
	      setStyleName("huni-dialog-box");
	}
	
	protected DialogBox buildDialogBox(final Label errorLabel) {
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
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				parentPanel.setEnabled(true);
				parentPanel.setFocus(true);
			}
		});
		return dialogBox;		
	}
	
	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub

	}

}
