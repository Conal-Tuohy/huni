package au.edu.versi.huni.gwt.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;

public class HomeComposite extends ResizeComposite {
	
	public static String COMPOSITE_INITIAL_DESIGN_WIDTH = "1000px";
	public static String COMPOSITE_DEPTH = "1000px";

	private static final String CATALOG_COMPOSITE_WIDTH = "15em";
	private static final String ACTIVITY_COMPOSITE_WIDTH = CATALOG_COMPOSITE_WIDTH;
	private static final String INTRODUCTION_COMPOSITE_DEPTH = "19em";
	private static final String SLIDESHOW_COMPOSITE_DEPTH = "18em";
	
	private static final double CATALOG_PANEL_WIDTH = 16.0;
	private static final double ACTIVITY_PANEL_WIDTH = CATALOG_PANEL_WIDTH + 1.0;
	private static final double INTRODUCTION_PANEL_DEPTH = 20.0;
	private static final double SLIDESHOW_PANEL_DEPTH = 19.0;

	private DockLayoutPanel wrapperPanel;
	private CatalogComposite catalogComposite;
	private IntroductionComposite introductionComposite;
	private ActivityComposite activityComposite;
	private SlideShowComposite slideShowComposite;
	private ArticleListComposite articleListComposite;

	public HomeComposite() {
		wrapperPanel = buildContents();
		initWidget(wrapperPanel);
		setStyleName("huni-vl-home-layout");
		setSize(COMPOSITE_INITIAL_DESIGN_WIDTH, COMPOSITE_DEPTH);
	}

	protected DockLayoutPanel buildContents() {
		DockLayoutPanel homeLayoutPanel = new DockLayoutPanel(Unit.EM);
		
		catalogComposite = new CatalogComposite();
		catalogComposite.setSize(CATALOG_COMPOSITE_WIDTH, "100%");
		homeLayoutPanel.addWest(catalogComposite, CATALOG_PANEL_WIDTH);
		
		activityComposite = new ActivityComposite();
		activityComposite.setSize(ACTIVITY_COMPOSITE_WIDTH, "100%");
		homeLayoutPanel.addEast(activityComposite, ACTIVITY_PANEL_WIDTH);
		
		introductionComposite = new IntroductionComposite();
		introductionComposite.setSize("100%", INTRODUCTION_COMPOSITE_DEPTH);
		homeLayoutPanel.addNorth(introductionComposite, INTRODUCTION_PANEL_DEPTH);

		slideShowComposite = new SlideShowComposite();
		slideShowComposite.setSize("100%", SLIDESHOW_COMPOSITE_DEPTH);
		homeLayoutPanel.addSouth(slideShowComposite, SLIDESHOW_PANEL_DEPTH);

		articleListComposite = new ArticleListComposite();
		articleListComposite.setSize("100%", "100%");
		homeLayoutPanel.add(articleListComposite);

		return homeLayoutPanel;

	}
}
