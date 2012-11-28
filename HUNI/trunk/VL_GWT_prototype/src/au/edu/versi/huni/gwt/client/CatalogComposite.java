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

public class CatalogComposite extends ResizeComposite implements ClickHandler {

	private DockLayoutPanel catalogDockLayoutPanel;
	private CrudButtonComposite crudButtonPanel;
	
	public CatalogComposite()
	{
		crudButtonPanel = new CrudButtonComposite();
		catalogDockLayoutPanel = contentBuilder();
	      // All composites must call initWidget() in their constructors.
	      initWidget(catalogDockLayoutPanel);
	      
			 if (isDesignTime()) { // or !Beans.isDesignTime() in GWT 2.4 or higher
				 catalogDockLayoutPanel.setSize("400px", "600px");
		        }
			 else
			 {
				 catalogDockLayoutPanel.setSize("100%", "100%");
			 }

	      // Give the overall composite a style name.
	      setStyleName("huni-catalog-list");
	}
	
	protected DockLayoutPanel contentBuilder() {
		
		DockLayoutPanel catalogDockLayoutPanel = new DockLayoutPanel(Unit.EM);
		
		InlineLabel historyInlineLable = new InlineLabel("Catalog");
		historyInlineLable.setSize("100%", "20px");
		catalogDockLayoutPanel.addNorth(historyInlineLable, 1.9);
				
		catalogDockLayoutPanel.addSouth(crudButtonPanel, 3.1);
		
		DecoratorPanel decoratorPanel = new DecoratorPanel();
		decoratorPanel.setSize("100%", "100%");		
		Tree historytTree = new Tree();
		decoratorPanel.setWidget(historytTree);
		historytTree.setSize("100%", "485px");
		catalogDockLayoutPanel.add(decoratorPanel);
		
		return catalogDockLayoutPanel;
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
