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

public class HistoryComposite extends ResizeComposite implements ClickHandler {

	private DockLayoutPanel historyDockLayoutPanel;
	private CrudButtonComposite crudButtonPanel;
	
	public HistoryComposite()
	{
		crudButtonPanel = new CrudButtonComposite();
		historyDockLayoutPanel = historyPanelBuilder();
	      // All composites must call initWidget() in their constructors.
	      initWidget(historyDockLayoutPanel);
	      
			 if (isDesignTime()) { // or !Beans.isDesignTime() in GWT 2.4 or higher
				 historyDockLayoutPanel.setSize("400px", "600px");
		        }
			 else
			 {
				 historyDockLayoutPanel.setSize("100%", "100%");
			 }

	      // Give the overall composite a style name.
	      setStyleName("huni-history-list");
	}
	
	protected DockLayoutPanel historyPanelBuilder() {
		
		DockLayoutPanel historyDockLayoutPanel = new DockLayoutPanel(Unit.EM);
		
		InlineLabel historyInlineLable = new InlineLabel("History");
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
