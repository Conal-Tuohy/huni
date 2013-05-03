package au.edu.versi.huni.gwt.client.view;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ResizeComposite;

public class ArticleListComposite extends ResizeComposite {

	public static String COMPOSITE_INITIAL_DESIGN_WIDTH = "600px";
	public static String COMPOSITE_DEPTH = "600px";

	private DockLayoutPanel wrapperPanel;

	public ArticleListComposite() {
		wrapperPanel = contentBuilder();
		initWidget(wrapperPanel);
		setStyleName("huni-article-list");
		setSize(COMPOSITE_INITIAL_DESIGN_WIDTH, COMPOSITE_DEPTH);
	}

	protected DockLayoutPanel contentBuilder() {
		DockLayoutPanel layoutPanel = new DockLayoutPanel(Unit.EM);

		InlineLabel toolDetailInlineLabel = new InlineLabel("Article list");
		toolDetailInlineLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		toolDetailInlineLabel.setSize("100%", "20px");
		layoutPanel.addNorth(toolDetailInlineLabel, 1.8);
		
		Frame frame = new Frame("http://www.google.com");
		frame.setStyleName("huni-article-list-frame");
		layoutPanel.add(frame);

		return layoutPanel;
	}
}
