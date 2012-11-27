package au.edu.versi.huni.gwt.client;

import java.beans.Beans;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

public class ProjectsDatasetsComposite extends ResizeComposite implements
		ClickHandler {
	
	private DockLayoutPanel projectsDatasourcesDockLayoutPanel;
	private CrudButtonComposite crudButtonPanel;
	
	public ProjectsDatasetsComposite()
	{
		crudButtonPanel = new CrudButtonComposite();
		projectsDatasourcesDockLayoutPanel = projectsDatasetsPanelBuilder();
	      // All composites must call initWidget() in their constructors.
	      initWidget(projectsDatasourcesDockLayoutPanel);

	      // Give the overall composite a style name.
	      setStyleName("huni-projects-datasources-list");
	}

	protected DockLayoutPanel projectsDatasetsPanelBuilder() {
		
		// The outermost container
		DockLayoutPanel projectsDatasourcesDockLayoutPanel = new DockLayoutPanel(Unit.EM);

		 if (isDesignTime()) { // or !Beans.isDesignTime() in GWT 2.4 or higher
			 projectsDatasourcesDockLayoutPanel.setSize("250px", "600px");
	        }
		 else
		 {
			 projectsDatasourcesDockLayoutPanel.setSize("100%", "100%");
		 }

		// The container label
		InlineLabel projectsDatasetsInlinelabel = new InlineLabel("Projects/Datasets");
		projectsDatasetsInlinelabel.setSize("100%", "20px");
		projectsDatasourcesDockLayoutPanel.addNorth(projectsDatasetsInlinelabel, 1.9);
		
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
		return projectsDatasourcesDockLayoutPanel;
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
