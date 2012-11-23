package au.edu.versi.huni.gwt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.google.gwt.dom.client.Style.Unit;

public class SearchBoxComposite extends Composite implements ClickHandler {

	private LayoutPanel layoutPanel;
	private InlineLabel searchInlineLabel;
	private TextBox searchTextBox;
	private LayoutPanel layoutPanel_1;
	
	public SearchBoxComposite()
	{
		layoutPanel = buildSearchBox();
	     // All composites must call initWidget() in their constructors.
	      initWidget(layoutPanel);
	      layoutPanel_1.setSize("290px", "24px");

	      // Give the overall composite a style name.
	      setStyleName("huni-search-box");
	}

	protected LayoutPanel buildSearchBox() {
		
		layoutPanel_1 = new LayoutPanel();
		
		searchInlineLabel = new InlineLabel("Search:");
		searchInlineLabel.setDirectionEstimator(true);
		searchInlineLabel.setWordWrap(false);
		searchInlineLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		searchInlineLabel.setSize("60px", "24px");
		layoutPanel_1.add(searchInlineLabel);
		layoutPanel_1.setWidgetTopHeight(searchInlineLabel, 0.0, Unit.PX, 24.0, Unit.PX);

		searchTextBox = new TextBox();
		searchTextBox.setDirectionEstimator(true);
		searchTextBox.setAlignment(TextAlignment.LEFT);
		searchTextBox.setText("for dataset by name");
		searchTextBox.setName("searchForDataset");
		searchTextBox.setAlignment(TextAlignment.LEFT);
		searchTextBox.setSize("200px", "18px");
		layoutPanel_1.add(searchTextBox);
		layoutPanel_1.setWidgetLeftWidth(searchTextBox, 66.0, Unit.PX, 211.0, Unit.PX);
		layoutPanel_1.setWidgetTopHeight(searchTextBox, 0.0, Unit.PX, 24.0, Unit.PX);

		return layoutPanel_1;
	}

	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub

	}

}
