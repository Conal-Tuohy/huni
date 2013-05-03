package au.edu.versi.huni.gwt.client.view;

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

	public AboutComposite() {
		layoutPanel = contentBuilder();
		// All composites must call initWidget() in their constructors.
		initWidget(layoutPanel);
		setStylePrimaryName("huni-about-box");
	}

	protected LayoutPanel contentBuilder() {

		LayoutPanel contentLayoutPanel = new LayoutPanel();
		contentLayoutPanel.setSize("200px", "106px");

		InlineLabel toolDetailInlineLabel = new InlineLabel("About");
		toolDetailInlineLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		toolDetailInlineLabel.setSize("100%", BUTTON_HEIGHT);
		toolDetailInlineLabel.setStylePrimaryName("huni-about-box-title");
		contentLayoutPanel.add(toolDetailInlineLabel);
		contentLayoutPanel.setWidgetTopHeight(toolDetailInlineLabel, 0.0, Unit.PX, 16.0, Unit.PX);

		// About HuNI

		AboutLabelComposite aboutHuniButton = new AboutLabelComposite("About HuNI", new AboutPopup());
		contentLayoutPanel.add(aboutHuniButton);
		contentLayoutPanel.setWidgetLeftWidth(aboutHuniButton, 0.0, Unit.PX, 189.0, Unit.PX);
		contentLayoutPanel.setWidgetTopHeight(aboutHuniButton, 17.0, Unit.PX, 16.0, Unit.PX);

		// Terms of use

		AboutLabelComposite termsOfUseButton = new AboutLabelComposite("Terms of use", new TermsOfUsePopup());
		contentLayoutPanel.add(termsOfUseButton);
		contentLayoutPanel.setWidgetLeftWidth(termsOfUseButton, 0.0, Unit.PX, 193.0, Unit.PX);
		contentLayoutPanel.setWidgetTopHeight(termsOfUseButton, 39.0, Unit.PX, 16.0, Unit.PX);

		// The HuNI partners

		AboutLabelComposite huniPartnersButton = new AboutLabelComposite("The HuNI partners", new PartnersPopup());
		contentLayoutPanel.add(huniPartnersButton);
		contentLayoutPanel.setWidgetLeftWidth(huniPartnersButton, 0.0, Unit.PX, 189.0, Unit.PX);
		contentLayoutPanel.setWidgetTopHeight(huniPartnersButton, 61.0, Unit.PX, 16.0, Unit.PX);

		// NeCTAR sponsorship

		AboutLabelComposite nectarSponsorshipButton = new AboutLabelComposite("NecTAR sponsorship", new SponsorshipPopup());
		contentLayoutPanel.add(nectarSponsorshipButton);
		contentLayoutPanel.setWidgetLeftWidth(nectarSponsorshipButton, 0.0, Unit.PX, 189.0, Unit.PX);
		contentLayoutPanel.setWidgetTopHeight(nectarSponsorshipButton, 83.0, Unit.PX, 16.0, Unit.PX);

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
