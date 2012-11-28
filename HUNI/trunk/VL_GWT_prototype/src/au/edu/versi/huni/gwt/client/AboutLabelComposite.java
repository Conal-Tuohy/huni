package au.edu.versi.huni.gwt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class AboutLabelComposite extends Composite{
	
	private static final String BUTTON_HEIGHT = "20px";

	public AboutLabelComposite(final String labelText, final PopupPanel popupPanel)
	{
		Label contentLabel = new Label(labelText);
		contentLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		contentLabel.setStylePrimaryName("huni-about-label");
		contentLabel.setSize("100%", BUTTON_HEIGHT);
		
		contentLabel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(final ClickEvent event) {
				popupPanel.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
		          public void setPosition(int offsetWidth, int offsetHeight) {
		            int left = event.getClientX();
		            int top = event.getClientY() + offsetHeight / 2;
		            popupPanel.setPopupPosition(left, top);
		          }
		        });
			}

	    });
	    initWidget(contentLabel);
	}
}
