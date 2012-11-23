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
import com.google.gwt.user.client.ui.Tree;

public class ToolkitsToolsComposite extends Composite implements ClickHandler {

	private DockLayoutPanel toolKitsToolsDockLayoutPanel;
	
	public ToolkitsToolsComposite()
	{
		toolKitsToolsDockLayoutPanel = toolkitsToolsPanelBuilder();
	      // All composites must call initWidget() in their constructors.
	      initWidget(toolKitsToolsDockLayoutPanel);

	      // Give the overall composite a style name.
	      setStyleName("huni-toolkits-tools-list");
	}
	
	protected DockLayoutPanel toolkitsToolsPanelBuilder() {
		
		DockLayoutPanel toolKitsToolsDockLayoutPanel = new DockLayoutPanel(Unit.EM);
		toolKitsToolsDockLayoutPanel.setSize("100%", "100%");
		
		// The container label
		InlineLabel toolkitsToolsInlineLabel = new InlineLabel("Toolkits/Tools");
		toolkitsToolsInlineLabel.setSize("100%", "20px");
		toolKitsToolsDockLayoutPanel.addNorth(toolkitsToolsInlineLabel, 1.9);
		
		// The button container populated with buttons
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setWidth("75%");		
		Button newButton = new Button("New");
		horizontalPanel.add(newButton);		
		Button editButton = new Button("Edit");
		horizontalPanel.add(editButton);		
		Button deleteButton = new Button("Delete");
		horizontalPanel.add(deleteButton);
		toolKitsToolsDockLayoutPanel.addSouth(horizontalPanel, 3.1);

		// The tree widget container. Decorate outline plus tree widget.
		DecoratorPanel toolKitsToolsDecoratorPanel = new DecoratorPanel();
		toolKitsToolsDecoratorPanel.setSize("95%", "95%");		
		Tree toolKitsToolsTree = new Tree();
		toolKitsToolsDecoratorPanel.setWidget(toolKitsToolsTree);
		toolKitsToolsTree.setSize("100%", "190px");
		toolKitsToolsDockLayoutPanel.add(toolKitsToolsDecoratorPanel);

		return toolKitsToolsDockLayoutPanel;
	}

	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub

	}

}
