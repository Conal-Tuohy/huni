package au.edu.versi.huni.gwt.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class CrudButtonComposite extends Composite {

	HorizontalPanel buttonBarPanel;
	
	public CrudButtonComposite()
	{
		buttonBarPanel = buildCrudBar();
	      // All composites must call initWidget() in their constructors.
	      initWidget(buttonBarPanel);

	      // Give the overall composite a style name.
	      setStyleName("huni-crud-button-bar");
	}

	protected HorizontalPanel  buildCrudBar() {
		// The button container populated with buttons
		HorizontalPanel buttonBarPanel = new HorizontalPanel();
		buttonBarPanel.setWidth("75%");		
		Button newButton = new Button("New");
		buttonBarPanel.add(newButton);		
		Button editButton = new Button("Edit");
		buttonBarPanel.add(editButton);		
		Button deleteButton = new Button("Delete");
		buttonBarPanel.add(deleteButton);
		return buttonBarPanel;
	}

}
