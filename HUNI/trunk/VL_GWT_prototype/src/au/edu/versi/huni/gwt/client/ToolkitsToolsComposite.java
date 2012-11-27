package au.edu.versi.huni.gwt.client;

import java.beans.Beans;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Tree;

public class ToolkitsToolsComposite extends ResizeComposite implements ClickHandler{

	private DockLayoutPanel toolKitsToolsDockLayoutPanel;
	private CrudButtonComposite crudButtonPanel;
	
	public ToolkitsToolsComposite()
	{
		crudButtonPanel = new CrudButtonComposite();
		toolKitsToolsDockLayoutPanel = toolkitsToolsPanelBuilder();
	      // All composites must call initWidget() in their constructors.
	      initWidget(toolKitsToolsDockLayoutPanel);
	      
		 if (isDesignTime()) { // or !Beans.isDesignTime() in GWT 2.4 or higher
			 toolKitsToolsDockLayoutPanel.setSize("250px", "600px");
	        }
		 else
		 {
			 toolKitsToolsDockLayoutPanel.setSize("100%", "100%");
		 }

	      // Give the overall composite a style name.
	      setStyleName("huni-toolkits-tools-list");
	}
	
	protected DockLayoutPanel toolkitsToolsPanelBuilder() {
		
		DockLayoutPanel toolKitsToolsDockLayoutPanel = new DockLayoutPanel(Unit.EM);
		
		// The container label
		InlineLabel toolkitsToolsInlineLabel = new InlineLabel("Toolkits/Tools");
		toolkitsToolsInlineLabel.setSize("100%", "20px");
		toolKitsToolsDockLayoutPanel.addNorth(toolkitsToolsInlineLabel, 1.9);
		
		toolKitsToolsDockLayoutPanel.addSouth(crudButtonPanel, 3.1);

		// The tree widget container. Decorate outline plus tree widget.
		DecoratorPanel toolKitsToolsDecoratorPanel = new DecoratorPanel();		
		toolKitsToolsDecoratorPanel.setSize("100%", "100%");	
		//toolKitsToolsDecoratorPanel.setWidth("95%");	
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
	
	// Implement the following method exactly as-is
    private static final boolean isDesignTime() {
        return Beans.isDesignTime(); // GWT 2.4 and above
    }

}
