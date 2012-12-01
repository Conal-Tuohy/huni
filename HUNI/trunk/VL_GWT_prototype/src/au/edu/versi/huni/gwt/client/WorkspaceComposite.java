package au.edu.versi.huni.gwt.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SplitLayoutPanel;

public class WorkspaceComposite extends ResizeComposite {

	public static String COMPOSITE_INITIAL_DESIGN_WIDTH = "1000px";
	public static String COMPOSITE_DEPTH = "1000px";

	private static final double RIGHT_WORKSPACE_PANEL = 17.0;
	private static final double LEFT_WORKSPACE_PANEL_WIDTH = 17.0;
	private static final String LEFT_WORKSPACE_COMPOSITE_WIDTH_EM = "16em";
	private static final String PROJECTS_DATASETS_COMPOSITE_WIDTH_EM = "15.5em";
	private static final String TOOLKITS_TOOLS_COMPOSITE_WIDTH_EM = "15.5em";
	private static final String HISTORY_COMPOSITE_WIDTH_EM = "15.5em";
	public static final double PROJECTS_DATASETS_DEPTH = 291.0;

	private ProjectsDatasetsComposite projectsDatasetsComposite;
	private ToolkitsToolsComposite toolkitsToolsComposite;
	private HistoryComposite historyComposite;
	private ToolComposite toolsDetailsComposite;
	private DockLayoutPanel wrapperPanel;

	public WorkspaceComposite() {
		wrapperPanel = buildContents();
		initWidget(wrapperPanel);
		setStyleName("huni-vl-workspace-layout");
		setSize(COMPOSITE_INITIAL_DESIGN_WIDTH, COMPOSITE_DEPTH);
	}

	protected DockLayoutPanel buildContents() {
		DockLayoutPanel workspaceLayoutPanel = new DockLayoutPanel(Unit.EM);

		// Left column and contents

		SplitLayoutPanel verticalSplitPanel = new SplitLayoutPanel();
		verticalSplitPanel.setSize(LEFT_WORKSPACE_COMPOSITE_WIDTH_EM, "100%");
		
		// Projects and data sets
		projectsDatasetsComposite = new ProjectsDatasetsComposite();
		projectsDatasetsComposite.setSize(PROJECTS_DATASETS_COMPOSITE_WIDTH_EM, "100%");
		verticalSplitPanel.addNorth(projectsDatasetsComposite, PROJECTS_DATASETS_DEPTH);
		
		// Toolkits and tools
		toolkitsToolsComposite = new ToolkitsToolsComposite();
		toolkitsToolsComposite.setSize(TOOLKITS_TOOLS_COMPOSITE_WIDTH_EM, "100%");
		verticalSplitPanel.add(toolkitsToolsComposite);
		
		workspaceLayoutPanel.addWest(verticalSplitPanel, LEFT_WORKSPACE_PANEL_WIDTH);

		// Right column and contents

		historyComposite = new HistoryComposite();
		historyComposite.setSize(HISTORY_COMPOSITE_WIDTH_EM, "100%");
		workspaceLayoutPanel.addEast(historyComposite, RIGHT_WORKSPACE_PANEL);

		// Middle column and contents

		toolsDetailsComposite = new ToolComposite();
		toolsDetailsComposite.setSize("100%", "100%");
		workspaceLayoutPanel.add(toolsDetailsComposite);

		return workspaceLayoutPanel;

	}
}
