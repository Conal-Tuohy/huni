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
	private AboutComposite aboutComposite;
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
						
		// Right column and contents
		
		activityComposite = new ActivityComposite();
		activityComposite.setSize("100%", "100%");

		homeLayoutPanel.addEast(activityComposite, 13.8);
		
		// Middle/centre column and contents
		
		grid = new Grid(2, 2);
		grid.setSize("100%", "100%");
		
		homeLayoutPanel.add(grid);
		
		CellFormatter cellFormatter = grid.getCellFormatter();
		
		cellFormatter.setWidth(1, 0, "200px");
		cellFormatter.setHeight(1, 0, "200px");		
		
		cellFormatter.setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);

		cellFormatter.setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
		cellFormatter.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
		
		cellFormatter.setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
		cellFormatter.setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_LEFT);
		
		cellFormatter.setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_TOP);
		cellFormatter.setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_LEFT);

		
		// Toolkits and tools
		// Left column and contents
		
		// Data set catalog
		
		catalogComposite = new CatalogComposite();
		catalogComposite.setSize("100%", "100%");
		grid.setWidget(0, 0, catalogComposite);
		
		aboutComposite = new AboutComposite();
		aboutComposite.setSize("200px", "200px");

		introductionComposite = new IntroductionComposite();
		introductionComposite.setSize("100%", "400px");
		
		slideShowComposite = new SlideShowComposite();
		catalogComposite.setSize("100%", "100%");

		grid.setWidget(0, 1, introductionComposite);
		grid.setWidget(1, 0, aboutComposite);
		grid.setWidget(1, 1, slideShowComposite);

		return homeLayoutPanel;

	}
	
	// Implement the following method exactly as-is
    private static final boolean isDesignTime() {
        return Beans.isDesignTime(); // GWT 2.4 and above
    }
}
