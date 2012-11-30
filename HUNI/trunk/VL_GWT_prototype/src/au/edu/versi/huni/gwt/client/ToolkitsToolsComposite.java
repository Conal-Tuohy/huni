package au.edu.versi.huni.gwt.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Tree;

public class ToolkitsToolsComposite extends ResizeComposite {

	protected static final String COMPOSITE_INITIAL_DESIGN_WIDTH = "400px";
	public static final String COMPOSITE_DEPTH = "500px";

	private DockLayoutPanel wrapperPanel;
	private CrudButtonComposite crudButtonPanel;

	public ToolkitsToolsComposite() {
		wrapperPanel = toolkitsToolsPanelBuilder();
		initWidget(wrapperPanel);
		setStyleName("huni-toolkits-tools-list");
		setSize(COMPOSITE_INITIAL_DESIGN_WIDTH, COMPOSITE_DEPTH);
	}

	protected DockLayoutPanel toolkitsToolsPanelBuilder() {

		DockLayoutPanel toolKitsToolsDockLayoutPanel = new DockLayoutPanel(Unit.EM);

		// The container label
		InlineLabel toolkitsToolsInlineLabel = new InlineLabel("Toolkits/Tools");
		toolkitsToolsInlineLabel.setSize("100%", "20px");
		toolKitsToolsDockLayoutPanel.addNorth(toolkitsToolsInlineLabel, 1.9);

		crudButtonPanel = new CrudButtonComposite();
		toolKitsToolsDockLayoutPanel.addSouth(crudButtonPanel, 3.1);

		// The tree widget container. Decorate outline plus tree widget.
		DecoratorPanel toolKitsToolsDecoratorPanel = new DecoratorPanel();
		toolKitsToolsDecoratorPanel.setSize("100%", "100%");
		// toolKitsToolsDecoratorPanel.setWidth("95%");
		Tree toolKitsToolsTree = new Tree();
		toolKitsToolsDecoratorPanel.setWidget(toolKitsToolsTree);
		toolKitsToolsTree.setSize("100%", "190px");
		toolKitsToolsDockLayoutPanel.add(toolKitsToolsDecoratorPanel);

		return toolKitsToolsDockLayoutPanel;
	}
}
