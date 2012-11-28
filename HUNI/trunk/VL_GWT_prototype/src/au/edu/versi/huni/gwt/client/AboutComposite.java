package au.edu.versi.huni.gwt.client;

import java.beans.Beans;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.LayoutPanel;

public class AboutComposite extends Composite implements ClickHandler {

	private static final String BUTTON_HEIGHT = "20px";

	private LayoutPanel layoutPanel;
	
	public AboutComposite()
	{
		layoutPanel = contentBuilder();
	     // All composites must call initWidget() in their constructors.
	      initWidget(layoutPanel);
	      layoutPanel.setSize("200px", "125px");

	      // Give the overall composite a style name.
	      setStyleName("huni-about-box");
	}

	protected LayoutPanel contentBuilder() {
		
		LayoutPanel contentLayoutPanel = new LayoutPanel();

		InlineLabel toolDetailInlineLabel = new InlineLabel("About");
		toolDetailInlineLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		toolDetailInlineLabel.setSize("100%", BUTTON_HEIGHT);
		contentLayoutPanel.add(toolDetailInlineLabel);
		
		// About HuNI

		AboutLabelComposite aboutHuniButton = new AboutLabelComposite("About HuNI", new AboutPopup());
		contentLayoutPanel.add(aboutHuniButton);
		contentLayoutPanel.setWidgetLeftWidth(aboutHuniButton, 0.0, Unit.PX, 200.0, Unit.PX);
		contentLayoutPanel.setWidgetTopHeight(aboutHuniButton, 27.0, Unit.PX, 20.0, Unit.PX);

		// Terms of use
		
		AboutLabelComposite termsOfUseButton = new AboutLabelComposite("Terms of use", new TermsOfUsePopup());
		contentLayoutPanel.add(termsOfUseButton);
		contentLayoutPanel.setWidgetLeftWidth(termsOfUseButton, 0.0, Unit.PX, 200.0, Unit.PX);
		contentLayoutPanel.setWidgetTopHeight(termsOfUseButton, 53.0, Unit.PX, 30.0, Unit.PX);

		// The HuNI partners
		
		AboutLabelComposite huniPartnersButton = new AboutLabelComposite("The HuNI partners", new PartnersPopup());
		contentLayoutPanel.add(huniPartnersButton);
		contentLayoutPanel.setWidgetLeftWidth(huniPartnersButton, 0.0, Unit.PX, 200.0, Unit.PX);
		contentLayoutPanel.setWidgetTopHeight(huniPartnersButton, 79.0, Unit.PX, 20.0, Unit.PX);

		// NeCTAR sponsorship
		
		AboutLabelComposite nectarSponsorshipButton = new AboutLabelComposite("NecTAR sponsorship", new SponsorshipPopup());
		contentLayoutPanel.add(nectarSponsorshipButton);		
		contentLayoutPanel.setWidgetLeftWidth(nectarSponsorshipButton, 0.0, Unit.PX, 200.0, Unit.PX);
		contentLayoutPanel.setWidgetTopHeight(nectarSponsorshipButton, 105.0, Unit.PX, 30.0, Unit.PX);

		return contentLayoutPanel;
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
