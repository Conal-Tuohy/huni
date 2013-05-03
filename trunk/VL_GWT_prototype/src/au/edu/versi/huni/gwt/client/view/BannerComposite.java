package au.edu.versi.huni.gwt.client.view;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;

public class BannerComposite extends ResizeComposite {

	private static final String ALT_BANNER_TEXT = "HuNI Banner";
	private static final String RESOURCES_IMAGES_HUNI_BANNER = "resources/images/huni-banner-slim.jpg";
	private static final double BANNER_WIDTH_PIXELS = 1597.0;
	private static final String BANNER_WIDTH = "1597px";
	private static final String BANNER_HEIGHT = "60px";
	public static final String COMPOSITE_DEPTH = BANNER_HEIGHT;
	public static final String COMPOSITE_INITIAL_DESIGN_WIDTH = "800px";

	private LayoutPanel layoutPanel;

	public BannerComposite() {
		layoutPanel = contentBuilder();
		initWidget(layoutPanel);
		setStyleName("huni-vl-banner");
		setSize(COMPOSITE_INITIAL_DESIGN_WIDTH, COMPOSITE_DEPTH);
	}

	protected LayoutPanel contentBuilder() {

		LayoutPanel bannerContainerPanel = new LayoutPanel();

		Image bannerImage = new Image(RESOURCES_IMAGES_HUNI_BANNER);
		bannerImage.setAltText(ALT_BANNER_TEXT);
		bannerImage.setSize(BANNER_WIDTH, BANNER_HEIGHT);
		bannerContainerPanel.add(bannerImage);
		bannerContainerPanel.setWidgetLeftWidth(bannerImage, 0.0, Unit.PX, BANNER_WIDTH_PIXELS, Unit.PX);

		InlineLabel virtualLaboratoryInlineLabel = new InlineLabel("Humanities Virtual Laboratory");
		virtualLaboratoryInlineLabel.setWordWrap(false);
		virtualLaboratoryInlineLabel.setDirectionEstimator(true);
		virtualLaboratoryInlineLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		virtualLaboratoryInlineLabel.setStyleName("huni-vl-banner-title");

		bannerContainerPanel.add(virtualLaboratoryInlineLabel);
		bannerContainerPanel.setWidgetTopHeight(virtualLaboratoryInlineLabel, 10.0, Unit.PX, 38.0, Unit.PX);
		bannerContainerPanel.setWidgetLeftWidth(virtualLaboratoryInlineLabel, 18.0, Unit.PX, 440.0, Unit.PX);

		return bannerContainerPanel;
	}
}
