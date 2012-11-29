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

public class IntroductionComposite extends ResizeComposite implements
		ClickHandler {

	private DockLayoutPanel introductionDockLayoutPanel;

	public IntroductionComposite() {
		introductionDockLayoutPanel = contentBuilder();
		// All composites must call initWidget() in their constructors.
		initWidget(introductionDockLayoutPanel);

		if (isDesignTime()) { // or !Beans.isDesignTime() in GWT 2.4 or higher
			setSize("400px", "300px");
		}

		// Give the overall composite a style name.
		setStyleName("huni-introduction");
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

	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub

	}

	// Implement the following method exactly as-is
	private static final boolean isDesignTime() {
		return Beans.isDesignTime(); // GWT 2.4 and above
	}

}
