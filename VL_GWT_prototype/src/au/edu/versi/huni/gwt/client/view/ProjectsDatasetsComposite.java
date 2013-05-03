package au.edu.versi.huni.gwt.client.view;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

public class ProjectsDatasetsComposite extends ResizeComposite {

	protected static final String COMPOSITE_INITIAL_DESIGN_WIDTH = "400px";
	public static final String COMPOSITE_DEPTH = "500px";

	private DockLayoutPanel wrapperPanel;
	private CrudButtonComposite crudButtonPanel;

	public ProjectsDatasetsComposite() {
		wrapperPanel = contentBuilder();
		initWidget(wrapperPanel);
		setStyleName("huni-projects-datasources-list");
		setSize(COMPOSITE_INITIAL_DESIGN_WIDTH, COMPOSITE_DEPTH);
	}

	protected DockLayoutPanel contentBuilder() {

		// The outermost container
		DockLayoutPanel projectsDatasourcesDockLayoutPanel = new DockLayoutPanel(Unit.EM);

		// The container label
		InlineLabel projectsDatasetsInlinelabel = new InlineLabel("Projects/Datasets");
		projectsDatasetsInlinelabel.setSize("100%", "20px");
		projectsDatasourcesDockLayoutPanel.addNorth(projectsDatasetsInlinelabel, 1.9);

		crudButtonPanel = new CrudButtonComposite();
		projectsDatasourcesDockLayoutPanel.addSouth(crudButtonPanel, 3.1);

		// The tree widget container. Decorate outline plus tree widget.
		DecoratorPanel projectsDatasourcesDecoratorPanel = new DecoratorPanel();
		projectsDatasourcesDecoratorPanel.setSize("100%", "100%");
		Tree projectsDatasourcesTree = new Tree();
		projectsDatasourcesDecoratorPanel.setWidget(projectsDatasourcesTree);
		projectsDatasourcesTree.setSize("100%", "190px");
		projectsDatasourcesDockLayoutPanel.add(projectsDatasourcesDecoratorPanel);

		TreeItem rootItem = new TreeItem();
		rootItem.addTextItem("Project 0");
		rootItem.addTextItem("Project 1");
		rootItem.addTextItem("Project 2");

		projectsDatasourcesTree.addItem(rootItem);

		return projectsDatasourcesDockLayoutPanel;
	}

	public Panel getPanel() {
		return wrapperPanel;
	}
}
