package au.edu.versi.huni.gwt.client;

import java.beans.Beans;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class ToolComposite extends ResizeComposite implements ClickHandler {

	private DockLayoutPanel toolDetailsDockLayoutPanel;
	
	public ToolComposite()
	{
		toolDetailsDockLayoutPanel = contentBuilder();
	      // All composites must call initWidget() in their constructors.
	      initWidget(toolDetailsDockLayoutPanel);

			 if (isDesignTime()) { // or !Beans.isDesignTime() in GWT 2.4 or higher
				 toolDetailsDockLayoutPanel.setSize("400px", "600px");
		        }
			 else
			 {
				 toolDetailsDockLayoutPanel.setSize("100%", "100%");
			 }

	      // Give the overall composite a style name.
	      setStyleName("huni-tool-details");
	}
	
	protected DockLayoutPanel contentBuilder() {
		DockLayoutPanel toolDetailsPanel = new DockLayoutPanel(Unit.EM);
		
		InlineLabel toolDetailInlineLabel = new InlineLabel("Tool");
		toolDetailInlineLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		toolDetailInlineLabel.setSize("100%", "20px");
		toolDetailsPanel.addNorth(toolDetailInlineLabel, 1.8);
		
		// Temporary place holder.
		Frame frame = new Frame("/hello_dojo/index.html");
		frame.setSize("100%", "100%");
		frame.setStylePrimaryName("huni-tool-frame");
		toolDetailsPanel.add(frame);
		
		return toolDetailsPanel;
	}

	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub

	}
	
	// Implement the following method exactly as-is
    private static final boolean isDesignTime() {
        return Beans.isDesignTime(); // GWT 2.4 and above
    }

}
