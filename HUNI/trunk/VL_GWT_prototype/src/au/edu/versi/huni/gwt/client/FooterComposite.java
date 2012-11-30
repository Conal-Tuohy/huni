package au.edu.versi.huni.gwt.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;

// Seems that just composite super works fine for the footer during window resizes.
public class FooterComposite extends Composite {

	private static final String COMPOSITE_INITIAL_DESIGN_WIDTH = "800px";
	public static final String COMPOSITE_DEPTH = "125px";

	private static final String ABOUT_BLOCK_HEIGHT = "100px";
	private static final String ABOUT_BLOCK_WIDTH = "200px";

	// The credit block is a div with a logo graphic for the sponsors in the background. 
	// See: virtual_laboratory.css for the definition of huni-vl-page-footer-credit.
	private static final String CREDIT_BLOCK_DIV = "<div class=\"huni-vl-page-footer-credit\">&nbsp;</div>";
	
	private DockLayoutPanel footerDockLayoutPanel;
	private HTML creditLogo;
	private AboutComposite aboutComposite;
	private LayoutPanel westLayoutPanel;
	private LayoutPanel eastLayoutPanel;

	public FooterComposite() {
		footerDockLayoutPanel = contentBuilder();
		initWidget(footerDockLayoutPanel);
		setStyleName("huni-vl-page-footer");
		setSize(COMPOSITE_INITIAL_DESIGN_WIDTH, COMPOSITE_DEPTH);
	}

	protected DockLayoutPanel contentBuilder() {

		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);

		westLayoutPanel = new LayoutPanel();
		dockLayoutPanel.addWest(westLayoutPanel, 18.5);

		aboutComposite = new AboutComposite();
		aboutComposite.setSize(ABOUT_BLOCK_WIDTH, ABOUT_BLOCK_HEIGHT);
		
		westLayoutPanel.add(aboutComposite);
		westLayoutPanel.setWidgetTopBottom(aboutComposite, 14.0, Unit.PX, 15.0, Unit.PX);
		westLayoutPanel.setWidgetRightWidth(aboutComposite, 0.0, Unit.PX, 210.0, Unit.PX);


		eastLayoutPanel = new LayoutPanel();
		creditLogo = new HTML(CREDIT_BLOCK_DIV);		
		eastLayoutPanel.add(creditLogo);
		
		dockLayoutPanel.addEast(eastLayoutPanel, 30.5);
		eastLayoutPanel.setWidgetTopBottom(creditLogo, 16.0, Unit.PX, 0.0, Unit.PX);
		eastLayoutPanel.setWidgetRightWidth(creditLogo, 63.0, Unit.PX, 333.0, Unit.PX);

		return dockLayoutPanel;
	}
}
