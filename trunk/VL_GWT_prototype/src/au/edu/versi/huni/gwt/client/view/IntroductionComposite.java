package au.edu.versi.huni.gwt.client.view;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ResizeComposite;

public class IntroductionComposite extends ResizeComposite {

	protected static final String COMPOSITE_INITIAL_DESIGN_WIDTH = "400px";
	public static final String COMPOSITE_DEPTH = "500px";

	private DockLayoutPanel wrapperPanel;

	public IntroductionComposite() {
		wrapperPanel = contentBuilder();
		initWidget(wrapperPanel);
		setStyleName("huni-introduction");
		setSize(COMPOSITE_INITIAL_DESIGN_WIDTH, COMPOSITE_DEPTH);
	}

	protected DockLayoutPanel contentBuilder() {
		DockLayoutPanel introductionPanel = new DockLayoutPanel(Unit.EM);

		InlineLabel toolDetailInlineLabel = new InlineLabel("Introduction");
		toolDetailInlineLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		toolDetailInlineLabel.setSize("100%", "20px");
		introductionPanel.addNorth(toolDetailInlineLabel, 1.8);

		// Temporary place holder.
		Frame frame = new Frame("/introduction/introduction.html");
		frame.setStyleName("huni-introduction-frame");
		introductionPanel.add(frame);

		return introductionPanel;
	}
}
