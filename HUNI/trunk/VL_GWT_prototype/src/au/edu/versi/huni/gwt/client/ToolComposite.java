package au.edu.versi.huni.gwt.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ResizeComposite;

public class ToolComposite extends ResizeComposite implements ClickHandler {

	private DockLayoutPanel toolDetailsDockLayoutPanel;
	
	public ToolComposite()
	{
		toolDetailsDockLayoutPanel = toolDetailsBuilder();
	      // All composites must call initWidget() in their constructors.
	      initWidget(toolDetailsDockLayoutPanel);

	      // Give the overall composite a style name.
	      setStyleName("huni-tool-details");
	}
	
	protected DockLayoutPanel toolDetailsBuilder() {
		DockLayoutPanel toolDetailsPanel = new DockLayoutPanel(Unit.EM);
		toolDetailsPanel.setSize("98%", "100%");
		
		InlineLabel toolDetailInlineLabel = new InlineLabel("Tool");
		toolDetailInlineLabel.setSize("100%", "20px");
		toolDetailsPanel.addNorth(toolDetailInlineLabel, 1.8);
		
		// Temporary place holder.
		Frame frame = new Frame("/hello_dojo/index.html");
		frame.setSize("98%", "98%");
		toolDetailsPanel.add(frame);
		
		return toolDetailsPanel;
	}

	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub

	}

}
