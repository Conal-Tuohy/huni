package au.edu.versi.huni.gwt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Label;

// Create a handler for the sendButton and nameField
class MyHandler implements ClickHandler, KeyUpHandler {

	private Label errorLabel;

	public void onClick(ClickEvent event) {
		sendNameToServer();
	}

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
//		String textToServer = nameField.getText();
//		if (!FieldVerifier.isValidName(textToServer)) {
//			errorLabel.setText("Please enter at least four characters");
//			return;
//		}

		// Then, we send the input to the server.
//		sendButton.setEnabled(false);
//		textToServerLabel.setText(textToServer);
//		serverResponseLabel.setText("");
//		laboratoryService.greetServer(textToServer,
//				new AsyncCallback<String>() {
//					public void onFailure(Throwable caught) {
//						// Show the RPC error message to the user
//						dialogBox
//								.setText("Remote Procedure Call - Failure");
//						serverResponseLabel
//								.addStyleName("serverResponseLabelError");
//						serverResponseLabel.setHTML(SERVER_ERROR);
//						dialogBox.center();
//						closeButton.setFocus(true);
//					}
//
//					public void onSuccess(String result) {
//						dialogBox.setText("Remote Procedure Call");
//						serverResponseLabel
//								.removeStyleName("serverResponseLabelError");
//						serverResponseLabel.setHTML(result);
//						dialogBox.center();
//						closeButton.setFocus(true);
//					}
//				});
	}
}
