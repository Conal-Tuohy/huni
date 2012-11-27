package au.edu.versi.huni.gwt.client;

import java.beans.Beans;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SplitLayoutPanel;

public class WorkspaceComposite extends ResizeComposite {

	private ProjectsDatasetsComposite projectsDatasetsComposite;
	private ToolkitsToolsComposite toolkitsToolsComposite;
	private HistoryComposite historyComposite;
	private ToolComposite toolsDetailsComposite;
	private DockLayoutPanel workspaceLayoutPanel;

	
	public WorkspaceComposite()
	{
		workspaceLayoutPanel = buildContents();
	      // All composites must call initWidget() in their constructors.
	      initWidget(workspaceLayoutPanel);

	      // Give the overall composite a style name.
	      setStyleName("huni-vl-workspace-layout");
	}

	protected DockLayoutPanel buildContents()
	{
		DockLayoutPanel workspaceLayoutPanel = new DockLayoutPanel(Unit.EM);		
		
		 if (isDesignTime()) { // or !Beans.isDesignTime() in GWT 2.4 or higher
			    workspaceLayoutPanel.setSize("718px", "625px");
	        }
				
		// Left column and contents
				
		SplitLayoutPanel verticalSplitPanel = new SplitLayoutPanel();
		verticalSplitPanel.setSize("200px", "100%");		
		// Projects and data sets
		projectsDatasetsComposite = new ProjectsDatasetsComposite();;
		verticalSplitPanel.addNorth(projectsDatasetsComposite, 291.0);
		// Toolkits and tools
		toolkitsToolsComposite = new ToolkitsToolsComposite();		
		verticalSplitPanel.add(toolkitsToolsComposite);
		// Add to parent panel
		workspaceLayoutPanel.addWest(verticalSplitPanel, 16.5);
		
		// Right column and contents
		
		historyComposite = new HistoryComposite();
		workspaceLayoutPanel.addEast(historyComposite, 16.5);
		
		// Middle column and contents

		toolsDetailsComposite = new ToolComposite();		
		workspaceLayoutPanel.add(toolsDetailsComposite);
		
		return workspaceLayoutPanel;

	}
	
	// Implement the following method exactly as-is
    private static final boolean isDesignTime() {
        return Beans.isDesignTime(); // GWT 2.4 and above
    }
}
