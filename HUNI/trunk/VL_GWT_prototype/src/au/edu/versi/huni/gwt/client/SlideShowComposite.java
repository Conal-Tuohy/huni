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

public class SlideShowComposite extends ResizeComposite implements ClickHandler {

	private DockLayoutPanel slideShowDockLayoutPanel;
	
	public SlideShowComposite()
	{
		slideShowDockLayoutPanel = contentBuilder();
	      // All composites must call initWidget() in their constructors.
	      initWidget(slideShowDockLayoutPanel);

			 if (isDesignTime()) { // or !Beans.isDesignTime() in GWT 2.4 or higher
				 slideShowDockLayoutPanel.setSize("400px", "600px");
		        }
			 else
			 {
				 slideShowDockLayoutPanel.setWidth("100%");
			 }

	      // Give the overall composite a style name.
	      setStyleName("huni-slide-show");
	}
	
	protected DockLayoutPanel contentBuilder() {
		DockLayoutPanel slideshowPanel = new DockLayoutPanel(Unit.EM);
		
		InlineLabel toolDetailInlineLabel = new InlineLabel("Slide show");
		toolDetailInlineLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		toolDetailInlineLabel.setSize("100%", "20px");
		slideshowPanel.addNorth(toolDetailInlineLabel, 1.8);
		
		// Temporary place holder.
		Frame frame = new Frame("/slide-show/slide-show.html");
		frame.setWidth("100%");
		frame.setStylePrimaryName("huni-slide-show-frame");
		slideshowPanel.add(frame);
		
		return slideshowPanel;
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
