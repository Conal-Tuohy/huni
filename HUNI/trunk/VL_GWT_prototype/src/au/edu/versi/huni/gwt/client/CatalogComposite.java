package au.edu.versi.huni.gwt.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Tree;

public class CatalogComposite extends ResizeComposite {

	protected static final String COMPOSITE_INITIAL_DESIGN_WIDTH = "400px";
	public static final String COMPOSITE_DEPTH = "500px";

	private DockLayoutPanel wrapperPanel;
	private CrudButtonComposite crudButtonPanel;

	public CatalogComposite() {
		wrapperPanel = contentBuilder();
		initWidget(wrapperPanel);
		setStylePrimaryName("huni-catalog-list");
		setSize(COMPOSITE_INITIAL_DESIGN_WIDTH, COMPOSITE_DEPTH);
	}

	protected DockLayoutPanel contentBuilder() {

		DockLayoutPanel catalogDockLayoutPanel = new DockLayoutPanel(Unit.EM);

		InlineLabel historyInlineLable = new InlineLabel("Catalog");
		historyInlineLable.setSize("100%", "20px");
		catalogDockLayoutPanel.addNorth(historyInlineLable, 1.9);

		crudButtonPanel = new CrudButtonComposite();
		catalogDockLayoutPanel.addSouth(crudButtonPanel, 3.1);

		DecoratorPanel decoratorPanel = new DecoratorPanel();
		decoratorPanel.setSize("100%", "100%");
		Tree historytTree = new Tree();
		decoratorPanel.setWidget(historytTree);
		historytTree.setSize("100%", "485px");
		catalogDockLayoutPanel.add(decoratorPanel);

		return catalogDockLayoutPanel;
	}
}
