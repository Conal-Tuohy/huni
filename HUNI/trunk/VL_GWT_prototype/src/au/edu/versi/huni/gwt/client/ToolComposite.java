package au.edu.versi.huni.gwt.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ResizeComposite;

public class ToolComposite extends ResizeComposite {

	protected static final String COMPOSITE_INITIAL_DESIGN_WIDTH = "400px";
	public static final String COMPOSITE_DEPTH = "500px";

	private DockLayoutPanel wrapperPanel;

	public ToolComposite() {
		wrapperPanel = contentBuilder();
		initWidget(wrapperPanel);
		setStyleName("huni-tool-details");
		setSize(COMPOSITE_INITIAL_DESIGN_WIDTH, COMPOSITE_DEPTH);
	}

	protected DockLayoutPanel contentBuilder() {
		DockLayoutPanel toolDetailsPanel = new DockLayoutPanel(Unit.EM);

		InlineLabel toolDetailInlineLabel = new InlineLabel("Tool");
		toolDetailInlineLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		toolDetailInlineLabel.setSize("100%", "20px");
		toolDetailsPanel.addNorth(toolDetailInlineLabel, 1.8);

		// Temporary place holder.
		Frame frame = new Frame("/hello_dojo/index.html");
		frame.setStyleName("huni-tool-frame");
		toolDetailsPanel.add(frame);

		return toolDetailsPanel;
	}
}
