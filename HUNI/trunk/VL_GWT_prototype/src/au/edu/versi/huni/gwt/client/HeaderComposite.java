package au.edu.versi.huni.gwt.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;

public class HeaderComposite extends ResizeComposite {

	protected static final String COMPOSITE_INITIAL_DESIGN_WIDTH = "1220px";
	public static final String COMPOSITE_DEPTH = "180px";

	private LayoutPanel wrapperPanel;
	private AdminBarComposite adminBarPanel;

	public HeaderComposite() {
		wrapperPanel = contentBuilder();
		// All composites must call initWidget() in their constructors.
		initWidget(wrapperPanel);
		setStyleName("huni-vl-page-header");
		setSize(COMPOSITE_INITIAL_DESIGN_WIDTH, COMPOSITE_DEPTH);
	}

	protected LayoutPanel contentBuilder() {

		LayoutPanel verticalLayoutPanel = new LayoutPanel();

		// Admin area

		adminBarPanel = new AdminBarComposite();
		adminBarPanel.setWidth("100%");
		verticalLayoutPanel.add(adminBarPanel);

		// Logo

		LogoComposite logoContainerPanel = new LogoComposite();
		logoContainerPanel.setWidth("100%");
		verticalLayoutPanel.add(logoContainerPanel);
		verticalLayoutPanel.setWidgetLeftWidth(logoContainerPanel, 0.0, Unit.PX, 1220.0, Unit.PX);
		verticalLayoutPanel.setWidgetTopHeight(logoContainerPanel, 34.0, Unit.PX, 80.0, Unit.PX);

		// Banner

		BannerComposite bannerComposite = new BannerComposite();
		bannerComposite.setWidth("100%");
		verticalLayoutPanel.add(bannerComposite);
		verticalLayoutPanel.setWidgetLeftRight(bannerComposite, 0.0, Unit.PX, 0.0, Unit.PX);
		verticalLayoutPanel.setWidgetTopHeight(bannerComposite, 120.0, Unit.PX, 60.0, Unit.PX);

		return verticalLayoutPanel;
	}
}
