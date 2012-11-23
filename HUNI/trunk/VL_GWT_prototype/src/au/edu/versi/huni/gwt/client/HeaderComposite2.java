package au.edu.versi.huni.gwt.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTML;

public class HeaderComposite2 extends Composite implements ClickHandler {

	private VerticalPanel headerDockLayoutPanel;
	private SearchBoxComposite searchTextBox;
	private Image logoImage;
	
	public HeaderComposite2()
	{
		headerDockLayoutPanel = headerBuilder();
	      // All composites must call initWidget() in their constructors.
	      initWidget(headerDockLayoutPanel);

	      // Give the overall composite a style name.
	      setStyleName("huni-vl-page-header");
	}
	
	protected VerticalPanel headerBuilder() {
		
		VerticalPanel headerDockLayoutPanel = new VerticalPanel();
		headerDockLayoutPanel.setSize("800px", "180px");
		
		// Admin area
		
		LayoutPanel adminBarPanel = adminBarBuilder();		
		headerDockLayoutPanel.add(adminBarPanel);
	
		// Logo
		
		LayoutPanel logoContainerPanel = logoPanelBuilder();
		headerDockLayoutPanel.add(logoContainerPanel);

		// Banner

		LayoutPanel bannerContainerPanel = bannerPanelBuilder();
		headerDockLayoutPanel.add(bannerContainerPanel);

		return headerDockLayoutPanel;
	}

	protected LayoutPanel adminBarBuilder() {
		LayoutPanel layoutPanel = new LayoutPanel();
		layoutPanel.setSize("100%", "36px");
		
		searchTextBox = new SearchBoxComposite();
		layoutPanel.add(searchTextBox);

		layoutPanel.setWidgetLeftWidth(searchTextBox, 0.0, Unit.PX, 358.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(searchTextBox, 0.0, Unit.PX, 24.0, Unit.PX);

//		layoutPanel.setWidgetLeftWidth(searchTextBox, 57.0, Unit.PX, 173.0, Unit.PX);
//		layoutPanel.setWidgetTopHeight(searchTextBox, 0.0, Unit.PX, 24.0, Unit.PX);

		InlineHyperlink advanceSearchHyperlink = new InlineHyperlink("Advanced search", false, "newHistoryToken");
		layoutPanel.add(advanceSearchHyperlink);

		layoutPanel.setWidgetLeftWidth(advanceSearchHyperlink, 409.0, Unit.PX, 101.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(advanceSearchHyperlink, 6.0, Unit.PX, 18.0, Unit.PX);

		InlineHyperlink registerHyperlink = new InlineHyperlink("Register", false, "newHistoryToken");
		layoutPanel.add(registerHyperlink);
		
		layoutPanel.setWidgetLeftWidth(registerHyperlink, 598.0, Unit.PX, 48.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(registerHyperlink, 6.0, Unit.PX, 18.0, Unit.PX);

		InlineHyperlink loginHyperlink = new InlineHyperlink("Login/AAF login", false, "newHistoryToken");
		layoutPanel.add(loginHyperlink);

		layoutPanel.setWidgetRightWidth(loginHyperlink, 0.0, Unit.PX, 107.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(loginHyperlink, 6.0, Unit.PX, 18.0, Unit.PX);
		return layoutPanel;
	}

	protected LayoutPanel bannerPanelBuilder() {
		
		LayoutPanel bannerContainerPanel = new LayoutPanel();
		bannerContainerPanel.setSize("100%", "60px");

		Image bannerImage = new Image("resources/images/huni-banner-slim.jpg");
		bannerImage.setAltText("HuNI Banner");
		bannerImage.setSize("1597px", "60px");
		
		bannerContainerPanel.add(bannerImage);
		
		bannerContainerPanel.setWidgetLeftWidth(bannerImage, 0.0, Unit.PX, 803.0, Unit.PX);
				
		InlineLabel virtualLaboratoryInlineLabel = new InlineLabel("Humanities Virtual Laboratory");
		virtualLaboratoryInlineLabel.setWordWrap(false);
		virtualLaboratoryInlineLabel.setDirectionEstimator(true);
		virtualLaboratoryInlineLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		virtualLaboratoryInlineLabel.setStyleName("huni-vl-banner-title");
		virtualLaboratoryInlineLabel.setSize("400px", "40px");
		
		bannerContainerPanel.add(virtualLaboratoryInlineLabel);
		bannerContainerPanel.setWidgetTopHeight(virtualLaboratoryInlineLabel, 10.0, Unit.PX, 40.0, Unit.PX);
		bannerContainerPanel.setWidgetLeftWidth(virtualLaboratoryInlineLabel, 37.0, Unit.PX, 800.0, Unit.PX);
		
		return bannerContainerPanel;
	}

	protected LayoutPanel logoPanelBuilder() {
		
		String logoWidth = "210px";
		String logoHeight = "80px";
		
		LayoutPanel logoContainerPanel = new LayoutPanel();		
		logoContainerPanel.setSize(logoWidth, logoHeight);
		
		logoImage = new Image("resources/images/huni-logo-21.png");
		logoImage.setAltText("HuNI Logo");
		logoImage.setSize(logoWidth, logoHeight);
		
		logoContainerPanel.add(logoImage);
		
		return logoContainerPanel;
	}

	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub

	}
}
