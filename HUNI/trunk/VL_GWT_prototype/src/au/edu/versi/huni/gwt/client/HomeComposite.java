package au.edu.versi.huni.gwt.client;

import java.beans.Beans;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class HomeComposite extends ResizeComposite {

	private CatalogComposite catalogComposite;
	private IntroductionComposite introductionComposite;
	private ActivityComposite activityComposite;
	private SlideShowComposite slideShowComposite;
	private DockLayoutPanel homeLayoutPanel;
	private Grid grid;

	
	public HomeComposite()
	{
		homeLayoutPanel = buildContents();
	      // All composites must call initWidget() in their constructors.
	      initWidget(homeLayoutPanel);

	      // Give the overall composite a style name.
	      setStylePrimaryName("huni-vl-home-layout");
	}

	protected DockLayoutPanel buildContents()
	{
		DockLayoutPanel homeLayoutPanel = new DockLayoutPanel(Unit.EM);		
		
		 if (isDesignTime()) { // or !Beans.isDesignTime() in GWT 2.4 or higher
			    homeLayoutPanel.setSize("800px", "800px");
	        }
						
		// Activity panel to the right of the container
		
		activityComposite = new ActivityComposite();
		activityComposite.setSize("100%", "100%");

		homeLayoutPanel.addEast(activityComposite, 13.8);
		
		// Layout the table for 4 panels and add it to the center/left of the container.
		
		grid = new Grid(2, 2);
		grid.setSize("100%", "100%");
		grid.setCellSpacing(5);
		
		homeLayoutPanel.add(grid);
		
		CellFormatter cellFormatter = grid.getCellFormatter();
		
		cellFormatter.setWidth(1, 0, "240px");
		cellFormatter.setHeight(1, 0, "200px");

		cellFormatter.setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
		
		cellFormatter.setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
		cellFormatter.setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_LEFT);
		
		cellFormatter.setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_TOP);

		
		// Fill the 4 table cells with the panels
		
		catalogComposite = new CatalogComposite();
		grid.setWidget(0, 0, catalogComposite);
		catalogComposite.setSize("200px", "500px");

		introductionComposite = new IntroductionComposite();
		introductionComposite.setSize("400px", "400px");
		grid.setWidget(0, 1, introductionComposite);
		
		slideShowComposite = new SlideShowComposite();
		slideShowComposite.setSize("400px", "400px");
		grid.setWidget(1, 1, slideShowComposite);
		grid.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
		grid.getCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_LEFT);
		grid.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		grid.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);

		return homeLayoutPanel;

	}
	
	// Implement the following method exactly as-is
    private static final boolean isDesignTime() {
        return Beans.isDesignTime(); // GWT 2.4 and above
    }
}
