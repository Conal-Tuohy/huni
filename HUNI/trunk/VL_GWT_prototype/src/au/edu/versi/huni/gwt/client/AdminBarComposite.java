package au.edu.versi.huni.gwt.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;

public class AdminBarComposite extends ResizeComposite {

	protected static final String COMPOSITE_INITIAL_DESIGN_WIDTH = "800px";
	public static final String COMPOSITE_DEPTH = "36px";

	private LayoutPanel wrapperPanel;
	private SearchBoxComposite searchTextBox;

	public AdminBarComposite() {
		wrapperPanel = contentBuilder();
		initWidget(wrapperPanel);
		setStyleName("huni-vl-logo");
		setSize(COMPOSITE_INITIAL_DESIGN_WIDTH, COMPOSITE_DEPTH);
	}

	protected LayoutPanel contentBuilder() {
		LayoutPanel layoutPanel = new LayoutPanel();

		searchTextBox = new SearchBoxComposite();
		layoutPanel.add(searchTextBox);

		layoutPanel.setWidgetLeftWidth(searchTextBox, 0.0, Unit.PX, 423.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(searchTextBox, 0.0, Unit.PX, 25.0, Unit.PX);

		InlineHyperlink registerHyperlink = new InlineHyperlink("Register", false, "newHistoryToken");
		layoutPanel.add(registerHyperlink);

		layoutPanel.setWidgetRightWidth(registerHyperlink, 135.0, Unit.PX, 67.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(registerHyperlink, 6.0, Unit.PX, 18.0, Unit.PX);

		InlineHyperlink loginHyperlink = new InlineHyperlink("Login/AAF login", false, "newHistoryToken");
		layoutPanel.add(loginHyperlink);

		layoutPanel.setWidgetRightWidth(loginHyperlink, 0.0, Unit.PX, 107.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(loginHyperlink, 6.0, Unit.PX, 18.0, Unit.PX);
		return layoutPanel;
	}
}
