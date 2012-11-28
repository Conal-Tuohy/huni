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

public class ActivityComposite extends ResizeComposite implements ClickHandler {

	private DockLayoutPanel activityDockLayoutPanel;
	private CrudButtonComposite crudButtonPanel;
	
	public ActivityComposite()
	{
		crudButtonPanel = new CrudButtonComposite();
		activityDockLayoutPanel = contentBuilder();
	      // All composites must call initWidget() in their constructors.
	      initWidget(activityDockLayoutPanel);
	      
			 if (isDesignTime()) { // or !Beans.isDesignTime() in GWT 2.4 or higher
				 activityDockLayoutPanel.setSize("400px", "600px");
		        }
			 else
			 {
				 activityDockLayoutPanel.setSize("100%", "100%");
			 }

	      // Give the overall composite a style name.
	      setStyleName("huni-activity-list");
	}
	
	protected DockLayoutPanel contentBuilder() {
		
		DockLayoutPanel historyDockLayoutPanel = new DockLayoutPanel(Unit.EM);
		
		InlineLabel historyInlineLable = new InlineLabel("Research activity");
		historyInlineLable.setSize("100%", "20px");
		historyDockLayoutPanel.addNorth(historyInlineLable, 1.9);
				
		historyDockLayoutPanel.addSouth(crudButtonPanel, 3.1);
		
		DecoratorPanel decoratorPanel = new DecoratorPanel();
		decoratorPanel.setSize("100%", "100%");		
		Tree historytTree = new Tree();
		decoratorPanel.setWidget(historytTree);
		historytTree.setSize("100%", "485px");
		historyDockLayoutPanel.add(decoratorPanel);
		
		return historyDockLayoutPanel;
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
