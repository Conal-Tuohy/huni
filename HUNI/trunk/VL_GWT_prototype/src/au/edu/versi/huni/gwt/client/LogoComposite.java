package au.edu.versi.huni.gwt.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;

public class LogoComposite extends ResizeComposite {

	private LayoutPanel wrapperPanel;
	private String LOGO_WIDTH = "210px";
	private String LOGO_HEIGHT = "80px";
	
	private String COMPOSITE_DEPTH = LOGO_HEIGHT;
	private String COMPOSITE_INITIAL_DESIGN_WIDTH = "800px";

	public LogoComposite() {
		wrapperPanel = contentBuilder();
		initWidget(wrapperPanel);
		setStyleName("huni-vl-logo");
		setSize(COMPOSITE_INITIAL_DESIGN_WIDTH, COMPOSITE_DEPTH);
	}

	protected LayoutPanel contentBuilder() {

		LayoutPanel logoContainerPanel = new LayoutPanel();

		Image logoImage = new Image("resources/images/huni-logo-21.png");
		logoImage.setAltText("HuNI Logo");
		logoImage.setSize(LOGO_WIDTH, LOGO_HEIGHT);

		logoContainerPanel.add(logoImage);
		logoContainerPanel.setWidgetLeftWidth(logoImage, 0.0, Unit.PX, 210.0, Unit.PX);
		logoContainerPanel.setWidgetTopHeight(logoImage, 0.0, Unit.PX, 80.0, Unit.PX);

		return logoContainerPanel;
	}
}
