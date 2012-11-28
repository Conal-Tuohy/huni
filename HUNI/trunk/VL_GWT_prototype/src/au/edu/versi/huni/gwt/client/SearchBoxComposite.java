package au.edu.versi.huni.gwt.client;

import java.beans.Beans;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;

public class SearchBoxComposite extends Composite implements ClickHandler {

	private LayoutPanel layoutPanel;
	private InlineLabel searchInlineLabel;
	private TextBox searchTextBox;
	
	public SearchBoxComposite()
	{
		layoutPanel = buildSearchBox();
	     // All composites must call initWidget() in their constructors.
	      initWidget(layoutPanel);
	      layoutPanel.setSize("392px", "25px");

	      // Give the overall composite a style name.
	      setStyleName("huni-search-box");
	}

	protected LayoutPanel buildSearchBox() {
		
		LayoutPanel layoutPanel = new LayoutPanel();
		
		searchInlineLabel = new InlineLabel("Search:");
		searchInlineLabel.setDirectionEstimator(true);
		searchInlineLabel.setWordWrap(false);
		searchInlineLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		searchInlineLabel.setSize("60px", "24px");
		layoutPanel.add(searchInlineLabel);
		layoutPanel.setWidgetRightWidth(searchInlineLabel, 338.0, Unit.PX, 60.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(searchInlineLabel, 6.0, Unit.PX, 24.0, Unit.PX);

		searchTextBox = new TextBox();
		searchTextBox.setDirectionEstimator(true);
		searchTextBox.setAlignment(TextAlignment.LEFT);
		searchTextBox.setText("for dataset by name");
		searchTextBox.setName("searchForDataset");
		searchTextBox.setAlignment(TextAlignment.LEFT);
		searchTextBox.setSize("200px", "18px");
		layoutPanel.add(searchTextBox);
		layoutPanel.setWidgetLeftWidth(searchTextBox, 66.0, Unit.PX, 211.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(searchTextBox, 0.0, Unit.PX, 24.0, Unit.PX);
		
		InlineHyperlink advanceSearchHyperlink = new InlineHyperlink("Advanced search", false, "newHistoryToken");
		layoutPanel.add(advanceSearchHyperlink);

		layoutPanel.setWidgetLeftWidth(advanceSearchHyperlink, 285.0, Unit.PX, 101.0, Unit.PX);
		layoutPanel.setWidgetBottomHeight(advanceSearchHyperlink, 1.0, Unit.PX, 18.0, Unit.PX);

		return layoutPanel;
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
