package au.edu.versi.huni.gwt.client;

import java.beans.Beans;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;

public class HomeComposite extends ResizeComposite {

	private CatalogComposite catalogComposite;
	private IntroductionComposite introductionComposite;
	private ActivityComposite activityComposite;
	private SlideShowComposite slideShowComposite;
	private DockLayoutPanel homeLayoutPanel;

	public HomeComposite() {
		homeLayoutPanel = buildContents();
		initWidget(homeLayoutPanel);

		// Give the overall composite a style name.
		setStyleName("huni-vl-home-layout");
	}

	protected DockLayoutPanel buildContents() {
		DockLayoutPanel homeLayoutPanel = new DockLayoutPanel(Unit.EM);

		if (isDesignTime()) { // or !Beans.isDesignTime() in GWT 2.4 or higher
			homeLayoutPanel.setSize("800px", "800px");
		}

		catalogComposite = new CatalogComposite();
		catalogComposite.setSize("100%", "100%");
		homeLayoutPanel.addWest(catalogComposite, 16.0);

		activityComposite = new ActivityComposite();
		activityComposite.setSize("100%", "100%");
		homeLayoutPanel.addEast(activityComposite, 16.0);

		introductionComposite = new IntroductionComposite();
		introductionComposite.setSize("100%", "100%");
		homeLayoutPanel.addNorth(introductionComposite, 29.8);

		slideShowComposite = new SlideShowComposite();
		slideShowComposite.setSize("100%", "100%");
		homeLayoutPanel.addSouth(slideShowComposite, 31.3);

		return homeLayoutPanel;

	}

	// Implement the following method exactly as-is
	private static final boolean isDesignTime() {
		return Beans.isDesignTime(); // GWT 2.4 and above
	}
}
