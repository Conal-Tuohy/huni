package au.edu.versi.huni.gwt.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;

public class FooterComposite extends Composite implements ClickHandler {

	public static final String FOOTER_DEPTH = "125px";
	
	private DockLayoutPanel footerDockLayoutPanel;
	private HTML creditLogo;
	private AboutComposite aboutComposite;
	private LayoutPanel westLayoutPanel;
	private LayoutPanel eastLayoutPanel;

	public FooterComposite() {
		footerDockLayoutPanel = contentBuilder();
		// All composites must call initWidget() in their constructors.
		initWidget(footerDockLayoutPanel);

		// Give the overall composite a style name.
		setStyleName("huni-vl-page-footer");
		setSize("800px", FOOTER_DEPTH);
	}

	protected DockLayoutPanel contentBuilder() {

		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);

		westLayoutPanel = new LayoutPanel();
		dockLayoutPanel.addWest(westLayoutPanel, 18.5);

		aboutComposite = new AboutComposite();
		westLayoutPanel.add(aboutComposite);
		westLayoutPanel.setWidgetTopBottom(aboutComposite, 14.0, Unit.PX, 15.0, Unit.PX);
		westLayoutPanel.setWidgetRightWidth(aboutComposite, 0.0, Unit.PX, 210.0, Unit.PX);
		aboutComposite.setSize("200px", "100px");

		String htmlStr = "<div class=\"huni-vl-page-footer-credit\">&nbsp;</div>";

		eastLayoutPanel = new LayoutPanel();
		dockLayoutPanel.addEast(eastLayoutPanel, 30.5);
		creditLogo = new HTML(htmlStr);
		eastLayoutPanel.add(creditLogo);
		eastLayoutPanel.setWidgetTopBottom(creditLogo, 16.0, Unit.PX, 0.0, Unit.PX);
		eastLayoutPanel.setWidgetRightWidth(creditLogo, 63.0, Unit.PX, 333.0, Unit.PX);

		return dockLayoutPanel;
	}

	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub

	}
}
