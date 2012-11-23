package au.edu.versi.huni.gwt.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

public class ProjectsDatasetsComposite extends Composite implements
		ClickHandler {
	
	private DockLayoutPanel projectsDatasourcesDockLayoutPanel;
	
	public ProjectsDatasetsComposite()
	{
		projectsDatasourcesDockLayoutPanel = projectsDatasetsPanelBuilder();
	      // All composites must call initWidget() in their constructors.
	      initWidget(projectsDatasourcesDockLayoutPanel);

	      // Give the overall composite a style name.
	      setStyleName("huni-projects-datasources-list");
	}

	protected DockLayoutPanel projectsDatasetsPanelBuilder() {
		
		// The outermost container
		DockLayoutPanel projectsDatasourcesDockLayoutPanel = new DockLayoutPanel(Unit.EM);
		projectsDatasourcesDockLayoutPanel.setSize("100%", "100%");

		// The container label
		InlineLabel projectsDatasetsInlinelabel = new InlineLabel("Projects/Datasets");
		projectsDatasetsInlinelabel.setSize("100%", "20px");
		projectsDatasourcesDockLayoutPanel.addNorth(projectsDatasetsInlinelabel, 1.7);

		// The button container populated with buttons
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setWidth("75%");
		
		Button newButton = new Button("New");
		newButton.addClickHandler(this);
		horizontalPanel.add(newButton);	
		
		Button editButton = new Button("Edit");
		editButton.addClickHandler(this);
		horizontalPanel.add(editButton);
		
		Button deleteButton = new Button("Delete");
		deleteButton.addClickHandler(this);
		horizontalPanel.add(deleteButton);	
		
		projectsDatasourcesDockLayoutPanel.addSouth(horizontalPanel, 2.8);		
		
		// The tree widget container. Decorate outline plus tree widget.
		DecoratorPanel projectsDatasourcesDecoratorPanel = new DecoratorPanel();
		projectsDatasourcesDecoratorPanel.setSize("95%", "95%");		
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

}
